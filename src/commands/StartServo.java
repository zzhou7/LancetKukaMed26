package commands;

import ControlMode.ServoMode;
import com.kuka.device.common.JointPosition;
import com.kuka.geometry.Frame;
import com.kuka.geometry.LocalFrame;
import com.kuka.geometry.Tool;
import com.kuka.geometry.World;
import com.kuka.math.geometry.Vector3D;
import com.kuka.med.devicemodel.LBRMed;
import com.kuka.motion.IMotionContainer;
import com.kuka.servoing.api.common.IServoMotion;
import com.kuka.servoing.api.common.IServoingCapability;
import com.kuka.servoing.api.smartservo.ISmartServo;
import com.kuka.servoing.api.smartservo.ISmartServoRuntime;
import com.kuka.task.ITaskLogger;
import com.kuka.threading.ThreadUtil;
import functions.FriManager;
import functions.MotionManager;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import protocols.DefualtProtocol;
import protocols.ProtocolResult;
import units.AbstractCommand;
import units.AbstractCommandEx;

public class StartServo extends AbstractCommandEx {
  @Inject private LBRMed robot;
  @Inject private ITaskLogger logger;
  @Inject private World world;
  @Inject private FriManager friManager;
  @Inject private ServoMode servoMode;
  
  private boolean m_debug = false;
  private boolean m_isOffset = false;
  private Tool tool;
  
  @Override
  public String GetNameString() {
    return "StartServo";
  }

  @Override
  public AbstractCommand CreateCommand() {
    return new StartServo();
  }

  @Override
  public ProtocolResult Execute(Object protocol) {
    DefualtProtocol p = (DefualtProtocol) protocol;
    tool = (Tool) robot.findObject("tool");
    
    Frame initialPosition = robot.getCurrentCartesianPosition(tool.getDefaultMotionFrame(), world.getRootFrame());
    // Create a SmartServo motion to the current position
    // By default the servo will stay active 30 seconds to wait for a new target destination
    // This timing can be configured by the user
    servoMode.initialize();
    servoMode.enterMode();
 
    Thread thrd = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          while (servoMode.isModeOn()) {
            if(m_isOffset) {
              Frame offset = friManager.GetFriDynamicFrame();
              
//              logger.info("initialPos");
//              logger.info(initialPosition.toString());
              
              Frame destFrame = LocalFrame.copyOfWithRedundancy(initialPosition, initialPosition.getParent());
              
              Vector3D v = offset.getTransformationFromParent().getTranslation();
              destFrame.translate(v);
//              logger.info("offset");
//              logger.info(v.toString());
//              logger.info("destFrame");
//              logger.info(destFrame.toString());

              servoMode.setNewDestination(destFrame);  
            } else {
              Frame destFrame = friManager.GetFriDynamicFrame();
              servoMode.setNewDestination(destFrame);  
            } 
            if(m_debug) {
              servoMode.printDebugData();
            }
          }
        } catch (Exception e) {
          logger.error("servo setNewDestination error,exit mode and close fri session");
          logger.error(e.toString());
          servoMode.exitMode();      
        }
      }
    });
    thrd.start(); 
 
    ProtocolResult ret = new ProtocolResult();
    ret.setOperateType(p.getOperateType());    
    ret.setResultMsg("Servo Start");
    ret.setResultCode(0);
    return ret;
  }
}
