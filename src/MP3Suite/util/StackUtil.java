package MP3Suite.util;

public class StackUtil {
	public static StackTraceElement getCurrentStackTraceElement(int offset) {
		Thread t = Thread.currentThread();
		if (t != null) {
			StackTraceElement[] stes = t.getStackTrace();
			boolean gotmethod = false;
			String cname = StackUtil.class.getName();
			int steoffset = 0;
			for(StackTraceElement ste : stes) {
				if (gotmethod) {
					if (offset == steoffset++) {
						return ste;
					}
				} else if (ste.getClassName().equals(cname) && ste.getMethodName().equals("getCurrentStackTraceElement")) {
					gotmethod = true;
				}
			}
		}
		return null;
	}
	public static StackTraceElement getCurrentStackTraceElement() {
		return getCurrentStackTraceElement(1);
	}

	public static String DebugClass() {
		StackTraceElement ste = getCurrentStackTraceElement(1);
		if (ste != null) {
			return ste.getClassName() + "(" + ste.getFileName() + ")";
		}
		return "Unknown Class";
	}

	public static String DebugMethod() {
		return DebugMethod(0);
	}
	public static String DebugMethod(int lineOffset) {
		StackTraceElement ste = getCurrentStackTraceElement(1);
		if (ste != null) {
			return ste.getClassName() + "." + ste.getMethodName() + "(" + ste.getFileName() + ":" + (ste.getLineNumber()-lineOffset) + ")";
		}
		return "Unknown Method";
	}
}
