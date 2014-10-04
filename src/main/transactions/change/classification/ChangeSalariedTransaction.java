package transactions.change.classification;

import paymentClassifications.PaymentClassification;
import paymentClassifications.SalariedClassification;
import paymentSchedules.MonthlySchedule;
import paymentSchedules.PaymentSchedule;

public class ChangeSalariedTransaction extends ChangeClassificationTransaction {

	private final double salary;

	public ChangeSalariedTransaction(int empId, double salary) {
		super(empId);
		this.salary = salary;
	}

	@Override
	protected PaymentSchedule getPaymentSchedule() {
		return new MonthlySchedule();
	}

	@Override
	protected PaymentClassification getPaymentClassification() {
		return new SalariedClassification(salary);
	}

}
