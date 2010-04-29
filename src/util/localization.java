package util;
import java.util.ResourceBundle;

public class localization {
	private localization() {}
	private static localization instance = new localization();
	private ResourceBundle bundle = ResourceBundle.getBundle(this.getClass().getName());
	public static String getString(String key) {
		return instance.bundle.getString(key);
	}
}

