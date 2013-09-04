package etri.sdn.controller.util;

import java.util.Calendar;
import java.util.Date;

public class Logger {
	static Calendar cal = Calendar.getInstance();
	static boolean debugTrace = false;
	
	private static Date getTime() {
		return Calendar.getInstance().getTime();
	}
	
	public static void setDebug(boolean enable) {
		debugTrace = enable;
	}
	
	public static void stderr(String string) {
		System.err.println("[Torpedo] (" + getTime().toString()+ ") " + string);
	}

	public static void stdout(String string) {
		System.out.println("[Torpedo] (" + getTime().toString()+ ") " + string);
	}
	
	public static void debug(String string) {
		if ( debugTrace ) {
			Logger.stdout(string);
		}
	}
	
	public static void debug(String format, Object ... arguments) {
		String formatString = format.replace("{}", "%s");
		Logger.debug( String.format(formatString, arguments) );
	}
	
	public static void error(String format, Object ... arguments) {
		String formatString = format.replace("{}", "%s");
		Logger.stderr( String.format(formatString, arguments) );
	}
}
