package Logger;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class LoggerSFS {
	private static LoggerSFS singleton = new LoggerSFS();
	
	public SFSExtension extension;
	
	private LoggerSFS() {}
	
	public static LoggerSFS instance() {
		return singleton;
	}
	
	public static void log(String message) {
		instance().extension.trace(message);
	}
}
