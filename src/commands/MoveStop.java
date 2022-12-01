package commands;

import com.kuka.geometry.World;
import com.kuka.med.devicemodel.LBRMed;
import com.kuka.task.ITaskLogger;
import functions.MotionManager;
import javax.inject.Inject;
import protocols.ProtocolResult;
import units.AbstractCommand;
import units.AbstractCommandEx;

public class MoveStop extends AbstractCommandEx {
  @Inject private LBRMed robot;
  @Inject private ITaskLogger logger;
  @Inject private World world;
  @Inject private MotionManager motionManager;
  
  @Override
  public String GetNameString() {
    return "MoveStop";
  }

  @Override
  public AbstractCommand CreateCommand() {
    return new MoveStop();
  }

  @Override
  public ProtocolResult Execute(Object protocol) {
    motionManager.getMotionContainer().cancel();
    ProtocolResult ret = new ProtocolResult();
    ret.setOperateType("MoveStop");
    ret.setResultCode(0);
    return ret; 
  }

}
