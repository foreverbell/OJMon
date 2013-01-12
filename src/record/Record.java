package record;

import java.util.Date;

import status.Status;

public class Record implements Comparable<Record> {

	private String _attachedOJName;
	private int _runId;
	private Status _status;
	private String _userName;
	private String _nickName;
	private String _probId;
	private Date _submitTime;
	private String _language;
	private int _timeUsage;
	private int _memoryUsage;
	private int _codeLength;

	public String getAttachedOJName() {
		return _attachedOJName;
	}
	
	public int getRunId() {
		return _runId;
	}
	
	public Status getStatus() {
		return _status;
	}
	
	public String getUserName() {
		return _userName;
	}
	
	public String getNickName() {
		return _nickName;
	}

	public String getProbId() {
		return _probId;
	}
	
	public Date getSubmitTime() {
		return _submitTime;
	}
	
	public String getLanguage() {
		return _language;
	}
	
	public int getTimeUsage() {
		return _timeUsage;
	}
	
	public int getMemoryUsage() {
		return _memoryUsage;
	}
	
	public int getCodeLength() {
		return _codeLength;
	}

	public boolean isNeedToRecord() {
		return _status.isNeedToRecord();
	}

	public Record(
			String OJName,
			int runId,
			Status status,
			String userName,
			String nickName,
			String probId,
			Date submitTime,
			String language,
			int timeUsage,
			int memoryUsage,
			int codeLength) {
		
		_attachedOJName = OJName;
		_runId = runId;
		_status = status;
		_userName = userName;
		_nickName = nickName;
		_probId = probId;
		_submitTime = submitTime;
		_language = language;
		_timeUsage = timeUsage;
		_memoryUsage = memoryUsage;
		_codeLength = codeLength;
	}

	public int compareTo(Record arg) {
		// Assume this._attachedOJName.equals(arg._attachedOJName)) = true.
		if (this._runId == arg._runId) return 0;
		return this._runId < arg._runId ? 1 : -1;
	}
}
