package paymentClassification;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import paymentClassifications.HourlyClassification;
import paymentClassifications.PaymentClassification;
import transactions.add.AddHourlyEmployeeTransaction;
import transactions.paymentClassifications.TimeCardTransaction;
import businessObjects.Employee;
import businessObjects.PayrollDatabase;
import businessObjects.TimeCard;

public class TimeCardTransactionTests {

	@Test
	public void testTimeCardTransaction() {
		int empId = 5;
		AddHourlyEmployeeTransaction addEmployeeTransaction = new AddHourlyEmployeeTransaction(empId, "Bill",
				"Home", 15.25);
		addEmployeeTransaction.execute();
		Date expectedDate = new Date(System.currentTimeMillis());

		TimeCardTransaction timeCardTransaction = new TimeCardTransaction(expectedDate, 8.0, empId);
		timeCardTransaction.execute();

		Employee employee = PayrollDatabase.getEmployee(empId);
		assertNotNull(employee);

		PaymentClassification paymentClassification = employee.getPaymentClassification();
		assertTrue(paymentClassification instanceof HourlyClassification);

		HourlyClassification hourlyClassification = (HourlyClassification) paymentClassification;
		TimeCard timeCard = hourlyClassification.getTimeCard(expectedDate);
		assertNotNull(timeCard);
		assertEquals("", 8.0, timeCard.getHours(), .0);
	}
}
