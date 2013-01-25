package oj;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import record.Record;
import status.Status;
import user.User;

public class OJZOJ extends OJ {

	public static final String OJNAME = "ZOJ";
	
	public EOJ getOJCode() {
		return EOJ.ZOJ;
	}
	
	public String getOJName() {
		return OJNAME;
	}

	protected ArrayList<Record> getUserRecord(String strHTML, int maximumSize, int untilRunId, int afterRunId) {
		Pattern pattern = Pattern.compile("<tr class=\"row.*?\">\\s+<td class=\"runId\">(\\d+)</td>\\s+<td class=\"runSubmitTime\">(\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2})</td>\\s+<td class=\"runJudgeStatus\">\\s+<span class=\"judgeReply.*?\">\\s+(.*?)\\s+</span></td>\\s+.*?<a href=\"/onlinejudge/showProblem.do\\?problemId=\\d+\">(\\d+)</a></td>\\s+<td class=\"runLanguage\">(.*?)</td>\\s+<td class=\"runTime\">(\\d+)</td>\\s+<td class=\"runMemory\">(\\d+)</td>\\s+<td class=\"runUserName\"><a href=\"/onlinejudge/showUserStatus.do\\?userId=(.*?)\"><font color=\".*?\">(.*?)</font></a></td>");
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
			
			runId = Integer.valueOf(matcher.group(1));
			submitTime = parseDate(matcher.group(2));
			status = Status.getStatusInstance(matcher.group(3));
			probId = matcher.group(4);
			language = matcher.group(5);
			timeUsage = Integer.valueOf(matcher.group(6));
			memoryUsage = Integer.valueOf(matcher.group(7));
			userName = matcher.group(8);
			nickName = matcher.group(9);
			
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
					OJ_INVALID_INFO
					)
			);
		}
		return result;
	}

	protected String getUserSubmissionURL(User user, Integer maximumSize, Integer lastRunId, Integer page) {
		String handleSession = "handle=" + user.getUserName();
		String lastIdSession = "lastId=" + lastRunId;
		return "http://acm.zju.edu.cn/onlinejudge/showRuns.do?contestId=1&" + handleSession + "&" + lastIdSession;
	}
	
	protected OJZOJ() { }
}
