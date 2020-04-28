package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String format(String pattern) {
		// pattern: YYYY-MM-dd
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		
		String date = sf.format(new Date());
		
		return date;
	}
}
