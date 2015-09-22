package xx.love.cc;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	
	/**
	 * 获取系统时间
	 * @return
	 */
	private synchronized static Calendar getCalendar(){
		return Calendar.getInstance();
	}
	
	/**
	 * 获取系统当前时间
	 * @return
	 */
	public static Timestamp getSysteCurTimeForDate(){
		Timestamp ts = new Timestamp(getCalendar().getTimeInMillis());
		return ts;
	}
	
	/**
	 * 获取系统当前时间
	 * @return
	 */
	public static long getSysteCurTimeForMill(){
		return getCalendar().getTimeInMillis();
	}
	
	/**
	 * 获取当前秒数
	 * 
	 * @return
	 */
	public static int getCurrentSecond() {
		return getCalendar().get(Calendar.SECOND);
	}
	
	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateFormat(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = formatter.format(date);
		return time;
	}
	
	
}
