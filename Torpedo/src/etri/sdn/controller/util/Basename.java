package etri.sdn.controller.util;

public class Basename {
	public static String get(String path, String extension) {
		String[] delimited = path.split("/");
		String filename = delimited[ delimited.length - 1 ];
		return filename.substring(0, filename.lastIndexOf("." + extension));
	}
}
