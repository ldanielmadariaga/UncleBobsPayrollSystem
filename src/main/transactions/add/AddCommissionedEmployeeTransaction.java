package transactions.add;

import paymentClassifications.CommissionedClassification;
import paymentClassifications.PaymentClassification;
import paymentSchedules.BiweeklySchedule;
import paymentSchedules.PaymentSchedule;

public class AddCommissionedEmployeeTransaction extends AddEmployeeTransaction {

	private double itsSalary;
	private double itsCommissionRate;

	public AddCommissionedEmployeeTransaction(int empId, String name,
			String address, double salary, double commisionRate) {
		super(empId, name, address);
		this.itsSalary = salary;
		this.itsCommissionRate = commisionRate;

	}

	@Override
	protected PaymentClassification makePaymentClassification() {
		return new CommissionedClassification(itsSalary, itsCommissionRate);
	}

	@Override
	protected PaymentSchedule makePaymentSchedule() {
		return new BiweeklySchedule();
	}

}
