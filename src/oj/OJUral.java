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

public class OJUral extends OJ {

	public EOJ getOJCode() {
		return EOJ.Ural;
	}

	public String getOJName() {
		return "Ural";
	}
	
	protected ArrayList<Record> getUserRecord(String strHTML, int maximumSize, int untilRunId, int afterRunId) {
		Pattern pattern = Pattern.compile("<TR class=\".*?\"><TD class=\"id\">(<.*?>)?(\\d+)(</A>)?</TD><TD class=\"date\"><NOBR>(.*?)</NOBR><BR><NOBR>(.*?)</NOBR></TD><TD class=\"coder\"><A HREF=\"author\\.aspx\\?id=(\\d+)\">(.*?)</A></TD><TD class=\"problem\"><A HREF=\"problem\\.aspx\\?space=1&amp;num=\\d+\">(.*?)</A></TD><TD class=\"language\">(.*?)</TD><TD class=\"verdict_.*?\">(<A HREF=\".*?\">)?(.*?)(</A>)?</TD>.*?<TD class=\"runtime\">(.*?)</TD><TD class=\"memory\">(.*?)( KB)?</TD></TR>");
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
	
			runId = Integer.valueOf(matcher.group(2));
			submitTime = parseDate(matcher.group(5) + " " + matcher.group(4));
			submitTime.setTime(submitTime.getTime() + 1000 * 60 * 60 * 2); // GMT+6 -> GMT+8
			userName = matcher.group(6);
			nickName = matcher.group(7);
			probId = matcher.group(8);
			language = matcher.group(9);
			status = Status.getStatusInstance(matcher.group(11));
			if (!matcher.group(13).equals("<BR>")) {
				timeUsage = (int) (Double.valueOf(matcher.group(13)) * 1000);
			}
			if (!matcher.group(14).equals("<BR>")) {
				memoryUsage = Integer.valueOf(matcher.group(14).replace(" ", ""));
			}
			
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
		String authorSession = "author=" + user.getUserName();
		String countSession = "count=" + Integer.valueOf(maximumSize + 5).toString();
		String fromSession = "from=" + Integer.valueOf(lastRunId - 1).toString();
		return "http://acm.timus.ru/status.aspx?" + authorSession + "&" + countSession + "&" + fromSession;
	}

	protected Date parseDate(String str) {
		try {
			return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, Locale.UK).parse(str);
		} catch (ParseException e) {
			return onParseDateError(e);
		}
	}
}
