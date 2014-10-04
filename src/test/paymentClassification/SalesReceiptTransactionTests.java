package paymentClassification;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import paymentClassifications.CommissionedClassification;
import paymentClassifications.PaymentClassification;
import transactions.add.AddCommissionedEmployeeTransaction;
import transactions.paymentClassifications.SalesReceiptTransaction;
import businessObjects.Employee;
import businessObjects.PayrollDatabase;
import businessObjects.SalesReceipt;

public class SalesReceiptTransactionTests {

	@Test
	public void testSalesReceiptTransaction() {
		int empId = 3;
		Date expectedDate = new Date(System.currentTimeMillis());

		AddCommissionedEmployeeTransaction addCommissionedEmployeeTransaction = new AddCommissionedEmployeeTransaction(
				empId, "Bill", "Home", 15.25, 0.15);
		addCommissionedEmployeeTransaction.execute();

		SalesReceiptTransaction salesReceiptTransaction = new SalesReceiptTransaction(expectedDate, 5, empId);
		salesReceiptTransaction.execute();

		Employee employee = PayrollDatabase.getEmployee(empId);
		assertNotNull(employee);

		PaymentClassification paymentClassification = employee.getPaymentClassification();
		assertTrue(paymentClassification instanceof CommissionedClassification);

		CommissionedClassification commissionedClassification = (CommissionedClassification) paymentClassification;
		SalesReceipt salesReceipt = commissionedClassification.getSalesReceipt(expectedDate);
		assertNotNull(commissionedClassification);
		assertEquals(5, salesReceipt.getAmount(), .0);

	}
}
