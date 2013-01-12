package status;

import java.util.ArrayList;

import util.INullable;
import util.Logger;

public abstract class Status implements INullable {

	abstract public EStatus getCode();
	abstract public String statusToString();
	abstract public String statusToShortString();
	abstract public boolean isExpectedStatus(String str);
	
	public boolean isNull() {
		return false;
	}
	
	public boolean isPassed() {
		return false;
	}

	public boolean isNeedToRecord() {
		return true;
	}
	
	public static final Status ACCEPTED = new StatusAccepted();
	public static final Status COMPILE_ERROR = new StatusCompileError();
	public static final Status MEMORY_LIMIT_EXCEEDED = new StatusMemoryLimitExceeded();
	public static final Status OUTPUT_LIMIT_EXCEEDED = new StatusOutputLimitExceeded();
	public static final Status PRESENTATION_ERROR = new StatusPresentationError();
	public static final Status PROCESSING = new StatusProcessing();
	public static final Status RUNTIME_ERROR = new StatusRuntimeError();
	public static final Status SYSTEM_ERROR = new StatusSystemError();
	public static final Status TIME_LIMIT_EXCEEDED = new StatusTimeLimitExceeded();
	public static final Status WRONG_ANSWER = new StatusWrongAnswer();

	private static ArrayList<Status> statusCollection;

	public static final Status getStatusInstance(String strStatus) {
		if (statusCollection == null) {
			statusCollection = new ArrayList<Status>();
			// Status judged in reasonable order to improve efficiency.
			statusCollection.add(ACCEPTED);
			statusCollection.add(WRONG_ANSWER);
			statusCollection.add(TIME_LIMIT_EXCEEDED);
			statusCollection.add(RUNTIME_ERROR);
			statusCollection.add(MEMORY_LIMIT_EXCEEDED);
			statusCollection.add(COMPILE_ERROR);
			statusCollection.add(OUTPUT_LIMIT_EXCEEDED);
			statusCollection.add(PRESENTATION_ERROR);
			statusCollection.add(PROCESSING);
			statusCollection.add(SYSTEM_ERROR);
		}
		for (Status status : statusCollection) {
			if (status.isExpectedStatus(strStatus.replace(" ", "").toLowerCase())) return status;
		}
		Logger.println("Warning: Unknown Status found.");
		return StatusNull.getInstance();
	}
}
