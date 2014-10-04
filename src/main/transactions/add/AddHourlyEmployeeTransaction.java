package transactions.add;

import paymentClassifications.HourlyClassification;
import paymentClassifications.PaymentClassification;
import paymentSchedules.PaymentSchedule;
import paymentSchedules.WeeklySchedule;

public class AddHourlyEmployeeTransaction extends AddEmployeeTransaction {

	private double itsHourlyRate;

	public AddHourlyEmployeeTransaction(int empId, String name, String address,
			double hourlyRate) {
		super(empId, name, address);
		this.itsHourlyRate = hourlyRate;
	}

	@Override
	protected PaymentClassification makePaymentClassification() {
		return new HourlyClassification(itsHourlyRate);
	}

	@Override
	protected PaymentSchedule makePaymentSchedule() {
		return new WeeklySchedule();
	}

}
