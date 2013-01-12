package status;

public class StatusWrongAnswer extends Status {

	public EStatus getCode() {
		return EStatus.WrongAnswer;
	}

	public String statusToString() {
		return "WrongAnswer";
	}
	
	public String statusToShortString() {
		return "WA";
	}
	
	public boolean isExpectedStatus(String str) {
		if (str.contains("wronganswer")) return true;
		return false;
	}
}
