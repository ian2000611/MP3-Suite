package MP3Suite.util;
import java.util.ResourceBundle;

public class config {
	private config() {}
	private static config instance = new config();
	private ResourceBundle bundle = ResourceBundle.getBundle(this.getClass().getName());
	public static String getSetting(String key) {
		return instance.bundle.getString(key);
	}
}
