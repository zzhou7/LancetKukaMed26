package protocols;

public class ProtocolResult {
	private int resultCode;
	private String resultMsg = "null";
	private String operateType = "null";
	private Param param = new Param();
	
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}	
	
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public Param getParam() {
		return param;
	}
	public void setParam(Param param) {
		this.param = param;
	}
	/*@Override
	public String toString() {
		return "ProtocolResult [resultCode=" + resultCode + ", resultMsg=" + resultMsg + ", param=" + param.toString() + "]";
	}	*/
}
