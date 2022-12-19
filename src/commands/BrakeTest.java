package commands;

import protocols.ProtocolResult;
import units.AbstractCommand;
import units.AbstractCommandEx;

import com.kuka.device.common.JointPosition;
import com.kuka.geometry.World;
import com.kuka.med.cyclicbraketest.EBraketestExecutionState;
import com.kuka.med.cyclicbraketest.EBraketestMonitorState;
import com.kuka.med.cyclicbraketest.EBraketestResult;
import com.kuka.med.cyclicbraketest.IBraketestEvent;
import com.kuka.med.cyclicbraketest.IBraketestMonitor;
import com.kuka.med.cyclicbraketest.IBraketestMonitorEvent;
import com.kuka.med.cyclicbraketest.IBraketestMonitorListener;
import com.kuka.med.devicemodel.LBRMed;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import com.kuka.roboticsAPI.uiModel.ApplicationDialogType;
import com.kuka.roboticsAPI.uiModel.IApplicationUI;
import com.kuka.task.ITaskLogger;
import javax.inject.Inject;
public class BrakeTest extends AbstractCommandEx {
  
  @Inject private LBRMed robot;
  @Inject private ITaskLogger logger;
  @Inject private World world;
  @Inject private IBraketestMonitor _brakeTestMonitor;
  @Inject private static JointPosition HOME = new JointPosition(0, 0, 0, 0, 0, 0, 0);

  @Override
  public String GetNameString() {
    // TODO Auto-generated method stub
    return "BrakeTest";
  }

  @Override
  public AbstractCommand CreateCommand() {
    // TODO Auto-generated method stub
    return new BrakeTest();
  }

  @Override
  public ProtocolResult Execute(Object protocol) {
    
    JointPosition target = null;
    target = HOME;
    // execute the brake test
//    _brakeTestMonitor.executeSingleBraketestOnJoint(option);
    EBraketestExecutionState outcome = _brakeTestMonitor.executeBraketest(target);
    logger.info(String.format("The overall result of the brake test is '%s'!", outcome.toString()));
    
    ProtocolResult ret = new ProtocolResult();
    ret.setOperateType("BrakeTest");    
    ret.setResultMsg("BrakeTest ok");
    ret.setResultCode(0);
    return ret;
  }

}
