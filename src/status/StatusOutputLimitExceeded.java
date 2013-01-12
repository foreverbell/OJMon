package status;

public class StatusOutputLimitExceeded extends Status {

	public EStatus getCode() {
		return EStatus.OutputLimitExceeded;
	}

	public String statusToString() {
		return "OutputLimitExceeded";
	}

	public String statusToShortString() {
		return "OLE";
	}
	
	public boolean isExpectedStatus(String str) {
		if (str.contains("outputlimitexceed")) return true;
		return false;
	}
}
