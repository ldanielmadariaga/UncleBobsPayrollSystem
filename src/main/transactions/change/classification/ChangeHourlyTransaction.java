package transactions.change.classification;

import paymentClassifications.HourlyClassification;
import paymentClassifications.PaymentClassification;
import paymentSchedules.PaymentSchedule;
import paymentSchedules.WeeklySchedule;

public class ChangeHourlyTransaction extends ChangeClassificationTransaction {

	private final double hourlyRate;

	public ChangeHourlyTransaction(int empId, double hourlyRate) {
		super(empId);
		this.hourlyRate = hourlyRate;
	}

	@Override
	protected PaymentSchedule getPaymentSchedule() {
		return new WeeklySchedule();
	}

	@Override
	protected PaymentClassification getPaymentClassification() {
		return new HourlyClassification(hourlyRate);
	}

}
