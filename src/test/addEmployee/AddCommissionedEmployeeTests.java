package addEmployee;

import org.junit.Assert;
import org.junit.Test;

import paymentClassifications.CommissionedClassification;
import paymentClassifications.PaymentClassification;
import paymentMethods.HoldMethod;
import paymentMethods.PaymentMethod;
import paymentSchedules.BiweeklySchedule;
import paymentSchedules.PaymentSchedule;
import transactions.add.AddCommissionedEmployeeTransaction;
import businessObjects.Employee;
import businessObjects.PayrollDatabase;

public class AddCommissionedEmployeeTests {

	private static final String EXPECTED_ADDRESS = "Home";
	private static final double EXPECTED_SALARY = 1000.00;
	private static final double EXPECTED_COMMISSION_RATE = 10.0;
	private static final String EXPECTED_NAME = "Bob";

	@Test
	public void commissionedEmployeeIsAddedSuccessfully() {
		int empId = 1;
		AddCommissionedEmployeeTransaction addCommissionedEmployeeTransaction = new AddCommissionedEmployeeTransaction(
				empId, EXPECTED_NAME, EXPECTED_ADDRESS, EXPECTED_SALARY,
				EXPECTED_COMMISSION_RATE);
		addCommissionedEmployeeTransaction.execute();
		Employee employee = PayrollDatabase.getEmployee(empId);

		// TODO Extract Assert messages to constants maybe
		Assert.assertEquals("Unexpected name:", EXPECTED_NAME,
				employee.getItsName());
		PaymentClassification paymentClassification = employee
				.getPaymentClassification();
		Assert.assertTrue("The payment classification is incorrect",
				paymentClassification instanceof CommissionedClassification);
		CommissionedClassification commissionedClassification = (CommissionedClassification) paymentClassification;
		Assert.assertEquals("Unexpected salary", EXPECTED_SALARY,
				commissionedClassification.getItsSalary(), .001);
		Assert.assertEquals("Unexpected commission rate",
				EXPECTED_COMMISSION_RATE,
				commissionedClassification.getItsCommissionRate(), .001);
		PaymentSchedule paymentSchedule = employee.getPaymentSchedule();
		Assert.assertTrue("The schedule type is incorrect",
				paymentSchedule instanceof BiweeklySchedule);
		PaymentMethod paymentMethod = employee.getPaymentMethod();
		Assert.assertTrue("The payment method is incorrect",
				paymentMethod instanceof HoldMethod);

	}
}
