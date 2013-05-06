package oj;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import record.Record;
import status.Status;
import user.User;

public class OJBZOJ extends OJ {
	
	public static final String OJNAME = "BZOJ";

	public EOJ getOJCode() {
		return EOJ.BZOJ;
	}

	public String getOJName() {
		return OJNAME;
	}

	protected ArrayList<Record> getUserRecord(String strHTML, int maximumSize, int untilRunId, int afterRunId) {
		Pattern pattern = Pattern.compile("<tr align=center class=.*?><td>(\\d+)<td><a href='userinfo.php\\?user=.*?'>(.*?)</a><td><a href='problem.php\\?id=.*?'>(\\d+)</a><td><font color=.*?>(.*?)/font><td>(\\d+) <font color=red>kb</font><td>(\\d+) <font color=red>ms</font><td>(.*?)<td>(\\d+) B<td>(.*?)</tr>");
		Matcher matcher = pattern.matcher(strHTML);
		ArrayList<Record> result = new ArrayList<Record>();
		while (matcher.find() && !isEndConditionHold(result, maximumSize, untilRunId)) {
			int runId;
			String userName;
			String probId;
			Status status;
			Date submitTime;
			String language;
			int memoryUsage;
			int timeUsage;
			int codeLength;
			
			runId = Integer.valueOf(matcher.group(1));
			userName = matcher.group(2);
			probId = matcher.group(3);
			status = Status.getStatusInstance(matcher.group(4).replace('_', ' '));
			memoryUsage = Integer.valueOf(matcher.group(5));
			timeUsage = Integer.valueOf(matcher.group(6));
			language = matcher.group(7);
			codeLength = Integer.valueOf(matcher.group(8));
			submitTime = parseDate(matcher.group(9));
			
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
		String basisUrl = "http://www.lydsy.com/JudgeOnline/status.php?";
		String userSession = "user_id=" + (user.isNull() ? "" : user.getUserName());
		String topSession = "top=" + (lastRunId == OJ_INVALID_INFO ? "" : (lastRunId - 1));
		return basisUrl + userSession + "&" + topSession;
	}
	
	protected OJBZOJ() { }
}
