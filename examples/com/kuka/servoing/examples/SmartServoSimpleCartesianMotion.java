package com.kuka.servoing.examples;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.ptp;

import com.kuka.device.common.JointPosition;
import com.kuka.geometry.Frame;
import com.kuka.geometry.LoadData;
import com.kuka.geometry.LocalFrame;
import com.kuka.geometry.ObjectFrame;
import com.kuka.geometry.Tool;
import com.kuka.math.geometry.Vector3D;
import com.kuka.math.geometry.XyzAbcTransformation;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.sensitivity.LBR;
import com.kuka.servoing.api.common.IServoMotion;
import com.kuka.servoing.api.common.IServoingCapability;
import com.kuka.servoing.api.smartservo.ISmartServo;
import com.kuka.servoing.api.smartservo.ISmartServoRuntime;
import com.kuka.task.ITaskLogger;
import com.kuka.threading.ThreadUtil;

import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

/**
 * This example activates a SmartServo motion in position control mode, sends a sequence of
 * Cartesian set points, describing a sine function and evaluates the statistic timing.
 * While not strictly required for position control mode, this example shows also how to perform the
 * load data validation, enabling to use impedance control modes with Servo motions.
 */
public class SmartServoSimpleCartesianMotion extends RoboticsAPIApplication {
  @Inject private LBR _robot;

  @Inject private IServoingCapability _servoingCapability;

  private ISmartServoRuntime _theSmartServoRuntime;
  private ITaskLogger _logger;

  private boolean _doDebugPrints = false;

  // Tool Data
  private Tool _toolAttachedToLbr;
  private LoadData _loadData;
  private static final String TOOL_FRAME = "toolFrame";
  private static final double[] TRANSLATION_OF_TOOL = { 0, 0, 100 };
  private static final double MASS = 0;
  private static final double[] CENTER_OF_MASS_IN_MILLIMETER = { 0, 0, 100 };

  private static final int MILLI_SLEEP_TO_EMULATE_COMPUTATIONAL_EFFORT = 30;
  private static final int NUM_RUNS = 1000;
  private static final double AMPLITUDE = 70;
  private static final double FREQUENCY = 0.6;

  private int _steps = 0;

  @Override
  public void initialize() {
    _logger = getLogger();

    // Create a Tool
    // This is the tool we want to move with some mass properties and a TCP-Z-offset of 100.
    _loadData = new LoadData();
    _loadData.setMass(MASS);
    _loadData.setCenterOfMass(CENTER_OF_MASS_IN_MILLIMETER[0], CENTER_OF_MASS_IN_MILLIMETER[1],
        CENTER_OF_MASS_IN_MILLIMETER[2]);
    _toolAttachedToLbr = new Tool("Tool", _loadData);

    XyzAbcTransformation trans = XyzAbcTransformation.of(TRANSLATION_OF_TOOL[0],
        TRANSLATION_OF_TOOL[1], TRANSLATION_OF_TOOL[2]);
    ObjectFrame toolTransformation = _toolAttachedToLbr.createFrame(TOOL_FRAME, trans);
    _toolAttachedToLbr.setDefaultMotionFrame(toolTransformation);
    // Attach tool to the robot
    _toolAttachedToLbr.attachTo(_robot.getFlange());
  }

  @Override
  public void dispose() {
    // Remove tool at the end of the application
    _toolAttachedToLbr.detach();
  }

  @Override
  public void run() {
    // Move to initial position
    // WARNING: make sure that the pose is collision free.
    _toolAttachedToLbr.move(
        ptp(JointPosition.ofDeg(0.0, 30.0, 0.0, -60.0, 0.0, 90.0, 0.0)).setJointVelocityRel(0.1));

    // Create a SmartServo motion to the current position
    // By default the servo will stay active 30 seconds to wait for a new target destination
    // This timing can be configured by the user
    JointPosition initialPosition = _robot.getCurrentJointPosition();
    ISmartServo smartServoMotion = _servoingCapability.createSmartServoMotion(initialPosition);

    smartServoMotion.setMinimumTrajectoryExecutionTime(5e-3);

    // Validate the load configuration at the current position
    performLoadValidation(smartServoMotion);

    _logger.info("Starting SmartServo motion in position control mode");
    _toolAttachedToLbr.moveAsync(smartServoMotion);

    _logger.info("Get the runtime of the SmartServo motion");
    _theSmartServoRuntime = smartServoMotion.getRuntime(500, TimeUnit.MILLISECONDS);

    Frame frame = _theSmartServoRuntime
        .getCurrentCartesianPosition(_toolAttachedToLbr.getDefaultMotionFrame());
    try {
      // do a cyclic loop
      // Refer to some timing in nanosec
      double omega = FREQUENCY * 2 * Math.PI * 1e-9;
      long startTimeStamp = System.nanoTime();
      for (_steps = 0; _steps < NUM_RUNS; ++_steps) {
        // ///////////////////////////////////////////////////////
        // Insert your code here
        // e.g Visual Servoing or the like
        /////////////////////////////////////////////////////////
        // emulate some computational effort
        ThreadUtil.milliSleep(MILLI_SLEEP_TO_EMULATE_COMPUTATIONAL_EFFORT);

        // Update the runtime information with the latest robot status
        _theSmartServoRuntime.updateWithRealtimeSystem();

        // do a cyclic loop
        long curTime = System.nanoTime() - startTimeStamp;
        double sinArgument = omega * curTime;

        // compute a new commanded position
        Frame destFrame = LocalFrame.copyOfWithRedundancy(frame, frame.getParent());
        double offset = AMPLITUDE * Math.sin(sinArgument);
        destFrame.translate(Vector3D.of(0, 0, offset));

        _theSmartServoRuntime.setDestination(destFrame);

        printDebugData();
      }
    } catch (Exception e) {
      getLogger().error(e.toString());
      e.printStackTrace();
    }

    // Print statistics and parameters of the motion
    _logger.info("Displaying final states after loop ");
    _logger.info(getClass().getName() + _theSmartServoRuntime.toString());

    // Stop the motion
    _logger.info("Stop the SmartServo motion");
    _theSmartServoRuntime.stopMotion();
  }

  /**
   * Try to perform the load data validation at the current joint position. The Validation itself
   * justifies that, in this very time instance, the load parameter setting was sufficient. This
   * does not mean that the parameter setting is going to be valid for the lifetime of this program.
   * 
   * @param servoMotion
   *        the servo motion to be validated
   */
  public void performLoadValidation(IServoMotion servoMotion) {
    try {
      servoMotion.validateForImpedanceMode(_toolAttachedToLbr);

      if (!servoMotion.isValidatedForImpedanceMode()) {
        _logger.info("Validation of load data failed - correct your mass property settings");
        _logger.info("SmartServo will be available for position controlled mode only, until"
            + " validation is performed");
      }
    } catch (IllegalStateException e) {
      _logger.info("Omitting validation failure for this example\n" + e.getMessage());
    }
  }

  /**
   * Print current system information, if the debug printing is enabled.
   */
  public void printDebugData() {
    // ////////////////////////////////////////////////////////////
    if (_doDebugPrints) {
      // Get the measured cartesian pose (robot root reference)
      Frame msrPose =
          _theSmartServoRuntime
              .getCurrentCartesianPosition(_toolAttachedToLbr.getDefaultMotionFrame());

      _logger
          .info("Current joint destination " + _theSmartServoRuntime.getCurrentJointDestination());
      _logger.info(
          "LBR position "
              + _robot.getCurrentCartesianPosition(_robot.getFlange(), _robot.getRootFrame()));
      _logger.info("Measured cartesian pose from runtime " + msrPose);

      if ((_steps % 100) == 0) {
        // Some internal values, which can be displayed
        getLogger()
            .info("Simple cartesian test - step " + _steps + _theSmartServoRuntime.toString());
      }
    }
  }
}
