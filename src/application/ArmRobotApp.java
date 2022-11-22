package application;

import com.kuka.device.common.JointPosition;
import com.kuka.geometry.LoadData;
import com.kuka.geometry.Tool;
import com.kuka.math.geometry.ITransformation;
import com.kuka.math.geometry.Transformation;
import com.kuka.med.devicemodel.LBRMed;
import com.kuka.med.mastering.MedApplicationCategory;
import com.kuka.motion.IMotionContainer;
import com.kuka.roboticsAPI.applicationModel.IApplicationControl;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplicationControl;
import com.kuka.roboticsAPI.conditionModel.BooleanIOCondition;
import com.kuka.roboticsAPI.conditionModel.ConditionObserver;
import com.kuka.roboticsAPI.conditionModel.IAnyEdgeListener;
import com.kuka.roboticsAPI.conditionModel.NotificationType;
import com.kuka.roboticsAPI.conditionModel.ObserverManager;
import com.kuka.roboticsAPI.motionModel.ErrorHandlingAction;
import com.kuka.roboticsAPI.motionModel.IMotionErrorHandler;
import com.kuka.roboticsAPI.uiModel.IApplicationUI;
import com.kuka.task.ITaskLogger;
import com.kuka.task.RoboticsAPITask;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.inject.Inject;
import javax.inject.Named;
/**
 * Implementation of a robot application.
 * <p>
 * The application provides a {@link RoboticsAPITask#initialize()} and a
 * {@link RoboticsAPITask#run()} method, which will be called successively in
 * the application lifecycle. The application will terminate automatically after
 * the {@link RoboticsAPITask#run()} method has finished or after stopping the
 * task. The {@link RoboticsAPITask#dispose()} method will be called, even if an
 * exception is thrown during initialization or run.
 * <p>
 * <b>It is imperative to call <code>super.dispose()</code> when overriding the
 * {@link RoboticsAPITask#dispose()} method.</b>
 * 
 * @see UseRoboticsAPIContext
 * @see #initialize()
 * @see #run()
 * @see #dispose()
 */

@MedApplicationCategory(checkMastering = false)
public class ArmRobotApp extends RoboticsAPIApplication {
  @Inject private LBRMed robot;
  //@Named("Tool")
  //@Inject 
  private Tool tool;
  @Inject private ITaskLogger logger;
  @Inject private IApplicationUI appUI;
  @Inject private IApplicationControl appControl;
  @Inject private ObserverManager observerManager;
  private BrakeTestHandler m_brakeTest = null;
  private boolean m_stop = false;
  public boolean isSendMaster = false;
  private Socket m_socket = null;
  private BufferedReader m_reader = null;
  private OutputStreamWriter m_writer = null;
  private ProtocolProcess m_processer = null;
  // private Mastering mastering;
  private boolean isNeedSoft = false;
  private long freeCheckStart = 0;
  private LinkedList<String> msgQueque = new LinkedList<String>();
  private ReadWriteLock msgLock = new ReentrantReadWriteLock();

  @Override
	public void initialize() {	
		//robot = getContext().getDeviceFromType(LBRMed.class);
		//mastering = new Mastering(robot);
		ITransformation tans = Transformation.ofDeg(0, 0, 20, 0, 0, 0);
	//	LoadData loadRobot =  robot.getLoadData();
		LoadData loadRobot =  new LoadData();
		loadRobot.setCenterOfMass(tans);
		loadRobot.setMass(0.676);
		tool = new Tool("tool", loadRobot);
		logger.info(tool.getLoadData().toString());
		tool.attachTo(robot.getFlange());  
		//logger.info(robot.getLoadData().toString());
		
		//tool.addDefaultMotionFrame("cubeOrigin", Transformation.of(0, 0, 66));
		//tool.addChildFrame("Finger", Transformation.ofTranslation(0, 48, 157));
		//tool.addChildFrame("RobotUserTool", Transformation.ofTranslation(0, 200, 170));
		
//		IErrorHandler errorMotionHandler = new IErrorHandler() {
//			@Override
//			public ErrorHandlingAction handleError(Device device,
//					IMotionContainer failedContainer,
//					List<IMotionContainer> canceledContainers) {
//				logger.info("failedContainer motion: " + failedContainer.toString());
//				logger.info("canceledContainers motions: ");
//				for (int i = 0; i < canceledContainers.size(); i++) {
//					logger.info(canceledContainers.get(i).toString());
//				}
//				logger.info("getErrorMessage: " + failedContainer.getErrorMessage());
//				String strError = failedContainer.getErrorMessage();
//				if (strError.contains("Maximum path deviation exceeded")) {
//					logger.info(" Maximum path exceeded ");
//					return ErrorHandlingAction.PauseMotion;
//				} else if (strError.contains("Can not plan motion")) {
//					logger.info(" failedContainer Can not plan motion !");
//				
//					return ErrorHandlingAction.Ignore;
//				} else {
//					logger.info("else failedContainer!");
//				return ErrorHandlingAction.Ignore;
//				}						
//			}			
//		};
		IMotionErrorHandler errorMotionHandler = new IMotionErrorHandler() {
      @Override
      public ErrorHandlingAction handleExecutionError(IMotionContainer failedContainer,
          List<IMotionContainer> canceledContainers) {
        logger.info("failedContainer motion: " + failedContainer.toString());
      
        logger.info("canceledContainers motions: ");
        for (int i = 0; i < canceledContainers.size(); i++) {
          logger.info(canceledContainers.get(i).toString());
        }
        logger.info("getErrorMessage: " + failedContainer.getErrorMessage());
        String strError = failedContainer.getErrorMessage();
        
        if (strError.contains("Maximum path deviation exceeded")) {
          logger.info(" Maximum path exceeded ");
          return ErrorHandlingAction.PAUSE_MOTION;
        } 
        else if (strError.contains("Can not plan motion")) {
          logger.info(" failedContainer Can not plan motion !");
          return ErrorHandlingAction.IGNORE;
        } 
        else {
          logger.info("else failedContainer!");
          return ErrorHandlingAction.IGNORE;
        }
      }

      @Override
      public ErrorHandlingAction handleMaintainingError(IMotionContainer lastContainer,
          List<IMotionContainer> canceledContainers, String errorMessage) {
        // TODO Auto-generated method stub
        return null;
      }
		};

		appControl.registerMotionErrorHandler(errorMotionHandler);
//		SafeDataIOGroup io = new SafeDataIOGroup(getContext().getDefaultController());
//		
//		BooleanIOCondition ioCondition = new BooleanIOCondition(io.getIO("Input1", false), false);
//		IAnyEdgeListener listener = new IAnyEdgeListener(){
//			@Override
//			public void onAnyEdge(ConditionObserver observer, Date time, int MissedEvents, boolean value) {
//			ProtocolResult ret = new ProtocolResult();		
//				ret.setResultMsg("emergency stop");
//				if( value) {
//					logger.info("閺堢儤顫懛鍌涳拷銉ヤ粻閹稿绗呴敍锟�" );
//					ret.setOperateType("Emergency");
//				} else {
//					logger.info("閺堢儤顫懛鍌涳拷銉ヤ粻閹额剝鎹ｉ敍锟�" );
//					ret.setOperateType("EmergencyOFF");
//				}
//				sendData(ret);			
//			}	
//		};
		
//	 ConditionObserver  obsver = getObserverManager().createConditionObserver(
//			 ioCondition, NotificationType.EdgesOnly, listener);		
//	 obsver.enable();
	
	}

  public boolean isPeerClosed() {
    try {
      if (m_socket.isConnected()) {
        m_socket.sendUrgentData(0xff);
        // m_socket.sendUrgentData(0x0A);
        return false;
      } else {
        return true;
      }
    } catch (Exception e) {
      logger.info("isPeerClosed  true.");
      e.printStackTrace();
      return true;
    }
  }

  public void ReConnect() {
    try {
      SocketAddress address = new InetSocketAddress("172.31.1.148", 30009);
      logger.info("ReConnect Peer ===== ");
      m_socket.close();
      Thread.sleep(100);
      m_socket = new Socket();
      m_socket.connect(address, 0);
      logger.info("peer IP:" + m_socket.getInetAddress().getHostAddress());

      m_reader = new BufferedReader(new InputStreamReader(
          m_socket.getInputStream()));
      m_writer = new OutputStreamWriter(new DataOutputStream(
          m_socket.getOutputStream()));

    } catch (Exception ex) {
      logger.error(ex.toString());
    }
  }

  @Override
  public void run() {
    logger.info("Starting Application.");
    m_brakeTest = new BrakeTestHandler();
    m_processer = new ProtocolProcess(robot, logger, tool, observerManager);
    m_processer.setBrakeTestExecutor(m_brakeTest);
    m_processer.setApp(this);
    m_socket = new Socket();
    ReConnect();

    Thread thrd = new Thread(new Runnable() {
      @Override
      public void run() {
        long oldTime = System.currentTimeMillis();
        while (!m_stop) {
          String line = new String();
          try {
            long timeOut = System.currentTimeMillis() - oldTime;
            if (0 != oldTime && timeOut > 5 * 1000) {
              logger.info("timeOut  " + String.valueOf(timeOut));

              ReConnect();
              Thread.sleep(3000);
              if (m_socket.isConnected()) {
                oldTime = 0;
              }
            }
            if (m_reader != null && m_reader.ready()) {
              line = m_reader.readLine();
            }
            if (0 == line.compareTo("heartBeat")) {
              oldTime = System.currentTimeMillis();
              continue;
            }
            if (!line.isEmpty()) {
              msgLock.writeLock().lock();
              msgQueque.offer(line);
              logger.info("msgQueque size " + msgQueque.size());
              msgLock.writeLock().unlock();
            }
          } catch (Exception ex) {
            ex.printStackTrace();
          }

        }
      }
    });
    thrd.start();

    try {
      while (!m_stop) {
        ProtocolBean msgBean = getMsgBean();
        if (msgBean != null) {
          ProtocolResult result = m_processer.ProcessData(msgBean);
          sendData(result);

          if (result != null && result.getOperateType() == "Master") {
            isSendMaster = false;
            freeCheckStart = 0;
          }
          if (result != null
              && result.getOperateType() == "SoftMode_On") {
            freeCheckStart = System.currentTimeMillis();
            logger.info("freeCheckStart: " + freeCheckStart);
          }
        }

        if (isNeedSoft && freeCheckStart != 0) {
          if (System.currentTimeMillis() - freeCheckStart > 3 * 1000) {
            isNeedSoft = false;
            // freeCheckStart = 0;
            logger.info("isNeedSoft " + isNeedSoft);
          }
        }
        //checkMasterState();

        if (m_processer.IsSoftMode() && !isNeedSoft) {
          CheckAxisLimit();
        }
      }
      logger.info("Closing socket.");
      m_reader.close();
      m_writer.close();
      m_socket.close();
    } catch (IOException e) {
      logger.info(e.getMessage());
    }
    logger.info("Ending Application.");
  }

  public void CheckAxisLimit() {
    double[] lmtJointMin = robot.getJointLimits().getMinJointPosition().toArray();
    double[] lmtJointMax = robot.getJointLimits().getMaxJointPosition().toArray();

    JointPosition jtPos = robot.getCurrentJointPosition();
    double[] axises = jtPos.toArray();

    for (int idx = 0; idx < 7; ++idx) {
      if (axises[idx] < lmtJointMin[idx] - Math.toRadians(10)
          && axises[idx] > lmtJointMin[idx]
          || axises[idx] > lmtJointMax[idx] - Math.toRadians(10)
              && axises[idx] < lmtJointMax[idx]) {
        m_processer.SoftModeOff();
        logger.info("check : SoftMode_Off");
        isNeedSoft = true;
        freeCheckStart = 0;
        ProtocolResult ret = new ProtocolResult();
        ret.setOperateType("NeedSoft");
        ret.setResultMsg(jtPos.toString());
        sendData(ret);
      }
    }
  }

  public ProtocolBean getMsgBean() {
    msgLock.readLock().lock();
    String line = msgQueque.poll();
    msgLock.readLock().unlock();

    ProtocolBean msgBean = null;
    if (line != null && !line.isEmpty()) {
      logger.info("recv_getMsg: " + line);
      msgBean = GsonUtil.json2Bean(line, ProtocolBean.class);
    }
    return msgBean;
  }

  public ProtocolBean peekMsgBean() {
    msgLock.readLock().lock();
    String line = msgQueque.peek();
    msgLock.readLock().unlock();

    ProtocolBean msgBean = null;
    if (line != null && !line.isEmpty()) {
      logger.info("recv_peekMsg: " + line);
      msgBean = GsonUtil.json2Bean(line, ProtocolBean.class);
    }
    return msgBean;
  }

  public void sendData(ProtocolResult result) {
    String jsonData = GsonUtil.bean2Json(result);
    logger.info("Json Result :" + jsonData);
    try {
      m_writer.write(jsonData);
      m_writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void exit() {
    //m_processer.exit();
  }

  @Override
  public void dispose() {
    List<ConditionObserver> list = observerManager.getRegisteredConditionObservers();

    for (ConditionObserver obs : list) {
      observerManager.removeConditionObserver(obs);
      logger.info("removeConditionObserver");
    }
  }

//  public void checkMasterState() {
//    double[] lmtJointMin = robot.getJointLimits().getMinJointPosition().toArray();
//    double[] lmtJointMax = robot.getJointLimits().getMaxJointPosition().toArray();
//
//    JointPosition jtPos = robot.getCurrentJointPosition();
//    double[] axises = jtPos.toArray();
//
//    for (int idx = 0; idx < 7; ++idx) {
//      if (axises[idx] < lmtJointMin[idx] || axises[idx] > lmtJointMax[idx]) {
//        if (mastering.isAxisMastered(idx)) {
//          boolean b = mastering.invalidateMastering(idx);
//          logger.info("mastering === " + b);
//        }
//      }
//    }
//
//    for (int idx = 0; idx < 7; ++idx) {
//      if (!mastering.isAxisMastered(idx)) {
//        if (!isSendMaster) {
//          logger.info("Axis " + idx + " need Master ");
//          ProtocolResult ret = new ProtocolResult();
//          ret.setOperateType("NeedMaster");
//          ret.setResultMsg(jtPos.toString());
//          sendData(ret);
//          isSendMaster = true;
//          logger.info(jtPos.toString());
//        }
//
//        break;
//      }
//    }
//  }
}
