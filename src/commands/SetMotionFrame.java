package commands;

import com.kuka.geometry.Tool;
import com.kuka.geometry.World;
import com.kuka.med.devicemodel.LBRMed;
import com.kuka.task.ITaskLogger;
import javax.inject.Inject;
import protocols.DefualtProtocol;
import protocols.ProtocolResult;
import units.AbstractCommand;
import units.AbstractCommandEx;

public class SetMotionFrame extends AbstractCommandEx {
  @Inject private LBRMed robot;
  @Inject private ITaskLogger logger;
  @Inject private World world;
  
  @Override
  public String GetNameString() {
    return "SetMotionFrame";
  }

  @Override
  public AbstractCommand CreateCommand() {
    return new SetMotionFrame();
  }

  @Override
  public ProtocolResult Execute(Object protocol) {
    DefualtProtocol p = (DefualtProtocol) protocol;
    Tool tool = (Tool) robot.findObject("tool");
    try {
      tool.setDefaultMotionFrame(tool.findFrame(p.getParam().target));
    } catch (Exception e) {
      logger.info("Frame not exist: " + p.getParam().target);
      ProtocolResult ret = new ProtocolResult();
      ret.setOperateType("SetMotionFrame");  
      ret.setResultMsg("Failed,Frame:" + p.getParam().target);
      ret.setResultCode(-1);
      return ret;
    }
    ProtocolResult ret = new ProtocolResult();
    ret.setOperateType("SetMotionFrame");
    ret.setResultMsg("Success,Frame:" + p.getParam().target);
    ret.setResultCode(0);
    return ret;
  }
}
