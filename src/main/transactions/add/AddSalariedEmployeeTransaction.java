package transactions.add;

import paymentClassifications.PaymentClassification;
import paymentClassifications.SalariedClassification;
import paymentSchedules.MonthlySchedule;
import paymentSchedules.PaymentSchedule;

public class AddSalariedEmployeeTransaction extends AddEmployeeTransaction {

	private final double itsSalary;

	public AddSalariedEmployeeTransaction(int empId, String itsName, String itsAddress,
			double itsSalary) {
		super(empId, itsName, itsAddress);
		this.itsSalary = itsSalary;

	}

	@Override
	protected PaymentClassification makePaymentClassification() {
		return new SalariedClassification(itsSalary);
	}

	@Override
	protected PaymentSchedule makePaymentSchedule() {
		return new MonthlySchedule();
	}

}
