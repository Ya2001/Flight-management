package flights;

import java.util.Calendar;
import java.util.Random;

public class Utils {

	public static String genenerateRandomID(char prefix) {
		Random rnd = new Random();
		int digits = 10;
		StringBuilder sb = new StringBuilder(digits + 1);
		sb.append(prefix);
		for (int i = 0; i < digits; i++)
			sb.append((char) ('0' + rnd.nextInt(10)));
		return sb.toString();
	}

	public static String showDate(Calendar date) {
		Integer day = Integer.valueOf(date.get(Calendar.DAY_OF_MONTH));
		Integer month = Integer.valueOf(date.get(Calendar.MONTH));
		Integer year = Integer.valueOf(date.get(Calendar.YEAR));
		return year + "-" + month + "-" + day;
	}

}
