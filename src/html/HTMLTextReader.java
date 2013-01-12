package html;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import util.Logger;

public class HTMLTextReader {
	
	public static final int CONNECTION_TIMEOUT = 5000;
	
	public static String textRead(String strURL, String encoding) {
		try {
			URL dstURL = new URL(strURL);
			HttpURLConnection dstURLCon = (HttpURLConnection) dstURL.openConnection();
			dstURLCon.setConnectTimeout(CONNECTION_TIMEOUT);
			dstURLCon.setReadTimeout(CONNECTION_TIMEOUT);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(dstURLCon.getInputStream(), encoding));
			String textLine;
			StringBuffer strBuf = new StringBuffer();
			while ((textLine = reader.readLine()) != null) {
				strBuf.append(textLine);
				strBuf.append('\n');
			}
			
			reader.close();
			dstURLCon.disconnect();
			
			return strBuf.toString();
		} catch (Exception e) {
			Logger.printlnError(e.getMessage());
			return null;
		}
	}
}
