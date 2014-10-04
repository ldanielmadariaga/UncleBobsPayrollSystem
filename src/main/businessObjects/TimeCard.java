package businessObjects;

import java.util.Date;

public class TimeCard {

	private final double hours;
	private final Date date;

	public TimeCard(Date date, double hours) {
		this.date = date;
		this.hours = hours;
	}

	public double getHours() {
		return hours;
	}

	public Date getDate() {
		return date;
	}

}
