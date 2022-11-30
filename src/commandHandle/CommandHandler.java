package commandHandle;

import commands.Test;
import javax.inject.Inject;
import protocols.GsonUtil;
import protocols.ProtocolResult;
import units.AbstractCommand;

public class CommandHandler {
  
  public CommandHandler() {}
  
  
  private boolean m_isDebug = false;
  public CommandFactory commandFactory = new CommandFactory();
  public CommandProtocolFactory commandProtocolFactory = new CommandProtocolFactory();

  public ProtocolResult PushCommandToHandler(String commandString) {
    ProtocolResult res =new ProtocolResult();
   if(m_isDebug) {
    System.out.println("[INFO] Try to parse and process the target command" + commandString);
    	}
    	String commandName = GsonUtil.getStringValue(commandString, "operateType");
    	if(commandName.isEmpty()) {
    	  System.out.println("[ERROR] can not parse commandName");
    	}
    	Object protocol = commandProtocolFactory.makeProtocol(commandName, commandString);
        //String operateType = this.AnalysisCommandNameOfString(commandString);
        // Make command object.
      AbstractCommand command = commandFactory.GetCommand(commandName);

    	if(m_isDebug) {
    		System.out.println("[INFO] Attempt to create a processing object for the target command; string " + commandName);
    	}
    	System.out.println("[INFO] Execute command: " + commandName);
      res = command.Execute(protocol);
      // TODO: The execution return results need to be fed back to the upper computer.
      return res;     
    }


}