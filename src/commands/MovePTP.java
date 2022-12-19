package commands;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.ptp;

import com.kuka.geometry.LocalFrame;
import com.kuka.geometry.World;
import com.kuka.math.geometry.ITransformation;
import com.kuka.math.geometry.Transformation;
import com.kuka.med.devicemodel.LBRMed;
import com.kuka.motion.IMotionContainer;
import com.kuka.task.ITaskLogger;
import functions.MotionManager;
import javax.inject.Inject;
import protocols.Param;
import protocols.DefualtProtocol;
import protocols.ProtocolResult;
import units.AbstractCommand;
import units.AbstractCommandEx;

public class MovePTP extends AbstractCommandEx {
  @Inject private LBRMed robot;
  @Inject private ITaskLogger logger;
  @Inject private World world;
  
  @Inject private MotionManager motionManager;
  
  @Override
  public String GetNameString() {
    return "MovePTP";
  }

  @Override
  public AbstractCommand CreateCommand() {   
    return new MovePTP();
  }

  @Override
  public ProtocolResult Execute(Object protocol) {
    DefualtProtocol p = (DefualtProtocol) protocol;
    
    
    if (motionManager.getMotionContainer() != null) { 
      motionManager.getMotionContainer().cancel();
    }
    Param param =p.getParam();
    ITransformation tans = Transformation.ofRad(param.x,param.y,param.z,param.a,param.b,param.c);
    LocalFrame target = new LocalFrame(world.getRootFrame(),tans);
    
    IMotionContainer mc = robot.moveAsync(ptp(target)
        .setJointVelocityRel(0.05).setJointAccelerationRel(0.02));
    
    motionManager.setMotionContainer(mc);
    
    ProtocolResult ret = new ProtocolResult();
    ret.setOperateType("MovePTP");    
    ret.setResultMsg("MovePTP ok");
    ret.setResultCode(0);
    return ret;
  }
}
