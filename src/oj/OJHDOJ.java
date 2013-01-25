package oj;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import record.Record;
import status.Status;
import user.User;

public class OJHDOJ extends OJ {

	public static final String OJNAME = "HDOJ";
	
	public EOJ getOJCode() {
		return EOJ.HDOJ;
	}

	public String getOJName() {
		return OJNAME;
	}
	
	protected ArrayList<Record> getUserRecord(String strHTML, int maximumSize, int untilRunId, int afterRunId) {
		Pattern pattern = Pattern.compile("<td height=22px>(\\d+)</td><td>(\\d+-\\d+-\\d+ \\d+:\\d+:\\d+)</td><td>(<a href=.*?\\s+.*?)?<font color=.*?>(.*?)</font>(</a>)?</td><td><a href=\"/showproblem.php\\?pid=.*?\">(\\d+)</a></td><td>(\\d+)MS</td><td>(\\d+)K</td><td>(\\d+)B</td><td>(.*?)</td><td class=fixedsize><a href=\"/userstatus.php\\?user=(.*?)(&PHPSESSID=.*?)?\">(.*?)</a></td></tr>");
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
			submitTime = parseDate(matcher.group(2));
			status = Status.getStatusInstance(matcher.group(4));
			probId = matcher.group(6);
			timeUsage = Integer.valueOf(matcher.group(7));
			memoryUsage = Integer.valueOf(matcher.group(8));
			codeLength = Integer.valueOf(matcher.group(9));
			language = matcher.group(10);
			userName = matcher.group(11);
			nickName = matcher.group(13);
			
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
		String userSession = "user=" + user.getUserName();
		String firstSession = "first=" + (lastRunId == OJ_INVALID_INFO ? "" : (lastRunId - 1));
		return "http://acm.hdu.edu.cn/status.php?" + userSession + "&" + firstSession;
	}

	protected OJHDOJ() { 
		_encoding = "gb2312";
	}

}
