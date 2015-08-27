package logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.*;

public class Loggers {
	private static Logger ACTION_LOG;
	private static Logger ERROR_LOG;
	private static FileHandler Actionfh;
	private static FileHandler Errorfh;
	private static LoggerFormatter actionFormatter;
	private static LoggerFormatter errorFormatter;
    private static final SimpleDateFormat date_format = new SimpleDateFormat("ddMMyy-HHmmss");
    private static boolean isInitialized = false;

    public static void makeLoggers() {
    	isInitialized = true;
		actionFormatter = new LoggerFormatter("Actions");
		errorFormatter = new LoggerFormatter("Errors");
		ACTION_LOG = Logger.getLogger("ActionsLog");
		ERROR_LOG = Logger.getLogger("ErrorsLog");

		try {
            if (!Files.exists(Paths.get("logs"))) {
                Files.createDirectory(Paths.get("logs"));
            }

            ACTION_LOG.setUseParentHandlers(false);
			Actionfh = new FileHandler("Logs"+System.getProperty("file.separator")+"ActionsLog"+date_format.format(new Date())+".htm");
			Actionfh.setFormatter(actionFormatter);
			ACTION_LOG.addHandler(Actionfh);
            ACTION_LOG.setLevel(Level.INFO);

            ERROR_LOG.setUseParentHandlers(false);
			Errorfh = new FileHandler("Logs"+System.getProperty("file.separator")+"ErrorsLog"+date_format.format(new Date())+".htm");
			Errorfh.setFormatter(errorFormatter);
			ERROR_LOG.addHandler(Errorfh);
			ERROR_LOG.setLevel(Level.WARNING);

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error init loggers!");
		}
		
		logError("System started!");
		logAction("System started!");
	}

	public static void closeLoggers() {
		logError("System exited!");
		logAction("System existed!");
		
		Actionfh.close();
		Errorfh.close();
	}

	public static void logError(String msg) {
		if (!isInitialized) {
			makeLoggers();
		}
		ERROR_LOG.warning(msg);
	}

	public static void logAction(String msg) {
		if (!isInitialized) {
			makeLoggers();
		}
		ACTION_LOG.info(msg);
	}
}