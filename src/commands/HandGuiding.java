package commands;

import static com.kuka.handguiding.motion.HRCMotions.handGuiding;

import com.kuka.geometry.World;
import com.kuka.med.devicemodel.LBRMed;
import com.kuka.med.lbrmed.precisehandguiding.api.IPreciseHandguiding;
import com.kuka.med.lbrmed.precisehandguiding.api.IPreciseHandguidingMotion;
import com.kuka.motion.IMotionContainer;
import com.kuka.task.ITaskLogger;
import javax.inject.Inject;
import protocols.ProtocolResult;
import units.AbstractCommand;
import units.AbstractCommandEx;

public class HandGuiding extends AbstractCommandEx {
  @Inject private LBRMed robot;
  @Inject private ITaskLogger logger;
  @Inject private World world;
  @Override
  public String GetNameString() {
    return "HandGuiding";
  }

  @Override
  public AbstractCommand CreateCommand() {
    return new HandGuiding();
  }

  @Override
  public ProtocolResult Execute(Object protocol) {
    
    IPreciseHandguiding phCapability =robot.getCapability(IPreciseHandguiding.class);
    IPreciseHandguidingMotion handguidingMotion = phCapability.createMotion();
    IMotionContainer mc = robot.move(handguidingMotion);
    
    
//    robot.move(handGuiding().setJointLimitViolationFreezesAll(false));
//    
//
//    
    
    ProtocolResult ret = new ProtocolResult();
    ret.setOperateType("HandGuiding");   
    ret.setResultMsg("HandGuiding succeed exit");
    return ret;
  }

}
