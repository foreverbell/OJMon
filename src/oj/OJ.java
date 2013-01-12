package oj;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import record.Record;
import user.User;
import util.Logger;
import html.HTMLTextReader;

public abstract class OJ {
	
	public static final int OJ_INVALID_INFO = -1;
	
	abstract public EOJ getOJCode();
	abstract public String getOJName();
	abstract protected ArrayList<Record> getUserRecord(String strHTML, int maximumSize, int untilRunId, int afterRunId);
	abstract protected String getUserSubmissionURL(User user, int maximumSize, int lastRunId, int page);
	
	protected String _encoding;
	
	public int updateUserRecords(User user, int maximumSize, int untilRunId) {
		String encoding = (_encoding == null ? "utf-8" : _encoding);
		ArrayList<Record> recordList = new ArrayList<Record>();
		ArrayList<Record> temp;
		String strHTML, strURL;
		int lastRunId = Integer.MAX_VALUE, lastPage = 1;
		do {
			strURL = getUserSubmissionURL(user, maximumSize - recordList.size(), lastRunId, lastPage ++);
			if (strURL == null) break;
			strHTML = HTMLTextReader.textRead(strURL, encoding);
			if (strHTML == null) break;
			temp = getUserRecord(strHTML, maximumSize - recordList.size(), untilRunId, lastRunId);
			if (temp.size() == 0) break;
			lastRunId = temp.get(temp.size() - 1).getRunId();
			for (Record r : temp) {
				if (r.isNeedToRecord()) recordList.add(r);
			}
		} while (!isEndConditionHold(recordList, maximumSize, untilRunId));
		return user.mergeRecords(recordList, maximumSize);
	}
	
	boolean isEndConditionHold(ArrayList<Record> records, int maximumSize, int untilRunId) {
		int listSize = records.size();
		if (listSize >= maximumSize) return true;
		if (listSize == 0) return false;
		if (records.get(listSize - 1).getRunId() <= untilRunId) return true;
		return false;
	}
	
	protected Date onParseDateError(ParseException e) {
		Logger.printlnError(e.getMessage());
		return null;	
	}
	
	protected Date parseDate(String str) {
		try {
			return DateFormat.getDateTimeInstance().parse(str);
		} catch (ParseException e) {
			return onParseDateError(e);
		}
	}
}