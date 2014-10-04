package paymentSchedules;

import java.util.Calendar;
import java.util.Date;

public class MonthlySchedule implements PaymentSchedule {

	private boolean isLastDayOfMonth(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int actualMonth = calendar.get(Calendar.MONTH);
		calendar.add(Calendar.DATE, 1);
		int actualMonthPlusOneDay = calendar.get(Calendar.MONTH);

		return (actualMonth != actualMonthPlusOneDay);

	}

	public boolean isPayDate(Date payDate) {
		return isLastDayOfMonth(payDate);
	}

	@Override
	public Date getPayPeriodStartDate(Date payDate) {
		Calendar startDateCalendar = Calendar.getInstance();
		startDateCalendar.setTime(payDate);
		startDateCalendar.add(Calendar.MONTH, -1);
		startDateCalendar.add(Calendar.DATE, +1);
		return startDateCalendar.getTime();
	}
}
