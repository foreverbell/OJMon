package status;

public class StatusRuntimeError extends Status {
	
	public EStatus getCode() {
		return EStatus.RuntimeError;
	}

	public String statusToString() {
		return "RuntimeError";
	}
	
	public String statusToShortString() {
		return "RE";
	}

	public boolean isExpectedStatus(String str) {
		if (str.contains("runtimeerror")) return true;
		if (str.contains("exitcode")) return true;
		if (str.contains("segmentationfault")) return true;
		if (str.contains("floatingPointerror")) return true;
		if (str.contains("crash")) return true;
		return false;
	}
}
