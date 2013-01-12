package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class Logger {
	public static final boolean isDebugEnabled = true;
	private static BufferedWriter _bufWriter;
	public static final String LOG_FILE_PATH = "D:\\output.txt";
	
	public static void switchToFile(String path) {
		try {
			_bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path))));
		} catch (Exception e) { }
	}
	
	public static void println(Object obj) {
		if (isDebugEnabled) {
			if (_bufWriter == null) System.out.println(obj);
			else {
				try {
				_bufWriter.write(obj.toString());
				_bufWriter.newLine();
				_bufWriter.flush();
				} catch (Exception e) { }
			}
		}
	}
	
	public static void printlnError(Object obj) {
		if (isDebugEnabled) {
			if (_bufWriter == null) System.out.println(obj);
			else {
				try {
				_bufWriter.write("Error: " + obj.toString());
				_bufWriter.newLine();
				_bufWriter.flush();
				} catch (Exception e) { }
			}
		}
	}
}
