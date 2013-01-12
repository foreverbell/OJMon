package status;

public class StatusPresentationError extends Status {

	public EStatus getCode() {
		return EStatus.PresentationError;
	}

	public String statusToString() {
		return "PresentationError";
	}
	
	public String statusToShortString() {
		return "PE";
	}

	public boolean isExpectedStatus(String str) {
		if (str.contains("presentationerror")) return true;
		return false;
	}
}
