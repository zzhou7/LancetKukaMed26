package application;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.lin;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.linRel;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.positionHold;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.ptp;

import  com.kuka.roboticsAPI.deviceModel.*;
import  com.kuka.roboticsAPI.motionModel.*;
import  com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import  com.kuka.roboticsAPI.motionModel.controlModeModel.*;
import com.kuka.sensitivity.conditions.JointTorqueCondition;
import com.kuka.sensitivity.controlmode.CartesianImpedanceControlMode;
import com.kuka.sensitivity.controlmode.JointImpedanceControlMode;
import com.kuka.condition.ICondition;
import com.kuka.device.common.JointEnum;
import com.kuka.device.common.JointPosition;
import com.kuka.geometry.Frame;
import com.kuka.geometry.LoadData;
import com.kuka.geometry.LocalFrame;
import com.kuka.geometry.ObjectFrame;
import com.kuka.geometry.Tool;
import com.kuka.math.geometry.ITransformation;
import com.kuka.math.geometry.Transformation;
import com.kuka.med.devicemodel.LBRMed;
import com.kuka.motion.IMotion;
//import com.kuka.med.mastering.Mastering;
import com.kuka.motion.IMotionContainer;
//import com.kuka.med.jogging.Jogging;

//import java.awt.Frame;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.EnumSet; 
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import protocols.Param;
import protocols.DefualtProtocol;
import protocols.ProtocolResult;
//import com.kuka.common.ThreadUtil;
//import com.kuka.med.deviceModel.LBRMed;
import com.kuka.roboticsAPI.conditionModel.ForceCondition;
//import com.kuka.roboticsAPI.conditionModel.ICondition;
import com.kuka.roboticsAPI.conditionModel.IORangeCondition;
//import com.kuka.roboticsAPI.conditionModel.JointTorqueCondition;
import com.kuka.roboticsAPI.conditionModel.ObserverManager;
//import com.kuka.roboticsAPI.deviceModel.JointPosition;
//import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.executionModel.CommandInvalidException;
//import com.kuka.roboticsAPI.geometricModel.*;
//import com.kuka.roboticsAPI.geometricModel.math.Transformation;
//import com.kuka.roboticsAPI.geometricModel.redundancy.IRedundancyCollection;
import com.kuka.task.ITaskLogger;
import com.kuka.threading.ThreadUtil;
import com.sun.media.jfxmedia.logging.Logger;
import functions.*;
//import static com.kuka.roboticsAPI.motionModel.HRCMotions.*;

public class ProtocolProcess {
	private FreeHandMotion m_freehandMotion = null;
	private IMotionContainer mc = null;
	private PositionControlMode posMode = new PositionControlMode();
	private LBRMed m_robot = null;
	private Tool tool;
	
	private BrakeTestHandler m_brakeTestExecutor = null;
	//private PositionAndGMS pgms = null;
	private boolean isSoftMode = false;
	//private Mastering mastering;
	//private Jogging jogging;
  private ITaskLogger log;
  private ArmRobotApp m_app;
  private double speedLevel = 1.0;
  public ProtocolProcess(LBRMed rb, ITaskLogger logger, Tool t, ObserverManager observerMgr)
	{
		m_robot = rb;
		log = logger;
		//mastering = new Mastering(rb);
		//jogging = new Jogging(rb);
		tool = t;
	}
	
	public void setBrakeTestExecutor(BrakeTestHandler brake)
	{
		m_brakeTestExecutor = brake;
	}
	public void  setApp(ArmRobotApp app) {
		m_app = app;
	}
	public  boolean IsSoftMode()  {
		return isSoftMode; 
	}
	
	public ProtocolResult checkStop() {
		DefualtProtocol msg = m_app.peekMsgBean();
		if (msg != null && msg.getOperateType().equals("MoveStop")) {
			mc.cancel();
			m_app.getMsgBean();
			ProtocolResult ret = new ProtocolResult();
			ret.setOperateType(msg.getOperateType());
			return ret; 
		}
		return null;		
	}
	public ProtocolResult GetJointPos(){
		DefualtProtocol msg = m_app.peekMsgBean();
		if(msg != null && msg.getOperateType().equals("GetJointPos")){
			JointPosition jtPos = m_robot.getCurrentJointPosition();
			Frame frm = m_robot.getCurrentCartesianPosition(m_robot.getFlange());
			ProtocolResult ret = new ProtocolResult();	
			Param  prm = new Param();
			prm.x = frm.getTransformationFromParent().getX();
			prm.y = frm.getTransformationFromParent().getY();
			prm.z = frm.getTransformationFromParent().getZ();
			prm.a = Math.toDegrees(frm.getTransformationFromParent().getAlphaRad());
			prm.b = Math.toDegrees(frm.getTransformationFromParent().getBetaRad());
			prm.c = Math.toDegrees(frm.getTransformationFromParent().getGammaRad());
			log.info("WWWWWWWW");
			m_app.getMsgBean();
			ret.setParam(prm);
			ret.setResultMsg(jtPos.toString());
			return ret;
		}
		return null;
		
	}
	
	public ProtocolResult checkTorque() {
		double[] vals = m_robot.getExternalTorque().getTorqueValues();
		for (double value : vals) {
			if (Math.abs(value) > 20) {
				mc.cancel();
				log.info("checkTorque " +
						m_robot.getExternalTorque().toString());
				ProtocolResult ret = new ProtocolResult();
				ret.setOperateType("MotionBlock");
				ret.setResultMsg("MotionBlock ===");
				return ret;
			}
		}
		
		return null;
	}
	public ProtocolResult ProcessData(DefualtProtocol bean)
	{
		if (bean == null) {
			log.info("bean null======");
			ProtocolResult result = new ProtocolResult();
			result.setResultCode(0);
			result.setResultMsg("fail");
			return result;
		}
		String opType = bean.getOperateType();
		if (opType.equals("Reset"))
		{
			return Reset(bean);
		}
		else if (opType.equals("RunBrakeTest"))
		{
			return TestBrake(bean);
		}
		else if (opType.equals("HandGuiding"))
		{
			return freeHand();
		}
		else if (opType.equals("AddFrame")) {
      Param param = bean.getParam();
      ObjectFrame frame = null;
      ProtocolResult ret = new ProtocolResult();
      ret.setOperateType("AddFrame");  
      ret.setResultMsg("Add Frame:"+param.target);
      try {
        frame = tool.findFrame(param.target);
      } catch (Exception e){
        log.info("Frame not exist: "+param.target);
      }
      if(frame != null) {
        tool.removeFrame(frame);
        ret.setResultMsg("Set Frame:"+param.target);
      } 
      tool.createFrame(param.target, 
          Transformation.ofRad(param.getX(), param.getY(), param.getZ(), 
              param.getA(),param.getB(), param.getC()));

      ret.setResultCode(0);
      return ret;
    }
    else if (opType.equals("SetMotionFrame")) {  
      try {
        tool.setDefaultMotionFrame(tool.findFrame(bean.getParam().target));
      } catch (Exception e){
        log.info("Frame not exist: "+bean.getParam().target);
        ProtocolResult ret = new ProtocolResult();
        ret.setOperateType("SetMotionFrame");  
        ret.setResultMsg("Failed,Frame:"+bean.getParam().target);
        ret.setResultCode(-1);
        return ret;
      }
      ProtocolResult ret = new ProtocolResult();
      ret.setOperateType("SetMotionFrame");  
      ret.setResultMsg("Success,Frame:"+bean.getParam().target);
      ret.setResultCode(0);
      return ret;
    }
    else if (opType.equals("MovePTP"))
    {
      if (mc != null) {     
        mc.cancel();
      }
      Param p =bean.getParam();
      ITransformation tans = Transformation.ofRad(p.x,p.y,p.z,p.a,p.b,p.c);
      LocalFrame target = new LocalFrame(m_robot.findWorld().getRootFrame(),tans);
      mc = m_robot.moveAsync(ptp(target)
          .setJointVelocityRel(0.05).setJointAccelerationRel(0.02));
      ProtocolResult ret = new ProtocolResult();
      ret.setOperateType("MovePTP");    
      ret.setResultMsg("MovePTP ok");
      ret.setResultCode(0);
      return ret;
    }
		else if (opType.equals("MoveStop"))
		{
			mc.cancel();
			m_app.getMsgBean();
			ProtocolResult ret = new ProtocolResult();
			ret.setOperateType("MoveStop");
			return ret;
		}
		//闂嗗墎鈹栭梻锟�
		else if (opType.equals("ZeroSpace"))
		{
			if (mc != null) {			
				mc.cancel();
			}
			isSoftMode = true;
			CartesianImpedanceControlMode softMode = new CartesianImpedanceControlMode();
			softMode.parametrize(CartDOF.ALL).setDamping(0.7);
			softMode.parametrize(CartDOF.A).setStiffness(5);
			softMode.parametrize(CartDOF.A).setStiffness(5);
			softMode.parametrize(CartDOF.B).setStiffness(5);
			softMode.parametrize(CartDOF.C).setStiffness(5);
			softMode.parametrize(CartDOF.X).setStiffness(5000);
			softMode.parametrize(CartDOF.Y).setStiffness(5000);
			softMode.parametrize(CartDOF.Z).setStiffness(5000);
			softMode.setMaxPathDeviation(5.0, 5.0, 5.0, 3.14, 3.14, 3.14);
			mc = m_robot.getFlange().moveAsync(positionHold(softMode,-1,TimeUnit.SECONDS));
			ProtocolResult ret = new ProtocolResult();
			ret.setOperateType("ZeroSpace_on");
			ret.setResultMsg("ZeroSpace_on ok");
			return ret;
		}
		//绾扮増鎸掑Λ锟藉ù锟�
		else if (opType.equals("Extern_Force"))
		{
			double[] joint = bean.getJointPos();
			JointPosition jp = new JointPosition(
					Math.toRadians(joint[0]),
					Math.toRadians(joint[1]),
					Math.toRadians(joint[2]),
					Math.toRadians(joint[3]),
					Math.toRadians(joint[4]),
					Math.toRadians(joint[5]),
					Math.toRadians(joint[6]));
		    ForceCondition littleforce = ForceCondition.createSpatialForceCondition(m_robot.getFlange(),5);
			ICondition conda ;
			conda = littleforce;
		    mc = m_robot.getFlange().moveAsync(ptp(jp).setJointVelocityRel(0.3* speedLevel)
		    		.setJointAccelerationRel(0.02).breakWhen(conda));
		}
		//闂嗗爼鍣搁崝锟�
		else if (opType.equals("Zero_Gravity"))
		{
			if (mc != null) {			
				mc.cancel();
			}
			isSoftMode = true; 
			JointImpedanceControlMode  softMode = new JointImpedanceControlMode(5, 5, 5, 5, 5, 5, 5);
			softMode.setDamping(0.4);
			mc = m_robot.getFlange().moveAsync(positionHold(softMode, -1, TimeUnit.SECONDS));
			ProtocolResult ret = new ProtocolResult();
			ret.setOperateType("SoftMode_On");		
			ret.setResultMsg("SoftMode_On ok");
			ret.setResultCode(0);
			return ret;
		}
		else if (opType.equals("JointPos"))
		{
			double[] joint = bean.getJointPos();
			JointPosition jp = new JointPosition(
					Math.toRadians(joint[0]),
					Math.toRadians(joint[1]),
					Math.toRadians(joint[2]),
					Math.toRadians(joint[3]),
					Math.toRadians(joint[4]),
					Math.toRadians(joint[5]),
					Math.toRadians(joint[6]));
			JointTorqueCondition torqueCondJ1 = new JointTorqueCondition(JointEnum.J1,-25,25);
			JointTorqueCondition torqueCondJ2 = new JointTorqueCondition(JointEnum.J2,-25,25);
			JointTorqueCondition torqueCondJ3 = new JointTorqueCondition(JointEnum.J3,-25,25);
			JointTorqueCondition torqueCondJ4 = new JointTorqueCondition(JointEnum.J4,-25,25);
			JointTorqueCondition torqueCondJ5 = new JointTorqueCondition(JointEnum.J5,-25,25);
			JointTorqueCondition torqueCondJ6 = new JointTorqueCondition(JointEnum.J6,-25,25);
			JointTorqueCondition torqueCondJ7 = new JointTorqueCondition(JointEnum.J7,-25,25);
			//ForceCondition littleforce = ForceCondition.createSpatialForceCondition(m_robot.getFlange(),5);
			ICondition conda ;
			conda = torqueCondJ1.or(torqueCondJ2,torqueCondJ3,torqueCondJ4,
					torqueCondJ5,torqueCondJ6,torqueCondJ7);
		    mc = m_robot.moveAsync(ptp(jp).setJointVelocityRel(0.3* speedLevel)
		    		.setJointAccelerationRel(0.02).breakWhen(conda));
		    //boolean m = m_robot.checkTorqueSensor(JointEnum.J1);
			while (!mc.isFinished()) {
//				ProtocolResult ret = checkStop();				
//				if (ret != null) {
//					log.info("ret_checkstop != null" );		
//					return ret;
//				}
				ProtocolResult ret = GetJointPos();
				if(ret != null){
					log.info("ret_getjointpos != null ");
					return ret;
				}
			   }
			log.info("ret 8888888888= null  " );
			ProtocolResult ret = new ProtocolResult();
			ret.setOperateType("JointPos");		
			ret.setResultMsg("JointPos ok");
			return ret;
		}	
			else if (opType.equals("FingerMove"))
		{
			bean.getJointPos();
			Param p = bean.getParam();			 
			Transformation offset = Transformation.ofDeg(
					p.x, p.y, p.z, p.a, p.b, p.c);
			log.info("Transformation " + offset.toString());
			IMotion mov = linRel(offset)
					.setJointVelocityRel(0.3 * speedLevel)
					.setJointAccelerationRel(0.02);
			mc = m_robot.getFlange().moveAsync(mov);
			//mc = tool.getFrame("Finger").moveAsync(mov);
		  	while (!mc.isFinished()) {
				ProtocolResult ret = checkStop();				
				if (ret != null) {
					log.info("ret != null  " );		
					return ret;
				}				
				ret = checkTorque();				
				if (ret != null) {
					log.info("ret != null  " );		
					return ret;
				}		
			}
			log.info("ret 8888888888= null  " );
			ProtocolResult ret = new ProtocolResult();
			ret.setOperateType(bean.getOperateType());		
			ret.setResultMsg("FingerMove ok");
			return ret;
		}		
		else if (opType.equals("LineMode_On"))
		{
			if (mc != null) {			
				mc.cancel();
			}
			isSoftMode = true; 
			CartesianImpedanceControlMode  softMode = new CartesianImpedanceControlMode();
			softMode.parametrize(CartDOF.ALL).setDamping(1);
			softMode.parametrize(CartDOF.ROT).setStiffness(300);
			softMode.parametrize(CartDOF.X).setStiffness(5000);
			softMode.parametrize(CartDOF.Y).setStiffness(50);	
			softMode.parametrize(CartDOF.Z).setStiffness(5000);
			
			mc = m_robot.moveAsync(positionHold(softMode, -1, TimeUnit.SECONDS));
			ProtocolResult ret = new ProtocolResult();
			ret.setOperateType("SoftMode_On");		
			ret.setResultMsg("SoftMode_On ok");
			ret.setResultCode(0);
			return ret;
		}
		else if (opType.equals("ConeMode_On"))
		{
			if (mc != null) {			
				mc.cancel();
			}
			CartesianImpedanceControlMode  softMode = new CartesianImpedanceControlMode();
			softMode.parametrize(CartDOF.ALL).setDamping(0.7);
			softMode.parametrize(CartDOF.A).setStiffness(40);
			softMode.parametrize(CartDOF.B).setStiffness(40);
			softMode.parametrize(CartDOF.C).setStiffness(40);
			softMode.parametrize(CartDOF.X).setStiffness(5000);
			softMode.parametrize(CartDOF.Y).setStiffness(5000);	
			softMode.parametrize(CartDOF.Z).setStiffness(5000);
			softMode.setMaxPathDeviation(5.0, 5.0, 5.0, 1.0, 0.26, 0.26);
			mc = tool.findFrame("RobotUserTool").moveAsync(positionHold(softMode, -1, TimeUnit.SECONDS));
			ProtocolResult ret = new ProtocolResult();
			ret.setOperateType("SoftMode_On");		
			ret.setResultMsg("SoftMode_On ok");
			ret.setResultCode(0);
			return ret;
		}
		
		else if (opType.equals("SoftMode_On"))
		{
			if (mc != null) {			
				mc.cancel();
			}
			isSoftMode = true; 
			JointImpedanceControlMode  softMode = new JointImpedanceControlMode(5, 5, 5, 5, 5, 5, 5);
			mc = m_robot.moveAsync(positionHold(softMode, -1, TimeUnit.SECONDS));
			ProtocolResult ret = new ProtocolResult();
			ret.setOperateType("SoftMode_On");		
			ret.setResultMsg("SoftMode_On ok");
			ret.setResultCode(0);
			return ret;
		}
		else if (opType.equals("SoftMode_Off"))
		{
			return SoftModeOff();
		
		}
//		else if (opType.equals("Master"))
//		{
//			if (isSoftMode) {
//			SoftModeOff();
//			log.info("Master SoftModeOff");
//			}
//			return funcMaster();
//		}

		else if (opType.equals("Speed"))
		{
			String str = bean.getParam().getTarget();
			speedLevel = Double.valueOf(str) / 100;
			log.info("speedLevel " + speedLevel);
			ProtocolResult ret = new ProtocolResult();
			ret.setOperateType(opType);		
			ret.setResultMsg("Speed ok");
			return ret;
		}
		else if (opType.equals("Load"))
		{
			log.info("**** Mass " + tool.getLoadData().toString());
			
			String[] strArray =  bean.getParam().getTarget().split(";");
		 	double MASS = new Double(strArray[0]);
		 	String[] strMass = strArray[1].split(",");
		 	double[] CENTER_MASS = {0, 0, 0};
		 	CENTER_MASS[0] = new Double(strMass[0]);
		 	CENTER_MASS[1] = new Double(strMass[1]);
		 	CENTER_MASS[2] = new Double(strMass[2]);

			log.info("CENTER_MASS " + CENTER_MASS[0]);
			log.info("CENTER_MASS " + CENTER_MASS[1]);
			log.info("CENTER_MASS " + CENTER_MASS[2]);

			LoadData load = tool.getLoadData();
			
			load.setMass(MASS);		
			load.setCenterOfMass(
					CENTER_MASS[0],
					CENTER_MASS[1],
					CENTER_MASS[2]);

			log.info("**** Mass " + tool.getLoadData().toString());
			mc = m_robot.move(
					positionHold(posMode, 1, TimeUnit.SECONDS));
			
			ProtocolResult ret = new ProtocolResult();
			ret.setOperateType(opType);		
			ret.setResultMsg("Load ok");
			return ret;
		}
		else if (opType.equals("CheckBrakeOK"))
		{
			boolean ok = m_brakeTestExecutor.isBrakeTestOK();

			ProtocolResult ret = new ProtocolResult();
			ret.setOperateType(opType);
			ret.setResultMsg("CheckBrakeOK");
			int code = ok ? 0 : -1;
			log.info("CheckBrakeOK " + ok);
			log.info("CheckBrakeOK code " + code);
			ret.setResultCode(code);
			
			return ret;
		
		}
		else if (opType.equals("CheckNearPos"))
		{
			double[] joint = bean.getJointPos();
			JointPosition jp = new JointPosition(
					Math.toRadians(joint[0]),
					Math.toRadians(joint[1]),
					Math.toRadians(joint[2]),
					Math.toRadians(joint[3]),
					Math.toRadians(joint[4]),
					Math.toRadians(joint[5]),
					Math.toRadians(joint[6]));
			
			boolean ok = m_robot.getCurrentJointPosition().isNearlyEqual(jp, Math.toRadians(2));
		
			ProtocolResult ret = new ProtocolResult();
			ret.setOperateType(opType);
			ret.setResultMsg("CheckNearPos");
			int code = ok ? 0 : -1;
			ret.setResultCode(code);
			
			return ret;
		}
		else {
			log.info(" cmd not found !" + opType);
			return null;	
		}
		return null;	
	}
	
	public ProtocolResult SoftModeOff()
	{
		mc.cancel();	
		isSoftMode = false;
		m_robot.moveAsync(positionHold(posMode, 1, TimeUnit.SECONDS));
		
		ProtocolResult ret = new ProtocolResult();
		ret.setOperateType("SoftMode_Off");		
		ret.setResultMsg("SoftMode_Off ok");
		return ret;
	}
	
	//鑴拌劥鑴拌剻闅嗗瀯楣胯寘鑴︾纰岄檰鎴纰岀洸鑴︾鑴拌剻
	private ProtocolResult Reset(DefualtProtocol bean)
	{
		/*JointPosition jp_02 = new JointPosition(0, 0, 0, Math.toRadians(90), 0, Math.toRadians(-90), 0);
		
		robot.setHomePosition(jp_02);
		robot.move(ptpHome().setJointVelocityRel(0.6));*/
		
		ProtocolResult result = new ProtocolResult();
		result.setResultCode(0);
		
		return result;
	}
	
	private ProtocolResult freeHand()
	{
	
		if( m_freehandMotion == null)
		{
			m_freehandMotion = new FreeHandMotion(m_robot);
		}
		
		return m_freehandMotion.run();
	}
	
	public ProtocolResult TestBrake(DefualtProtocol bean)
	{
		if (m_brakeTestExecutor == null) return null;
		
		return m_brakeTestExecutor.run();
	}
	
//	public void jogAxis(int idx, double jogPos)
//	{		
//		JointPosition jtPos = m_robot.getCurrentJointPosition();
//		double[] axises = jtPos.toArray();
//		double jogVec = 1;
//		jogPos = Math.toRadians(jogPos);
//
//		if (axises[idx] < jogPos) {
//			jogVec = 1;
//			// isJog = true;
//		} else if (axises[idx] > jogPos) {
//			jogVec = -1;
//			// isJog = true;
//		}
//		log.info("Jog axis " + " " + idx);
//		JointEnum jtNum = JointEnum.values()[idx];
//		try {
//			jogging.startJointJogging(EnumSet.of(jtNum), 0, -1);
//			log.info("start Joint Jogging...");
//			while (true) {
//				jtPos = m_robot.getCurrentJointPosition();
//				axises = jtPos.toArray();
//				if (Math.abs(axises[idx] - jogPos) < 0.02) {
//					jogging.stopJointJogging(EnumSet.of(jtNum));
//					break;
//				}
//				jogging.updateJointJogging(EnumSet.of(jtNum), jogVec);
//
//				ThreadUtil.milliSleep(100);
//			}
//		} catch (Exception e) {
//			log.error("Jogging failed... " + e.toString());
//		}
//	}
	
	
//public ProtocolResult funcMaster() {
//		ThreadUtil.milliSleep(5000); 
//		ProtocolResult ret = new ProtocolResult();
//		ret.setOperateType("Master");
//		
//		double jogPosArray[] = {0, -60, 0, 0, 0, 0, 0}; // 鐟欐帒瀹�
//		for (int idx = 0; idx < 7; ++idx) {
//			if (!mastering.isAxisMastered(idx)) {
//				log.info("Mastering Axis " + idx + "...");
//				jogAxis(idx, 0);
//				if (mastering.masterAxis(idx)) {
//					log.info("Mastering Axis " + idx + " finished");
//				} else {
//					log.error("Mastering Axis " + idx + " failed");
//					ret.setResultCode(-1);		
//					ret.setResultMsg("Master bad");
//					return ret;
//				}
//			}
//			jogAxis(idx, jogPosArray[idx]);
//		}
//
//		ret.setResultCode(0);	
//		ret.setResultMsg("Master ok");
//		return ret;
//	}
//	
//public void jog2AxisPosition(double[] axisesDest /*鐟欐帒瀹� */) {
//	JointPosition jtPos = m_robot.getCurrentJointPosition();
//	double[] axises = jtPos.get();
//
//	int cnt = 0;
//	while (++cnt <= 60) {
//		JointPosition jtPosAA = m_robot.getCurrentJointPosition();
//		double[] axisesTmp = jtPosAA.get();
//		for (int idx = 0; idx < 7; ++idx) {
//			double delta = (axises[idx] - Math.toRadians(axisesDest[idx]))
//					* cnt / 10;
//			if (Math.abs(axisesTmp[idx] - Math.toRadians(axisesDest[idx])) > 0.1) {
//				jogAxis(idx, Math.toDegrees(axises[idx] - delta));
//			}
//		}
//	}
//}
//	
//	public ProtocolResult funcMasterNew() {
//		ThreadUtil.milliSleep(10000); // 缁涘绶�10s 娴ｈ法鈻兼惔蹇斾划婢跺秷绻嶇悰宀�濮搁幀锟�
//	
//		ProtocolResult ret = new ProtocolResult();
//		ret.setOperateType("Master");
//
//		JointPosition jtPos = m_robot.getCurrentJointPosition();
//		double[] axises = jtPos.toArray();
//		log.info(" ========== " + axises);
//
//		int cnt = 0;
//		if (axises[1] > 0) {
//			while (++cnt <= 20) {
//				JointPosition jtPosAA = m_robot.getCurrentJointPosition();
//				double[] axisesTmp = jtPosAA.toArray();
//				if (axises[1] > Math.toRadians(60))
//					break;
//				double delta = (axises[1] - (axises[1] - Math.toRadians(20)))
//						* cnt / 20;
//				if (Math.abs(axisesTmp[1] - (axises[1] - Math.toRadians(20))) > 0.1) {
//					jogAxis(1, Math.toDegrees(axises[1] - delta));
//				}
//			}
//			log.info("Move jog Axis 2 ===============...");
//			double[] axisesDest = { 0, 50, 0, -70, 0, -30, 0 };
//			jog2AxisPosition(axisesDest);
//			
//			// 鐠嬪啯鏆� 2閿涳拷4閿涳拷6鏉炵繝缍呯純锟�
//			double[] axisesDest246 = { 0, 85, 0, 0, 0, 0, 0 };
//			jog2AxisPosition(axisesDest246);
//
//			for (int idx = 2; idx < 7; ++idx) {
//				if (!mastering.isAxisMastered(idx)) {
//					log.info("Mastering Axis " + idx + "...");
//					jogAxis(idx, 0);
//					if (mastering.masterAxis(idx)) {
//						log.info("Mastering Axis " + idx + " finished");
//					} else {
//						log.error("Mastering Axis " + idx + " failed");
//						ret.setResultCode(-1);		
//						ret.setResultMsg("Master bad");
//						return ret;
//					}
//				}
//			}
//			
//		}
//		// ================================
//	
//		jtPos = m_robot.getCurrentJointPosition();
//		axises = jtPos.toArray();
//		log.info("aaaaaaaaaa  ==********************======== " + axises);
//		cnt = 0;
//		// 娴煎摜娲�2鏉炶揪绱� 瀵垱娲�4閿涳拷6鏉烇拷
//		double[] axisesDestWind = { 0, 0, 0, -90, 0, 10, 0 };
//		jog2AxisPosition(axisesDestWind);
//		
//		for (int idx = 0; idx < 2; ++idx) {
//			if (!mastering.isAxisMastered(idx)) {
//				log.info("Mastering Axis " + idx + "...");
//				jogAxis(idx, 0);
//				if (mastering.masterAxis(idx)) {
//					log.info("Mastering Axis " + idx + " finished");
//				} else {
//					log.error("Mastering Axis " + idx + " failed");
//					ret.setResultCode(-1);		
//					ret.setResultMsg("Master bad");
//					return ret;
//				}
//			}
//		}
//
//		ret.setResultCode(0);		
//		ret.setResultMsg("Master ok");
//		return ret;
//	}
}
