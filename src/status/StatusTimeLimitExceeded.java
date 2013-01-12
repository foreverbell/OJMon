package status;

public class StatusTimeLimitExceeded extends Status {

	public EStatus getCode() {
		return EStatus.TimeLimitExceeded;
	}

	public String statusToString() {
		return "TimeLimitExceeded";
	}

	public String statusToShortString() {
		return "TLE";
	}
	
	public boolean isExpectedStatus(String str) {
		if (str.contains("timelimitexceed")) return true;
		return false;
	}
}
