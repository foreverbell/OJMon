package html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;

import oj.OJ;

import record.Record;

import user.Account;
import user.User;
import util.Logger;

public class HTMLLocalTextWriter {

	private static final String HTMLTopCode = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><meta http-equiv=\"Refresh\" content=\"60\"><style type=\"text/css\">body{font-family:\"Tahoma\";}a{color:inherit}.row0{height:25px;background-color:#33aaee;text-align:center;font-size:16px}.row1{height:20px;background-color:#ffffff;text-align:center;font-size:12px}.row2{height:20px;background-color:#d7ebff;text-align:center;font-size:12px}</style></head><body>";
	private static final String HTMLTableTopCode = "<table class=list><tr class=row0><td width=5%>OJ</td><td width=7%>Run ID</td><td width=10%>Nick Name</td><td width=12%>Problem</td><td width=14%>Judge Status</td><td width=7%>Memory</td><td width=7%>Time</td><td width=7%>Language</td><td width=8%>Code Length</td><td width=16%>Submit Time</td></tr>";
	private static final String HTMLTableBottomCode = "</table><br>"; 
	private static final String HTMLBottomCode = "</body></html>";

	private Account _bindAccount;

	
	private String HTMLtdWrapper(String str) {
		return "<td>" + str + "</td>";
	}
	
	private String toHTMLCode(Record r, boolean isEvenRow) {
		String OJSession = HTMLtdWrapper(r.getBindOJName());
		String runIdSession = HTMLtdWrapper(Integer.valueOf(r.getRunId()).toString());
		// String userNameSession = HTMLtdWrapper(r.getUserName());
		String nickNameSession = HTMLtdWrapper(r.getNickName());
		String probIdSession = HTMLtdWrapper(r.getProbId());
		String statusSession = HTMLtdWrapper("<font color=" + (r.getStatus().isPassed() ? "green" : "blue") + ">" + (r.getStatus().isPassed() ? "<strong>" : "") + r.getStatus().statusToString() + (r.getStatus().isPassed() ? "</strong>" : "") + "</font>");
		String memorySession = HTMLtdWrapper(r.getMemoryUsage() == OJ.OJ_INVALID_INFO ? "n/a" : Integer.valueOf(r.getMemoryUsage()).toString() + "k");
		String timeSession = HTMLtdWrapper(r.getTimeUsage() == OJ.OJ_INVALID_INFO ? "n/a" : Integer.valueOf(r.getTimeUsage()).toString() + "ms");
		String langSession = HTMLtdWrapper(r.getLanguage());
		String codeLenSession = HTMLtdWrapper(r.getCodeLength() == OJ.OJ_INVALID_INFO ? "n/a" : Integer.valueOf(r.getCodeLength()).toString() + "b");
		String submitTimeSession = HTMLtdWrapper(DateFormat.getDateTimeInstance().format(r.getSubmitTime()));
		
		return "<tr class=" + (isEvenRow ? "row1" : "row2") + ">"
				+ OJSession
				+ runIdSession
				// + userNameSession
				+ nickNameSession
				+ probIdSession
				+ statusSession
				+ memorySession
				+ timeSession
				+ langSession
				+ codeLenSession
				+ submitTimeSession
				+ "</tr>";
	}
	
	public void writeToHTML() {
		try {
			String HTMLFileName = _bindAccount.getAccountRecordFilePath();
			BufferedWriter _bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(HTMLFileName)), "utf-8"));
			
			_bufWriter.write(HTMLTopCode);
			_bufWriter.newLine();
			for (User user : _bindAccount.getAllUser()) {
				_bufWriter.write(HTMLTableTopCode);
				_bufWriter.newLine();
				boolean isEvenRow = false;
				for (Record record : user.getAllRecord()) {
					_bufWriter.write(toHTMLCode(record, isEvenRow));
					isEvenRow = !isEvenRow;
					_bufWriter.newLine();
				}
				_bufWriter.write(HTMLTableBottomCode);
				_bufWriter.newLine();
			}
			_bufWriter.write(HTMLBottomCode);
			_bufWriter.flush();
			_bufWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			Logger.printlnError(e.getMessage());
		}
	}
	
	public HTMLLocalTextWriter(Account account) {
		_bindAccount = account;
	}
}
