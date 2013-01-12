package status;

public class StatusMemoryLimitExceeded extends Status {

	public EStatus getCode() {
		return EStatus.MemoryLimitExceeded;
	}

	public String statusToString() {
		return "MemoryLimitExceeded";
	}

	public String statusToShortString() {
		return "MLE";
	}
	
	public boolean isExpectedStatus(String str) {
		if (str.contains("memorylimitexceed")) return true;
		return false;
	}
}
