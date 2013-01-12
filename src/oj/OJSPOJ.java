package oj;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import record.Record;
import status.Status;
import user.User;

public class OJSPOJ extends OJ {

	public EOJ getOJCode() {
		return EOJ.SPOJ;
	}

	public String getOJName() {
		return "SPOJ";
	}
	
	protected ArrayList<Record> getUserRecord(String strHTML, int maximumSize, int untilRunId, int afterRunId) {
		Pattern pattern = Pattern.compile("<tr class=\"kol.*?\"><td class=\"statustext\">\\s*(\\d+)</td>\\s*<td class=\"status_sm\">\\s*(\\d+-\\d+-\\d+ \\d+:\\d+:\\d+)\\s*</td>\\s*<td><a href=\"/users/(.*?)/\" title=.*?>(.*?)</a></td><td><a href=\"/problems/(.*?)/\".*?>(.*?)</a></td><td class=\"statusres\".*?>\\s*(.*?)\\s*</td>\\s*?<td .*?id=\"statustime_\\d+\">.*?\\s*<a href=.*?>\\s*(.*?)\\s*</a>\\s*.*?</td>\\s*<td .*?id=\"statusmem_\\d+\">\\s*(.*?)(M|k|\\s)\\s*</td>\\s*<td class=\"slang\">\\s*<p>(.*?)</p>.*?\\s*</tr>");
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
			int memoryUsage = OJ_INVALID_INFO;
			int timeUsage = OJ_INVALID_INFO;
			
			runId = Integer.valueOf(matcher.group(1));
			submitTime = parseDate(matcher.group(2));
			submitTime.setTime(submitTime.getTime() + 1000 * 60 * 60 * 7); // GMT+1 -> GMT+8
			userName = matcher.group(3);
			nickName = matcher.group(4);
			probId = matcher.group(6) + "(" + matcher.group(5) + ")";
			status = Status.getStatusInstance(matcher.group(7));
			if (!matcher.group(8).equals("-")) {
				timeUsage = (int) (Double.valueOf(matcher.group(8)) * 1000);
			}
			if (!matcher.group(9).equals("-")) {
				memoryUsage = (int) (Double.valueOf(matcher.group(9)) * (matcher.group(10).equals("M") ? 1024 : 1));
			}
			language = matcher.group(11);

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

	protected String getUserSubmissionURL(User user, int maximumSize, int lastRunId, int page) {
		if (page > 6) return null;
		String userSession = (user.isNull() ? "" : "/" + user.getUserName());
		String pageSession = "/start=" + Integer.valueOf((page - 1) * 20).toString();
		return "http://www.spoj.com/status" + userSession + pageSession;
	}
}
