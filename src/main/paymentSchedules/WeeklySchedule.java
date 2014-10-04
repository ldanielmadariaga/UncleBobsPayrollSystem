package paymentSchedules;

import java.util.Calendar;
import java.util.Date;

public class WeeklySchedule implements PaymentSchedule {

	@Override
	public boolean isPayDate(Date payDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(payDate);
		return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
	}

	@Override
	public Date getPayPeriodStartDate(Date payDate) {
		Calendar startDateCalendar = Calendar.getInstance();
		startDateCalendar.setTime(payDate);
		startDateCalendar.add(Calendar.DATE, -6);
		return startDateCalendar.getTime();
	}
}
