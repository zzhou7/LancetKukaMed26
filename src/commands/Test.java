package commands;

import com.kuka.med.devicemodel.LBRMed;
import com.kuka.task.ITaskLogger;
import javax.inject.Inject;
import protocols.GsonUtil;
import protocols.DefualtProtocol;
import protocols.ProtocolResult;
import units.AbstractCommand;
import units.AbstractCommandEx;

public class Test extends AbstractCommandEx {

  @Inject private LBRMed robot;
  @Inject private ITaskLogger logger;
  @Override
  public AbstractCommand CreateCommand() {
      return new Test();
  }

  @Override
  public ProtocolResult Execute(Object protocol) {
    DefualtProtocol p = (DefualtProtocol) protocol;
    logger.info(GsonUtil.jsonFormatter(GsonUtil.bean2Json(p)));
    logger.info("Execute command: " + p.getOperateType());
    logger.info(robot.getCurrentCartesianPosition(robot.getFlange()).getTransformationFromParent().toString());
    
    ProtocolResult ret = new ProtocolResult();
    ret.setOperateType("Test");
    ret.setResultMsg("Test ok");
    ret.setResultCode(0);
    return ret;
  }

  @Override
  public String GetNameString() {
    return "Test";
  }   
}
