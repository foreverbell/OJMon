package status;

public class StatusAccepted extends Status {

	public EStatus getCode() {
		return EStatus.Accepted;
	}

	public String statusToString() {
		return "Accepted";
	}

	public String statusToShortString() {
		return "AC";
	}
	
	public boolean isPassed() {
		return true;
	}
	
	public boolean isExpectedStatus(String str) {
		if (str.contains("accept")) return true;
		return false;
	}
}
