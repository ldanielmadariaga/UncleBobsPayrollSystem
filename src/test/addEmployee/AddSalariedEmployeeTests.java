package addEmployee;

import org.junit.Assert;
import org.junit.Test;

import paymentClassifications.PaymentClassification;
import paymentClassifications.SalariedClassification;
import paymentMethods.HoldMethod;
import paymentMethods.PaymentMethod;
import paymentSchedules.MonthlySchedule;
import paymentSchedules.PaymentSchedule;
import transactions.add.AddSalariedEmployeeTransaction;
import businessObjects.Employee;
import businessObjects.PayrollDatabase;

public class AddSalariedEmployeeTests {

	private static final String EXPECTED_ADDRESS = "Home";
	private static final double EXPECTED_SALARY = 1000.00;
	private static final String EXPECTED_NAME = "Bob";

	@Test
	public void salariedEmployeeIsAddedSuccessfully() {
		int empId = 1;
		AddSalariedEmployeeTransaction addSalariedEmployeeTransaction = new AddSalariedEmployeeTransaction(empId,
				EXPECTED_NAME, EXPECTED_ADDRESS, EXPECTED_SALARY);
		addSalariedEmployeeTransaction.execute();
		Employee employee = PayrollDatabase.getEmployee(empId);

		// TODO Extract Assert messages to constants maybe
		Assert.assertEquals("Unexpected name:", EXPECTED_NAME, employee.getItsName());
		PaymentClassification paymentClassification = employee.getPaymentClassification();
		Assert.assertTrue("The payment classification is incorrect",
				paymentClassification instanceof SalariedClassification);
		SalariedClassification salariedClassification = (SalariedClassification) paymentClassification;
		Assert.assertEquals("Unexpected salary", EXPECTED_SALARY, salariedClassification.getItsSalary(), .001);
		PaymentSchedule paymentSchedule = employee.getPaymentSchedule();
		Assert.assertTrue("The schedule type is incorrect", paymentSchedule instanceof MonthlySchedule);
		PaymentMethod paymentMethod = employee.getPaymentMethod();
		Assert.assertTrue("The payment method is incorrect", paymentMethod instanceof HoldMethod);

	}
}
