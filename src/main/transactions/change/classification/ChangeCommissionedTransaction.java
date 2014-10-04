package transactions.change.classification;

import paymentClassifications.CommissionedClassification;
import paymentClassifications.PaymentClassification;
import paymentSchedules.BiweeklySchedule;
import paymentSchedules.PaymentSchedule;

public class ChangeCommissionedTransaction extends ChangeClassificationTransaction {

	private final double salary;
	private final double commisionRate;

	public ChangeCommissionedTransaction(int empId, double salary, double commissionRate) {
		super(empId);
		this.salary = salary;
		this.commisionRate = commissionRate;
	}

	@Override
	protected PaymentSchedule getPaymentSchedule() {
		return new BiweeklySchedule();
	}

	@Override
	protected PaymentClassification getPaymentClassification() {
		return new CommissionedClassification(salary, commisionRate);
	}

}
