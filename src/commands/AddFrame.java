package commands;

import com.kuka.geometry.ObjectFrame;
import com.kuka.geometry.Tool;
import com.kuka.geometry.World;
import com.kuka.math.geometry.Transformation;
import com.kuka.med.devicemodel.LBRMed;
import com.kuka.task.ITaskLogger;
import javax.inject.Inject;
import protocols.DefualtProtocol;
import protocols.Param;
import protocols.ProtocolResult;
import units.AbstractCommand;
import units.AbstractCommandEx;

public class AddFrame extends AbstractCommandEx {
  @Inject private LBRMed robot;
  @Inject private ITaskLogger logger;
  @Inject private World world;
  
  @Override
  public String GetNameString() {
    return "AddFrame";
  }

  @Override
  public AbstractCommand CreateCommand() {
    return new AddFrame();
  }

  @Override
  public ProtocolResult Execute(Object protocol) {
    DefualtProtocol p = (DefualtProtocol) protocol;
    Tool tool = (Tool) robot.findObject("tool");
    
    Param param = p.getParam();
    ObjectFrame frame = null;
    ProtocolResult ret = new ProtocolResult();
    ret.setOperateType("AddFrame");  
    ret.setResultMsg("Add Frame:"+param.target);
    
    try {
      frame = tool.findFrame(param.target);
    } catch (Exception e){
      logger.info("Frame not exist: "+param.target);
      logger.info(e.toString());
    }
    if(frame != null) {
      tool.removeFrame(frame);
      ret.setResultMsg("Set Frame:"+param.target);
    } 
    tool.createFrame(param.target, 
        Transformation.ofRad(param.getX(), param.getY(), param.getZ(), 
            param.getA(),param.getB(), param.getC()));

    ret.setResultCode(0);
    return ret;
  }

}
