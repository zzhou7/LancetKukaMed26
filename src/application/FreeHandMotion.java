package application;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.ptp;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.ptpHome;
import com.kuka.handguiding.motion.controlmode.HandGuidingControlMode;
import static com.kuka.handguiding.motion.HRCMotions.handGuiding;
import com.kuka.med.devicemodel.LBRMed;
import protocols.ProtocolResult;


public class FreeHandMotion {
	private LBRMed robot_ = null;
	
	public FreeHandMotion(LBRMed rb)
	{
		robot_ = rb;
	}
	
	public void initialize() {
		
	}
	
	public ProtocolResult run() {
		
		robot_.move(handGuiding().setJointLimitViolationFreezesAll(false));
		
		ProtocolResult ret = new ProtocolResult();
		ret.setOperateType("freeHand");		
		ret.setResultMsg("freehand motion succeed");
		return ret;
		
	}
}
