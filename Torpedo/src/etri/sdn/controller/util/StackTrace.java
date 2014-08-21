package etri.sdn.controller.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Utility class to convert StackTrace of java Throwable into readable string
 * that e.printStackTrace() outputs. 
 * 
 * @author bjlee
 *
 */
public class StackTrace {
	public static String of(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
}
