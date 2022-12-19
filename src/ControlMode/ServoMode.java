package ControlMode;

import com.kuka.device.common.JointPosition;
import com.kuka.geometry.Frame;
import com.kuka.geometry.Tool;
import com.kuka.geometry.World;
import com.kuka.med.devicemodel.LBRMed;
import com.kuka.motion.IMotionContainer;
import com.kuka.servoing.api.common.IServoMotion;
import com.kuka.servoing.api.common.IServoingCapability;
import com.kuka.servoing.api.smartservo.ISmartServo;
import com.kuka.servoing.api.smartservo.ISmartServoRuntime;
import com.kuka.task.ITaskLogger;
import functions.FriManager;
import functions.MotionManager;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ServoMode {
  @Inject private LBRMed robot;
  @Inject private ITaskLogger logger;
  @Inject private IServoingCapability servoingCapability;
  @Inject private MotionManager motionManager;
  
  private ISmartServoRuntime m_theSmartServoRuntime;
  private int m_debugSteps = 0;
  private ISmartServo m_smartServoMotion;
  private Tool tool;
  private boolean m_ModeOn = false;
  
  public void initialize() {
    tool = (Tool) robot.findObject("tool");
    JointPosition initialPosition = robot.getCurrentJointPosition();
    m_smartServoMotion = servoingCapability.createSmartServoMotion(initialPosition);
    m_smartServoMotion.setMinimumTrajectoryExecutionTime(5e-3);
 // Validate the load configuration at the current position
    performLoadValidation(m_smartServoMotion);
  }
  
  public void enterMode() {
    logger.info("enter SmartServo mode in position control mode");
    IMotionContainer mc = tool.moveAsync(m_smartServoMotion);
    motionManager.setMotionContainer(mc);

    logger.info("Get the runtime of the SmartServo motion");
    m_theSmartServoRuntime = m_smartServoMotion.getRuntime(500, TimeUnit.MILLISECONDS);
    m_ModeOn = true;
  }
  
  public void setNewDestination(Frame destFrame) {
    m_theSmartServoRuntime.setDestination(destFrame);
  }
  
  public void exitMode() {
    logger.info("Stop the SmartServo motion");
    m_ModeOn = false;
    m_theSmartServoRuntime.stopMotion();
  }
  
  public boolean isModeOn() {
    return m_ModeOn;
  }
  
  public void printServoInfo() {
    // Print statistics and parameters of the motion
    logger.info(getClass().getName() + m_theSmartServoRuntime.toString());
  }
  
  public void performLoadValidation(IServoMotion servoMotion) {
    try {
      servoMotion.validateForImpedanceMode(tool);

      if (!servoMotion.isValidatedForImpedanceMode()) {
        logger.info("Validation of load data failed - correct your mass property settings");
        logger.info("SmartServo will be available for position controlled mode only, until"
            + " validation is performed");
      }
    } catch (IllegalStateException e) {
      logger.info("Omitting validation failure for this example\n" + e.getMessage());
    }
  }
  
  /**
   * Print current system information, if the debug printing is enabled.
   */
  public void printDebugData() {
    m_debugSteps++;
    // Get the measured cartesian pose (robot root reference)
    Frame msrPose =
        m_theSmartServoRuntime
            .getCurrentCartesianPosition(tool.getDefaultMotionFrame());

    logger
        .info("Current joint destination " + m_theSmartServoRuntime.getCurrentJointDestination());
    logger.info(
        "LBR position "
            + robot.getCurrentCartesianPosition(tool.getDefaultMotionFrame(), robot.getRootFrame()));
    logger.info("Measured cartesian pose from runtime " + msrPose);

    if ((m_debugSteps % 100) == 0) {
      // Some internal values, which can be displayed
      logger
          .info("ServoRuntime info - step " + m_debugSteps + m_theSmartServoRuntime.toString());
    }     
  }
}
