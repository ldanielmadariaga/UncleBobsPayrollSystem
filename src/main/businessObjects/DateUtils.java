package businessObjects;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static boolean isInPayPeriod(Date theDate, Paycheck paycheck) {
		Calendar startPeriodCalendar = Calendar.getInstance();
		startPeriodCalendar.setTime(paycheck.getPayPeriodStartDate());
		startPeriodCalendar.add(Calendar.DATE, -1);
		Date payPeriodStartDate = startPeriodCalendar.getTime();

		Calendar endPeriodCalendar = Calendar.getInstance();
		endPeriodCalendar.setTime(paycheck.getPayPeriodEndDate());
		endPeriodCalendar.add(Calendar.DATE, +1);
		Date payPeriodEndDate = endPeriodCalendar.getTime();

		return (theDate.after(payPeriodStartDate)) && (theDate.before(payPeriodEndDate));
	}
}
