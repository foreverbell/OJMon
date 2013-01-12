package status;

public class StatusSystemError extends Status {
	
	public EStatus getCode() {
		return EStatus.SystemError;
	}

	public String statusToString() {
		return "SystemError";
	}

	public String statusToShortString() {
		return "SE";
	}
	
	public boolean isExpectedStatus(String str) {
		if (str.contains("denialofjudgement")) return true;
		if (str.contains("systemerror")) return true;
		if (str.contains("judgingerror")) return true;
		if (str.contains("validatorerror")) return true;
		return false;
	}
}
