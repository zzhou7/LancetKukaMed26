package commandHandle;

import java.util.HashMap;
import java.util.Map;
import protocols.GsonUtil;
import protocols.ProtocolBean;
import units.AbstractCommandParameter;

public class CommandProtocolFactory {
    
    /**
     * Resource Object Container Required for Command Running.
     * 
     * This container will register runtime resources of all commands. When the model 
     * runs automatically, it will tell the corresponding resources to the command 
     * function execution object in the form of command parameter object.
     */
    private Map<String, Object> m_mapRuntimeProperties = new HashMap<String, Object>();

    /**
     * Command parameter class work instruction container.
     * 
     * A work instruction containing all the required command parameter classes, 
     * according to which the target product can be perfectly copied.
     * 
     * @see commandHandle.CommandProtocolFactory#makeProtocol(String, String)
     * @see commandHandle.CommandProtocolFactory#RegisterParameter(AbstractCommandParameter)
     * @see commandHandle.CommandProtocolFactory#unRegisterProtocol(AbstractCommandParameter)
     * 
     * @see commandHandle.CommandProtocolFactory#commandParameters
     */
    private Map<String, Object> commandParameters = new HashMap<String, Object>();

    /**
     * Register the target parameter to the factory production line, or it can be 
     * understood as the operation instructions for delivering the factory target 
     * products, to ensure that the factory has the ability to produce the target 
     * products.
     * 
     * @param protocol Object instance of registration target.
     * @return If the registration is successful, return true; otherwise, release.
     * 
     * @see units.AbstractCommandParameter
     * @see commandHandle.CommandProtocolFactory#commandParameters
     */
    public boolean registerProtocol(String commandName, Object protocol) {

        if(null == protocol || null == commandName) {
            return false;
           
        }
        if(false == this.commandParameters.containsKey(commandName.toLowerCase())) {
            this.commandParameters.put(commandName.toLowerCase(), protocol);
        }
        return true;
    }
    
    /**
     * Unload the target product to the factory production line, or understand the 
     * factory's ability to stop producing the target product.
     * 
     * @param parameter Object instance of registration target.
     * @return If the registration is successful, return true; otherwise, release.
     * 
     * @see units.AbstractCommandParameter
     * @see commandHandle.CommandProtocolFactory#commandParameters
     */
    public boolean unRegisterProtocol(AbstractCommandParameter parameter) {

        if(null == parameter) {
            return false;
        }
        if(false == this.commandParameters.containsKey(parameter.getClass().getSimpleName().toLowerCase())) {
            this.commandParameters.remove(parameter.getClass().getSimpleName().toLowerCase());
        }
        return true;
    }

    /**
     * To produce the target product instance, the factory will look for the production 
     * mode in all the operation instructions, and the production will be smooth. The 
     * factory will deliver an external AbstractCommand object instance, otherwise it 
     * will deliver  a null object.
     * 
     * 
     * @param commandName Characteristics of the target product, such as brand.
     * @param datas Parameter string object passed by remote PC.
     * @return AbstractCommandParameter object.
     * 
     * @see units.AbstractCommandParameter
     * @see commandHandle.CommandProtocolFactory#commandParameters
     */
    public Object makeProtocol(String commandName, String massage) {

        if(null != commandName) {

            if(true == this.commandParameters.containsKey(commandName.toLowerCase())) {
                Object protocol = GsonUtil.json2Bean(massage, this.commandParameters.get(commandName.toLowerCase()).getClass());
                return protocol;
            }
        }
        return null;
    }

    
    /**
     * Register the target parameter to the factory production line, or it can be 
     * understood as the operation instructions for delivering the factory target 
     * products, to ensure that the factory has the ability to produce the target 
     * products.
     * 
     * @param parameter Object instance of registration target.
     * @return If the registration is successful, return true; otherwise, release.
     * 
     * @see commandHandle.CommandProtocolFactory#m_mapRuntimeProperties
     */
    public boolean RegisterRunTimeProperty(Object o) {

        if(null == o) {
            return false;
        }
        System.out.println("[DEBUG] CommandProtocolFactory.RegisterRunTimeProperty " + o.getClass().getSimpleName().toLowerCase());
        this.m_mapRuntimeProperties.put(o.getClass().getSimpleName().toLowerCase(), o);
        return true;
    }
    
    /**
     * Unload the target product to the factory production line, or understand the 
     * factory's ability to stop producing the target product.
     * 
     * @param parameter Object instance of registration target.
     * @return If the registration is successful, return true; otherwise, release.
     * 
     * @see commandHandle.CommandProtocolFactory#m_mapRuntimeProperties
     */
    public boolean UnRegisterRunTimeProperty(Object o) {

        if(null == o) {
            return false;
        }
        if(false == this.m_mapRuntimeProperties.containsKey(o.getClass().getSimpleName().toLowerCase())) {
            this.m_mapRuntimeProperties.remove(o.getClass().getSimpleName().toLowerCase());
        }
        return true;
    }
}
