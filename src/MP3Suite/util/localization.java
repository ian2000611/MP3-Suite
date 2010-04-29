package MP3Suite.util;

import java.util.ResourceBundle;

public class localization {
	private localization() {}
	private static ResourceBundle bundle = ResourceBundle.getBundle("localization");
	public static String getString(String key) {
		return bundle.getString(StackUtil.getCurrentStackTraceElement(1).getClassName() + "." + key);
	}
}

