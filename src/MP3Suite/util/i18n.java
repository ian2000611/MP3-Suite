package MP3Suite.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;

public abstract class i18n {
	private static Locale locale;
	static {
		Locale defaultLocale = Locale.getDefault();
		String language = Config.get("language", defaultLocale.getLanguage());
		String country = Config.get("country", defaultLocale.getCountry());
		String variant = Config.get("variant", defaultLocale.getVariant());
		locale = new Locale(language, country, variant);
	}
	
	private static Properties bundle = PropertyLoader.loadProperties("MP3Suite.i18n.i18n", locale);
	
	public static String get(String key, Object... args) {
		String classkey = StackUtil.getCurrentStackTraceElement(1).getClassName() + "." + key;
		if (bundle != null && bundle.containsKey(classkey)) {
			String message = bundle.getProperty(classkey);
			if (args != null) {
				MessageFormat formatter = new MessageFormat(message,locale);
				return formatter.format(args);
			}
			return message;
		}
		String missing = get("missingTranslation",classkey);
		throw new RuntimeException(missing);
	}
}

