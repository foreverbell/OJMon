package oj;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.*;

import record.Record;
import status.Status;
import user.User;

public class OJPOJ extends OJ {
	
	public static final String OJNAME = "POJ";
	
	public EOJ getOJCode() {
		return EOJ.POJ;
	}
	
	public String getOJName() {
		return OJNAME;
	}
	
	protected ArrayList<Record> getUserRecord(String strHTML, int maximumSize, int untilRunId, int afterRunId) {
		Pattern patternBasisInfo = Pattern.compile("<tr align=center><td>(\\d+)</td><td><a href=userstatus\\?user_id=(.*?)>.*?</a></td><td><a href=.*?=(\\d+)>.*?</a></td><td>.*?<font color=.*?>(.*?)</font>");
		Pattern patternRuntimeInfo = Pattern.compile("<td>(\\d+)K</td><td>(\\d+)MS</td>");
		Pattern patternCodeInfo = Pattern.compile("<td>([a-zA-Z+]+)</td><td>(\\d+)B</td><td>(\\d+-\\d+-\\d+ \\d+:\\d+:\\d+)</td>");
		Matcher matcherBasisInfo = patternBasisInfo.matcher(strHTML);
		Matcher matcherRuntimeInfo = patternRuntimeInfo.matcher(strHTML);
		Matcher matcherCodeInfo = patternCodeInfo.matcher(strHTML);
		ArrayList<Record> result = new ArrayList<Record>();
		
		while (matcherBasisInfo.find() && !isEndConditionHold(result, maximumSize, untilRunId)) {
			int runId;
			String userName;
			String probId;
			Status status;
			Date submitTime;
			String language;
			int memoryUsage;
			int timeUsage;
			int codeLength;
			
			runId = Integer.valueOf(matcherBasisInfo.group(1)); 
			userName = matcherBasisInfo.group(2);
			probId = matcherBasisInfo.group(3);
			status = Status.getStatusInstance(matcherBasisInfo.group(4));
			if (status.isPassed()) {
				matcherRuntimeInfo.find();
				memoryUsage = Integer.valueOf(matcherRuntimeInfo.group(1)); 
				timeUsage = Integer.valueOf(matcherRuntimeInfo.group(2)); 
			} else {
				memoryUsage = OJ_INVALID_INFO;
				timeUsage = OJ_INVALID_INFO;
			}
			matcherCodeInfo.find();
			language = matcherCodeInfo.group(1);
			codeLength = Integer.valueOf(matcherCodeInfo.group(2));
			submitTime = parseDate(matcherCodeInfo.group(3));
			
			if (runId >= afterRunId) continue;
			result.add(new Record(
					getOJName(),
					runId,
					status,
					userName,
					userName,
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
		String userSession = "user_id=" + user.getUserName();
		String topSession = "top=" + (lastRunId == OJ_INVALID_INFO ? "" : lastRunId);
		String sizeSession = "size=" + (maximumSize == OJ_INVALID_INFO ? "" : (maximumSize + 5));
		return "http://poj.org/status?" + userSession + "&" + topSession + "&" + sizeSession;
	}
	
	protected OJPOJ() { }
}
