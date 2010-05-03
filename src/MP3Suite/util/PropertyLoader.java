package MP3Suite.util;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public abstract class PropertyLoader
{
    /**
     * Looks up a resource named 'name' in the classpath. The resource must map
     * to a file with .properties extention. The name is assumed to be absolute
     * and can use either "/" or "." for package segment separation with an
     * optional leading "/" and optional ".properties" suffix. Thus, the
     * following names refer to the same resource:
     * <pre>
     * some.pkg.Resource
     * some.pkg.Resource.properties
     * some/pkg/Resource
     * some/pkg/Resource.properties
     * /some/pkg/Resource
     * /some/pkg/Resource.properties
     * </pre>
     * 
     * @param name classpath resource name [may not be null]
     * @param loader classloader through which to load the resource [null
     * is equivalent to the application loader]
     * 
     * @return resource converted to java.util.Properties [may be null if the
     * resource was not found and THROW_ON_LOAD_FAILURE is false]
     * @throws IllegalArgumentException if the resource was not found and
     * THROW_ON_LOAD_FAILURE is true
     */
    public static Properties loadProperties (String name, ClassLoader loader, boolean localized, Locale locale)
    {
        if (name == null) {
            throw new IllegalArgumentException("null input: name");
	}
        
        if (name.startsWith ("/")) {
            name = name.substring(1);
	}
            
        if (name.endsWith(SUFFIX)) {
            name = name.substring(0, name.length() - SUFFIX.length());
	}
        
        Properties result = null;
        
        InputStream in = null;
        try
        {
            if (loader == null) {
		    loader = ClassLoader.getSystemClassLoader();
	    }
            
            if (localized)
            {    
                name = name.replace('/', '.');
                // Throws MissingResourceException on lookup failures:
                final ResourceBundle rb = ResourceBundle.getBundle(name, locale, loader);
                
                result = new Properties();
                for (Enumeration keys = rb.getKeys(); keys.hasMoreElements();)
                {
                    final String key = (String)keys.nextElement();
                    final String value = rb.getString(key);
                    
                    result.put(key, value);
                } 
            }
            else
            {
                name = name.replace('.', '/');
                
                if (!name.endsWith(SUFFIX))
                    name = name.concat(SUFFIX);
                                
                // Returns null on lookup failures:

		in = loader.getResourceAsStream(name);
		if (in==null) {
			URL u = loader.getResource("MP3Suite/config/defaultConfig.properties");
			String u2 = u.toString();
			if (u2.startsWith("jar:")) {
				u2 = u2.substring(4);
				String[] u3 = u2.split("/");
				u2 = "";
				for (int i = 0; i < u3.length; i++) {
					if (u3[i].endsWith(".jar!")) {
						u3[i]="";
						i=u3.length;
					} else if (i < u3.length-1) {
						u2 += u3[i] + "/";
					} else {
						u2 += u3[i];
					}
				}
				u2 = u2.replace ("//","/");
				u = new URL(u2 + name);
				in = u.openStream();
			}
		}
		if (in != null)
		{
			result = new Properties();
			result.load(in); // Can throw IOException
		}
	    }
	}
	catch (Exception e)
	{
		result = null;
	}
	finally
	{
		if (in != null) {
		    try { 
			    in.close (); 
		    } catch (Throwable ignore) {}
	    }
        }
        
        if (THROW_ON_LOAD_FAILURE && (result == null))
        {
            throw new IllegalArgumentException("could not load [" + name + "]"+
                " as " + (localized
                ? "a resource bundle"
                : "a classloader resource"));
        }
        
        return result;
    }

    /**
     * A convenience overload of {@link #loadProperties(String, ClassLoader, boolean)}
     * that uses a classloader to load a non-localized resource.
     */
    public static Properties loadProperties (String name, ClassLoader loader)
    {
        return loadProperties (name, loader, false, null);
    }
    
    /**
     * A convenience overload of {@link #loadProperties(String, ClassLoader, boolean)}
     * that uses the current thread's context classloader to load a non-localized resource.
     */
    public static Properties loadProperties (final String name)
    {
        return loadProperties (name, Thread.currentThread().getContextClassLoader(), false, null);
    }

    /**
     * A convenience overload of {@link #loadProperties(String, ClassLoader, boolean)}
     * that uses the current thread's context classloader to load a posibly localized resource.
     */
    public static Properties loadProperties (final String name, Locale locale)
    {
        return loadProperties (name, Thread.currentThread().getContextClassLoader(), true, locale);
    }
        
    private static final boolean THROW_ON_LOAD_FAILURE = true;
    private static final String SUFFIX = ".properties";
} // End of class

