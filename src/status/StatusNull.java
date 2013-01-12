package status;

public class StatusNull extends Status {

	private static final StatusNull _instance = new StatusNull();
	
	public static StatusNull getInstance() {
		return _instance;
	}
	
	public boolean isNull() {
		return true;
	}
	
	public boolean isNeedToRecord() {
		return false;
	}
	
	public EStatus getCode() {
		return EStatus.Null;
	}

	public String statusToString() {
		return "(null)";
	}

	public String statusToShortString() {
		return "(null)";
	}

	public boolean isExpectedStatus(String str) {
		return false;
	}
	
	private StatusNull() { }
}
