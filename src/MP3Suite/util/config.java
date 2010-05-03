package MP3Suite.util;
import java.util.Properties;

public class Config {
	private Config() {}
	private static Properties defaultConfig = PropertyLoader.loadProperties("MP3Suite.config.defaultConfig");
	private static Properties myConfig = null;
	static {
	//	try {
			myConfig = PropertyLoader.loadProperties("MP3-Suite Configuration");
	//		myConfig.list(System.out);
	//	} catch (Exception e) {
			//don't complain about missing or invalid configuration
	//	}
	}
	public static String get(String key) {
		if (myConfig != null && myConfig.containsKey(key)) {
			return myConfig.getProperty(key);
		} else if (defaultConfig != null && defaultConfig.containsKey(key)) {
			return defaultConfig.getProperty(key);
		} else {
			return null;
		}
	}
	public static String get(String key, String defaultSetting) {
		if (myConfig != null && myConfig.containsKey(key)) {
			return myConfig.getProperty(key);
		} else if (defaultConfig != null && defaultConfig.containsKey(key)) {
			return defaultConfig.getProperty(key);
		} else {
			return defaultSetting;
		}
	}
}
