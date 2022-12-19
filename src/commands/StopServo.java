package commands;

import ControlMode.ServoMode;
import com.kuka.geometry.Tool;
import com.kuka.geometry.World;
import com.kuka.med.devicemodel.LBRMed;
import com.kuka.task.ITaskLogger;
import functions.FriManager;
import javax.inject.Inject;
import protocols.DefualtProtocol;
import protocols.ProtocolResult;
import units.AbstractCommand;
import units.AbstractCommandEx;

public class StopServo extends AbstractCommandEx {
  @Inject private LBRMed robot;
  @Inject private ITaskLogger logger;
  @Inject private World world;
  @Inject private ServoMode servoMode;
  @Override
  public String GetNameString() {
    return "StopServo";
  }

  @Override
  public AbstractCommand CreateCommand() {
    return new StopServo();
  }

  @Override
  public ProtocolResult Execute(Object protocol) {
    DefualtProtocol p = (DefualtProtocol) protocol;
    
    try {
      servoMode.exitMode();
    } catch (Exception e) {
      logger.error("stop servo error");
      logger.error(e.toString());
      ProtocolResult ret = new ProtocolResult();
      ret.setOperateType(p.getOperateType());    
      ret.setResultMsg("stop servo error" + e.toString());
      ret.setResultCode(-1);
      return ret;
    }
    
    ProtocolResult ret = new ProtocolResult();
    ret.setOperateType(p.getOperateType());    
    ret.setResultMsg("seccuss");
    ret.setResultCode(0);
    return ret;

  }

}
