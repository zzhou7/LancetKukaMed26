package protocols;

import java.util.List;
import java.util.Map;

import units.AbstractCommandParameter;
import units.AbstractCommandParameterEx;
import units.AbstractCommandParameterProperty;

public class BasicCommandProtocol extends AbstractCommandParameterEx {

    public BasicCommandProtocol() { }

    public BasicCommandProtocol(String parame) {
        this.SetInputString(parame);
    }

    @Override
    public AbstractCommandParameter CreateCommandParameter(String parame, Map<String, Object> mapRuntimeProperties) {

        return new BasicCommandProtocol(parame);
    }

    @Override
    public String GetErrorString() {
        return "Non";
    }

    @Override
    public List<AbstractCommandParameterProperty> CreateProperty(String parameter) {
        return null;
    }

	@Override
	public boolean IsSecurity() {
		return false;
	}    
}
