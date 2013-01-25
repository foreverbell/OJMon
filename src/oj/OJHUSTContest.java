package oj;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import record.Record;
import status.Status;
import user.User;

public class OJHUSTContest extends OJHUST {
	
	public static final String OJNAME = "HUSTVC";
	
	private Integer _contestID;
	
	public EOJ getOJCode() {
		return EOJ.HUSTContest;
	}

	public String getOJName() {
		return OJNAME;
	}

	protected ArrayList<Record> getUserRecord(String strHTML, int maximumSize, int untilRunId, int afterRunId) {
		Pattern pattern = Pattern.compile("\\[(\\d+),\"(.*?)\",\"(.*?)\",\"(.*?)\",(\\d+),(\\d+),\"(.*?)\",(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+)\\]");
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
			probId = matcher.group(3);
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

	protected String getUserSubmissionURL(User user, Integer maximumSize, Integer lastRunId, Integer page) {
		page -= 1;
		return "http://acm.hust.edu.cn:8080/judge/contest/fetchStatus.action?" + "cid=" + _contestID + "&sEcho=1&iColumns=13&sColumns=&iDisplayStart=" + (page * maximumSize) + "&iDisplayLength=" + maximumSize + "&mDataProp_0=0&mDataProp_1=1&mDataProp_2=2&mDataProp_3=3&mDataProp_4=4&mDataProp_5=5&mDataProp_6=6&mDataProp_7=7&mDataProp_8=8&mDataProp_9=9&mDataProp_10=10&mDataProp_11=11&mDataProp_12=12&un=" + user.getUserName() + "&num=-&res=0";
	}
	
	protected OJHUSTContest(Integer contestID) {
		_contestID = contestID;
	}
}
