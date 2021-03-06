package user;

import java.util.ArrayList;
import java.util.TreeSet;

import oj.OJ;
import record.Record;
import util.INullable;

public class User implements INullable {
	
	public static final int RECORD_MAXIMUM_SIZE = 25;
	
	protected OJ _bindOJ;
	protected String _userName;
	protected int _maximumSize = RECORD_MAXIMUM_SIZE;
	private TreeSet<Record> _userRecordList = new TreeSet<Record>();
	private boolean _hasNewRecord;
	private int _newRecordCount;
	
	public boolean isNull() {
		return false;
	}
	
	public Record [] getAllRecord() {
		return _userRecordList.toArray(new Record[0]);
	}
	
	public OJ getBindOJ() {
		return _bindOJ;
	}
	
	public String getUserName() {
		return _userName;
	}
	
	public int getMaximumSize() {
		return _maximumSize;	
	}
	
	public void setMaximumSize(int maximumSize) {
		if (maximumSize > _maximumSize) _userRecordList.clear();
		_maximumSize = maximumSize;
	}
	
	public int mergeRecords(ArrayList<Record> records, int maximumSize) {
		int updatedCnt = 0;
		for (Record record : records) {
			if (!_userRecordList.contains(record)) {
				_userRecordList.add(record);
				updatedCnt += 1;
			}
		}
		while (_userRecordList.size() > maximumSize) _userRecordList.pollLast();
		return updatedCnt;
	}
	
	public int update() {
		int updatedCount = 0;
		if (_userRecordList.size() == 0) {
			updatedCount = _bindOJ.updateUserRecords(this, _maximumSize, 0);
		} else {
			updatedCount = _bindOJ.updateUserRecords(this, _maximumSize, _userRecordList.first().getRunId());
		}
		if (updatedCount > 0) _hasNewRecord = true;
		_newRecordCount += updatedCount;
		return updatedCount;
	}
	
	public boolean hasNewRecord() {
		return _hasNewRecord;
	}
	
	public int getNewRecordCount() {
		return _newRecordCount;
	}
	
	public void clearNewRecordFlag() {
		_newRecordCount = 0;
		_hasNewRecord = false;
	}
	
	public User(OJ bindOJ, String userName) {
		_bindOJ = bindOJ;
		_userName = userName;
	}
}
