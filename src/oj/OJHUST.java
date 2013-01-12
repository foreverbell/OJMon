package oj;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import record.Record;
import status.Status;
import user.User;

public class OJHUST extends OJ {

	public EOJ getOJCode() {
		return EOJ.HUST;
	}

	public String getOJName() {
		return "HUSTVJ";
	}

	protected ArrayList<Record> getUserRecord(String strHTML, int maximumSize, int untilRunId, int afterRunId) {
		Pattern pattern = Pattern.compile("\\[(\\d+),\"(.*?)\",(\\d+),\"(.*?)\",(\\d+),(\\d+),\"(.*?)\",(\\d+),(\\d+),(\\d+),(\\d+),\"(.*?)\",\"(.*?)\",.*?\\]");
		Matcher matcher = pattern.matcher(strHTML);
		ArrayList<Record> result = new ArrayList<Record>();

		while (matcher.find() && !isEndConditionHold(result, maximumSize, untilRunId)) {
			int runId;
			String userName;
			String nickName;
			String probId;
			Status status;
			Date submitTime;
			String language;
			int memoryUsage;
			int timeUsage;
			int codeLength;
			
			runId = Integer.valueOf(matcher.group(1));
			submitTime = parseDate(matcher.group(9));
			status = Status.getStatusInstance(matcher.group(4));
			probId = matcher.group(12) + " " + matcher.group(13);
			if (status.isPassed()) {
				memoryUsage = Integer.valueOf(matcher.group(5));
				timeUsage = Integer.valueOf(matcher.group(6));
			} else {
				memoryUsage = OJ_INVALID_INFO;
				timeUsage = OJ_INVALID_INFO;			
			}
			codeLength = Integer.valueOf(matcher.group(8));
			language = matcher.group(7);
			userName = matcher.group(10);
			nickName = matcher.group(2);
			
			if (runId >= afterRunId) continue;
			result.add(new Record(
					getOJName(),
					runId,
					status,
					userName,
					nickName,
					probId,
					submitTime,
					language,
					timeUsage,
					memoryUsage,
					codeLength
					)
			);
		}
		return result;
	}

	protected String getUserSubmissionURL(User user, int maximumSize, int lastRunId, int page) {
		page -= 1;
		return "http://acm.hust.edu.cn:8080/judge/problem/fetchStatus.action?sEcho=1&iColumns=15&sColumns=&iDisplayStart=" + Integer.valueOf(page * maximumSize).toString() + "&iDisplayLength=" + Integer.valueOf(maximumSize).toString() + "&mDataProp_0=0&mDataProp_1=1&mDataProp_2=2&mDataProp_3=3&mDataProp_4=4&mDataProp_5=5&mDataProp_6=6&mDataProp_7=7&mDataProp_8=8&mDataProp_9=9&mDataProp_10=10&mDataProp_11=11&mDataProp_12=12&mDataProp_13=13&mDataProp_14=14&un=" + user.getUserName() + "&OJId=all&probNum=&res=0";
	}
	
	protected Date parseDate(String str) {
		Date ret = new Date();
		ret.setTime(Long.valueOf(str));
		return ret;
	}
}
