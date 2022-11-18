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
import com.kuka.servoing.api.common.IServoingCapability;
import com.kuka.servoing.api.smartservolin.ISmartServoLin;
import com.kuka.servoing.api.smartservolin.ISmartServoLinRuntime;
import com.kuka.task.ITaskLogger;
import com.kuka.threading.ThreadUtil;
import com.kuka.time.StatisticTimer;
import com.kuka.time.StatisticTimer.OneTimeStep;

import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

/**
 * This sample activates a SmartServoLIN motion in position control mode, sends a sequence of
 * Cartesian set points, describing a sine function in z-direction and evaluates the statistic
 * timing.
 */
public class SmartServoLinSimpleMotion extends RoboticsAPIApplication {
  @Inject private LBR _lbr;

  @Inject private IServoingCapability _servoingCapability;

  private Tool _toolAttachedToLbr;
  private LoadData _loadData;
  private ISmartServoLinRuntime _smartServoLinRuntime;
  private ITaskLogger _logger;

  // Tool Data
  private static final String TOOL_FRAME = "toolFrame";
  private static final double[] TRANSLATION_OF_TOOL = { 0, 0, 100 };
  private static final double MASS = 0;
  private static final double[] CENTER_OF_MASS_IN_MILLIMETER = { 0, 0, 100 };

  private static final int NUM_RUNS = 600;
  private static final double AMPLITUDE = 70;
  private static final double FREQUENCY = 0.6;

  private static final int MILLI_SLEEP_TO_EMULATE_COMPUTATIONAL_EFFORT = 30;

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
    _toolAttachedToLbr.attachTo(_lbr.getFlange());
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

    // Create a SmartServoLin motion to the current position
    // By default the servo will stay active 30 seconds to wait for a new target destination
    // This timing can be configured by the user
    LocalFrame initialPosition = _lbr.getCurrentCartesianPosition(_lbr.getFlange());
    ISmartServoLin smartServoLinMotion =
        _servoingCapability.createSmartServoLinMotion(initialPosition);

    smartServoLinMotion.setMinimumTrajectoryExecutionTime(20e-3);

    _logger.info("Starting the SmartServoLIN in position control mode");
    _lbr.getFlange().moveAsync(smartServoLinMotion);

    _logger.info("Get the runtime of the SmartServoLIN motion");
    _smartServoLinRuntime = smartServoLinMotion.getRuntime(500, TimeUnit.MILLISECONDS);

    StatisticTimer timing = new StatisticTimer();

    // Start the smart servo lin sine movement
    timing = startSineMovement(timing);

    ThreadUtil.milliSleep(1000);

    _logger.info("Print statistic timing");
    _logger.info(getClass().getName() + _smartServoLinRuntime.toString());

    getLogger().info("Stop the SmartServoLIN motion");
    _smartServoLinRuntime.stopMotion();

    // Statistic Timing of sine movement loop
    if (timing.getMeanMillis() > 150) {
      _logger.info(
          "Statistic Timing is unexpectedly slow, you should try to optimize TCP/IP Transfer");
      _logger
          .info("Under Windows, you should change the registry settings, see the e.g. user manual");
    }
  }

  private StatisticTimer startSineMovement(StatisticTimer timing) {

    Frame frame = _smartServoLinRuntime
        .getCurrentCartesianPosition(_toolAttachedToLbr.getDefaultMotionFrame());

    getLogger().info("Do sine movement");
    try {
      // do a cyclic loop
      // Refer to some timing in nanosec
      double omega = FREQUENCY * 2 * Math.PI * 1e-9;
      long startTimeStamp = System.nanoTime();

      for (int i = 0; i < NUM_RUNS; ++i) {
        final OneTimeStep aStep = timing.newTimeStep();
        // ///////////////////////////////////////////////////////
        // Insert your code here
        // e.g Visual Servoing or the like
        /////////////////////////////////////////////////////////
        // emulate some computational effort
        ThreadUtil.milliSleep(MILLI_SLEEP_TO_EMULATE_COMPUTATIONAL_EFFORT);

        // Update the runtime information with the latest robot status
        _smartServoLinRuntime.updateWithRealtimeSystem();

        // Compute the next target pose
        double curTime = System.nanoTime() - startTimeStamp;
        double sinArgument = omega * curTime;

        Frame destFrame = LocalFrame.copyOfWithRedundancy(frame, frame.getParent());
        double offset = AMPLITUDE * Math.sin(sinArgument);
        destFrame.translate(Vector3D.of(0, 0, offset));

        // Set new destination
        _smartServoLinRuntime.setDestination(destFrame);

        aStep.end();
      }

    } catch (Exception e) {
      getLogger().error(e.getLocalizedMessage());
      e.printStackTrace();
    }
    return timing;
  }
}
