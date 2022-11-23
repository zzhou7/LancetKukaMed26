package application;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.ptpHome;

import backgroundTask.UDPSocketForBackground;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.applicationModel.tasks.IRoboticsAPITaskInjectableTypes;
import com.kuka.device.ForceData;
import com.kuka.device.common.JointPosition;
import com.kuka.geometry.LoadData;
import com.kuka.geometry.ObjectFrame;
import com.kuka.geometry.Tool;
import com.kuka.math.geometry.ITransformation;
import com.kuka.math.geometry.Transformation;
import com.kuka.med.devicemodel.LBRMed;
import com.kuka.scenegraph.ISceneGraph;
import com.kuka.task.ITaskLogger;
import java.io.IOException;
import java.net.SocketException;
import javax.inject.Inject;
import protocols.robotInformation;
import protocols.trackingFrame;

/**
 * Implementation of a robot application.
 * 
 * <p>The application provides an {@link #initialize()} and a {@link #run()} method, which will be
 * called successively in the application life cycle. The application will terminate automatically
 * after the {@link #run()} method has finished or after stopping the task. The {@link #dispose()}
 * method will be called, even if an exception is thrown during initialization or run.
 * 
 * @see IRoboticsAPITaskInjectableTypes Types and Services available for Dependency Injection
 * @see RoboticsAPIApplication Application specific services available for Dependency Injection
 */
public class backgroudTest extends RoboticsAPIApplication {
  @Inject private LBRMed robot;
  @Inject private ISceneGraph sceneGraph;
  private UDPSocketForBackground soc;
  @Inject 
  private ITaskLogger logger;
  private Tool tool;
  @Override
  public void initialize() throws Exception {
    // Cleans the scene graph by removing all transient objects
    sceneGraph.clean();
//    ITransformation tans = Transformation.ofDeg(0, 0, 20, 0, 0, 0);
//    //  LoadData loadRobot =  robot.getLoadData();
//    LoadData loadRobot =  new LoadData();
//    loadRobot.setCenterOfMass(tans);
//    loadRobot.setMass(0.676);
//    tool = new Tool("tool", loadRobot);
//    logger.info(tool.getLoadData().toString());
//    tool.attachTo(robot.getFlange());
    // TODO Initialize your application here
    try {
      soc = new UDPSocketForBackground("172.31.1.148", 30003);
    } catch (SocketException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void run() throws Exception {
    // TODO Your application execution starts here
    //execution starts here
    robotInformation info = new robotInformation();
    
    //get joint position
    JointPosition jointPosition = robot.getCurrentJointPosition();  
    info.joints = jointPosition.toArray();
    
    //get force & torque
    ForceData forceData = robot.getExternalForceTorque(robot.getFlange());
    info.forcetorque[0] = forceData.getForce().getX();
    info.forcetorque[1] = forceData.getForce().getY();
    info.forcetorque[2] = forceData.getForce().getZ();
    info.forcetorque[3] = forceData.getTorque().getX();
    info.forcetorque[4] = forceData.getTorque().getY();
    info.forcetorque[5] = forceData.getTorque().getZ();
    
    
    try {
      tool = (Tool)robot.findObject("tool");
    } catch (Exception e) {
      logger.info("cant find tool");
      e.printStackTrace();
    }
    if (tool == null) {
      //no frame in tool added,return flange position
      Transformation trans = robot.getCurrentCartesianPosition(robot.getFlange()).getTransformationFromParent();
      trackingFrame f = new trackingFrame();
      f.name = robot.getFlange().getName();
      f.position[0] = trans.getX();
      f.position[1] = trans.getY();
      f.position[2] = trans.getZ();
      f.position[3] = trans.getAlphaRad();
      f.position[4] = trans.getBetaRad();
      f.position[5] = trans.getGammaRad();
      f.isMotionFrame = true;
      info.frames.add(f);
    } else {
      //find all frames link to tool and set positions to info
      for (ObjectFrame frame : tool.findFrames()) {
        Transformation trans = robot.getCurrentCartesianPosition(frame).getTransformationFromParent();
        trackingFrame f = new trackingFrame();
        f.name = frame.getName();
        f.position[0] = trans.getX();
        f.position[1] = trans.getY();
        f.position[2] = trans.getZ();
        f.position[3] = trans.getAlphaRad();
        f.position[4] = trans.getBetaRad();
        f.position[5] = trans.getGammaRad();
        if (frame.equals(tool.getDefaultMotionFrame())) {
          f.isMotionFrame = true;
        }
        info.frames.add(f);
      }
    }
    
    try {
      soc.send(GsonUtil.bean2Json(info).getBytes());
      logger.info(GsonUtil.bean2Json(info));
    } catch (IOException e) {    
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
