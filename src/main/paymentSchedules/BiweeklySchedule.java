package paymentSchedules;

import java.util.Calendar;
import java.util.Date;

public class BiweeklySchedule implements PaymentSchedule {

	@Override
	public boolean isPayDate(Date payDate) {
		return isFridayOfFirstOrThirdWeek(payDate);
	}

	public boolean isFridayOfFirstOrThirdWeek(Date payDate) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(payDate);
		return (isFirstOrThirdWeek(calendar) && isFriday(calendar));

	}

	private boolean isFriday(Calendar calendar) {
		return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
	}

	private boolean isFirstOrThirdWeek(Calendar calendar) {
		int dayOfWeekInMonth = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		return (dayOfWeekInMonth == 1) || (dayOfWeekInMonth == 3);
	}

	@Override
	public Date getPayPeriodStartDate(Date payDate) {
		Calendar startDateCalendar = Calendar.getInstance();
		startDateCalendar.setTime(payDate);
		startDateCalendar.add(Calendar.DATE, -14);
		return startDateCalendar.getTime();
	}
}
