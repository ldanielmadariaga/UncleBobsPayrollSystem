package addEmployee;

import org.junit.Assert;
import org.junit.Test;

import paymentClassifications.HourlyClassification;
import paymentClassifications.PaymentClassification;
import paymentMethods.HoldMethod;
import paymentMethods.PaymentMethod;
import paymentSchedules.PaymentSchedule;
import paymentSchedules.WeeklySchedule;
import transactions.add.AddHourlyEmployeeTransaction;
import businessObjects.Employee;
import businessObjects.PayrollDatabase;

public class AddHourlyEmployeeTests {

	private static final String EXPECTED_ADDRESS = "Home";
	private static final double EXPECTED_HOURLY_RATE = 2.50;
	private static final String EXPECTED_NAME = "Bob";

	@Test
	public void test() {
		int i = 1;
		int j = 2;
		String blaString = "bla";

		Assert.assertEquals("bla2", "bla" + i);
		Assert.assertEquals("bla2", "bla" + j);
	}

	@Test
	public void hourlyEmployeeIsAddedSuccessfully() {
		int empId = 1;
		AddHourlyEmployeeTransaction addHourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId,
				EXPECTED_NAME, EXPECTED_ADDRESS, EXPECTED_HOURLY_RATE);
		addHourlyEmployeeTransaction.execute();
		Employee employee = PayrollDatabase.getEmployee(empId);

		// TODO Extract Assert messages to constants maybe
		Assert.assertEquals("Unexpected name:", EXPECTED_NAME, employee.getItsName());
		PaymentClassification paymentClassification = employee.getPaymentClassification();
		Assert.assertTrue("The payment classification is incorrect",
				paymentClassification instanceof HourlyClassification);
		HourlyClassification hourlyClassification = (HourlyClassification) paymentClassification;
		Assert.assertEquals("Unexpected hourly rate", EXPECTED_HOURLY_RATE,
				hourlyClassification.getItsHourlyRate(), .001);
		PaymentSchedule paymentSchedule = employee.getPaymentSchedule();
		Assert.assertTrue("The schedule type is incorrect", paymentSchedule instanceof WeeklySchedule);
		PaymentMethod paymentMethod = employee.getPaymentMethod();
		Assert.assertTrue("The payment method is incorrect", paymentMethod instanceof HoldMethod);

	}
}
