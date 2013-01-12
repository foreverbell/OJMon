package status;

public class StatusProcessing extends Status {

	public EStatus getCode() {
		return EStatus.Processing;
	}

	public String statusToString() {
		return "Processing";
	}

	public String statusToShortString() {
		return statusToString();
	}
	
	public boolean isNeedToRecord() {
		return false;
	}
	
	public boolean isExpectedStatus(String str) {
		if (str.contains("error")) return false;
		if (str.contains("running")) return true;
		if (str.contains("judging")) return true;
		if (str.contains("waiting")) return true;
		if (str.contains("compiling")) return true;
		if (str.contains("queuing")) return true;
		if (str.contains("queue")) return true;
		return false;
	}
}
