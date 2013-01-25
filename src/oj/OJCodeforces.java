package oj;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import record.Record;
import status.Status;
import user.User;

public class OJCodeforces extends OJ {

	public static final String OJNAME = "Codeforces";
	
	public EOJ getOJCode() {
		return EOJ.Codeforces;
	}

	public String getOJName() {
		return OJNAME;
	}
	
	protected ArrayList<Record> getUserRecord(String strHTML, int maximumSize, int untilRunId, int afterRunId) {
		Pattern pattern = Pattern.compile("<tr.*?data-submission-id=\"\\d+\">\\s+<td>\\s+(<a class=\"view-source\" title=\"Source\" href=\"#\" submissionId=\"\\d+\">)?(\\d+)(</a>)?\\s+</td>\\s+<td.*?>\\s+(.*?)\\s+</td>\\s+.*?\\s+.*?<a href=\"/profile/(.*?)\".*?</a>.*?    </td>\\s+<td.*?>\\s+<a href=\"/problemset/.*?\">\\s+(.*?)\\s+</a>\\s+</td>\\s+<td>\\s+(.*?)\\s+</td>\\s+<td.*?>\\s+<.*?>(.*?)</.*?>\\s+</td>\\s+<td.*?>\\s+(\\d+) ms\\s+</td>\\s+<td.*?>\\s+(\\d+) KB\\s+</td>\\s+</tr>");
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
			
			runId = Integer.valueOf(matcher.group(2));
			submitTime = parseDate(matcher.group(4));
			submitTime.setTime(submitTime.getTime() + 1000 * 60 * 60 * 4); // GMT+4 -> GMT+8
			userName = matcher.group(5);
			probId = matcher.group(6);
			language = matcher.group(7);
			status = Status.getStatusInstance(matcher.group(8));
			timeUsage = Integer.valueOf(matcher.group(9));
			memoryUsage = Integer.valueOf(matcher.group(10));

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
					OJ_INVALID_INFO
					)
			);
		}
		return result;
	}

	protected String getUserSubmissionURL(User user, Integer maximumSize, Integer lastRunId, Integer page) {
		String basisURL = null;
		if (user.isNull()) {
			basisURL = "http://www.codeforces.com/problemset/status";
		} else {
			basisURL = "http://www.codeforces.com/submissions/" + user.getUserName();
		}
		String pageSession = "/page/" + page;
		return basisURL + pageSession;
	}

	protected Date parseDate(String str) {
		try {
			return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM, Locale.US).parse(str);
		} catch (ParseException e) {
			return onParseDateError(e);
		}
	}
	
	protected OJCodeforces() { }
}
