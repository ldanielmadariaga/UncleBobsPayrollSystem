package transactions.add;

import paymentClassifications.PaymentClassification;
import paymentMethods.HoldMethod;
import paymentMethods.PaymentMethod;
import paymentSchedules.PaymentSchedule;
import transactions.Transaction;
import affiliations.Affiliation;
import affiliations.NoAffiliation;
import businessObjects.Employee;
import businessObjects.PayrollDatabase;

public abstract class AddEmployeeTransaction implements Transaction {

	private final int empId;
	private final String itsAddress;
	private final String itsName;

	public AddEmployeeTransaction(int empId, String itsName, String itsAddress) {
		this.empId = empId;
		this.itsName = itsName;
		this.itsAddress = itsAddress;

	}

	@Override
	public void execute() {
		PaymentClassification paymentClassification = makePaymentClassification();
		PaymentSchedule paymentSchedule = makePaymentSchedule();
		PaymentMethod paymentMethod = new HoldMethod();
		Affiliation affiliation = new NoAffiliation();

		Employee employee = new Employee(this.empId, this.itsName, this.itsAddress);
		employee.setAffiliation(affiliation);
		employee.setPaymentClassification(paymentClassification);
		employee.setPaymentMethod(paymentMethod);
		employee.setPaymentSchedule(paymentSchedule);
		PayrollDatabase.addEmployee(empId, employee);

	}

	protected abstract PaymentClassification makePaymentClassification();

	protected abstract PaymentSchedule makePaymentSchedule();
}
