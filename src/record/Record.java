package record;

import java.util.Date;

import status.Status;

public class Record implements Comparable<Record> {

	private String _bindOJName;
	private Integer _runId;
	private Status _status;
	private String _userName;
	private String _nickName;
	private String _probId;
	private Date _submitTime;
	private String _language;
	private Integer _timeUsage;
	private Integer _memoryUsage;
	private Integer _codeLength;

	public String getBindOJName() {
		return _bindOJName;
	}
	
	public Integer getRunId() {
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
	
	public Integer getTimeUsage() {
		return _timeUsage;
	}
	
	public Integer getMemoryUsage() {
		return _memoryUsage;
	}
	
	public Integer getCodeLength() {
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
			Integer timeUsage,
			Integer memoryUsage,
			Integer codeLength) {
		
		_bindOJName = OJName;
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
		return - this._runId.compareTo(arg._runId);
	}
}
