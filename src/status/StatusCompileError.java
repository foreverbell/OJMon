package status;

public class StatusCompileError extends Status {
	
	public EStatus getCode() {
		return EStatus.CompileError;
	}

	public String statusToString() {
		return "CompileError";
	}
	
	public String statusToShortString() {
		return "CE";
	}

	public boolean isExpectedStatus(String str) {
		if (str.contains("compileerror")) return true;
		if (str.contains("compilationerror")) return true;
		return false;
	}
}
