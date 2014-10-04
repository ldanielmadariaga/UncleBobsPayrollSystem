package paymentClassifications;

import java.util.Date;
import java.util.Hashtable;

import businessObjects.DateUtils;
import businessObjects.Paycheck;
import businessObjects.TimeCard;

public class HourlyClassification implements PaymentClassification {

	private double itsHourlyRate;

	private Hashtable<Date, TimeCard> timeCards = new Hashtable<Date, TimeCard>();

	public HourlyClassification(double hourlyRate) {
		this.itsHourlyRate = hourlyRate;
	}

	public double getItsHourlyRate() {
		return this.itsHourlyRate;
	}

	public TimeCard getTimeCard(Date date) {
		TimeCard datedTimeCard = null;
		for (TimeCard timeCard : timeCards.values()) {
			if (timeCard.getDate().equals(date)) {
				datedTimeCard = timeCard;
			}
		}
		return datedTimeCard;
	}

	public void addTimeCard(TimeCard timeCard) {
		timeCards.put(timeCard.getDate(), timeCard);
	}

	@Override
	public double calculatePay(Paycheck paycheck) {
		double totalPay = 0.0;
		for (TimeCard timeCard : timeCards.values()) {
			if (DateUtils.isInPayPeriod(timeCard.getDate(), paycheck)) {
				totalPay += calculatePayForTimeCard(timeCard);
			}
		}
		return totalPay;
	}

	private double calculatePayForTimeCard(TimeCard timeCard) {
		double overtimeHours = Math.max(0.0, timeCard.getHours() - 8);
		double normalHours = timeCard.getHours() - overtimeHours;

		return (itsHourlyRate * normalHours) + (itsHourlyRate * 1.5 * overtimeHours);
	}
}
