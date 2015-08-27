package logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LoggerFormatter extends Formatter {
	private String title;

	public LoggerFormatter(String title) {
		this.title = title;
	}

	@Override
	public String format(LogRecord rec) {
		StringBuffer buf = new StringBuffer(1000);

		if (rec.getLevel().intValue() >= Level.WARNING.intValue()) {
			buf.append("[<font color=\"red\">ERROR</font>@<font color=\"green\">");
		} else {
			buf.append("[<font color=\"blue\">ACTION</font>@<font color=\"green\">");
		}
		buf.append(calcDate() + "</font>] ");
		buf.append(formatMessage(rec));
		buf.append("<br/>");

		return buf.toString();
	}

	private String calcDate() {
		SimpleDateFormat date_format = new SimpleDateFormat("dd.MM.yy HH:mm:SS");
		Date resultdate = new Date(System.currentTimeMillis());
		return date_format.format(resultdate);
	}

	@Override
	public String getHead(Handler h) {
		StringBuffer sb = new StringBuffer(1000);
		sb.append("<!DOCTYPE html>" + "<html>" + "<head>"
				+ "<title>Forum Server " + title + " Log</title>"
				+ "</head><body>");
		return sb.toString();
	}

	@Override
	public String getTail(Handler h) {
		StringBuffer sb = new StringBuffer(1000);
		sb.append("</body></html>");
		return sb.toString();
	}
}