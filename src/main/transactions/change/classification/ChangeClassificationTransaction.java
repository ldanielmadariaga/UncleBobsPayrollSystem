package transactions.change.classification;

import paymentClassifications.PaymentClassification;
import paymentSchedules.PaymentSchedule;
import transactions.change.ChangeEmployeeTransaction;
import businessObjects.Employee;

public abstract class ChangeClassificationTransaction extends ChangeEmployeeTransaction {

	public ChangeClassificationTransaction(int empId) {
		super(empId);
	}

	@Override
	protected void change(Employee employee) {
		employee.setPaymentClassification(getPaymentClassification());
		employee.setPaymentSchedule(getPaymentSchedule());
	}

	protected abstract PaymentSchedule getPaymentSchedule();

	protected abstract PaymentClassification getPaymentClassification();
}
