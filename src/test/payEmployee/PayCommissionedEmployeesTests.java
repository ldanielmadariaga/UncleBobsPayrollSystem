package payEmployee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import transactions.add.AddCommissionedEmployeeTransaction;
import transactions.pay.PaydayTransaction;
import transactions.paymentClassifications.SalesReceiptTransaction;
import businessObjects.Paycheck;

public class PayCommissionedEmployeesTests {

	@Test
	public void testPayCommissionedEmployeeNoPayslips() throws ParseException {

		int empId = 77;
		AddCommissionedEmployeeTransaction addCommissionedEmployeeTransaction = new AddCommissionedEmployeeTransaction(
				empId, "Bill", "Home", 1500, 10.0);
		addCommissionedEmployeeTransaction.execute();

		Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-04");

		PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
		paydayTransaction.execute();

		ValidateCommissionedPaycheck(paydayTransaction, empId, payDate, 1500);
	}

	@Test
	public void testPayCommissionedEmployeeOutsidePaydate() throws ParseException {

		int empId = 77;
		AddCommissionedEmployeeTransaction addCommissionedEmployeeTransaction = new AddCommissionedEmployeeTransaction(
				empId, "Bill", "Home", 1500, 10.0);
		addCommissionedEmployeeTransaction.execute();

		Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-25");

		PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
		paydayTransaction.execute();

		Paycheck paycheck = paydayTransaction.getPaycheck(empId);
		assertNull(paycheck);
	}

	@Test
	public void testPayCommissionedEmployeeOnePayslipWithinPayPeriod() throws ParseException {

		int empId = 77;
		AddCommissionedEmployeeTransaction addCommissionedEmployeeTransaction = new AddCommissionedEmployeeTransaction(
				empId, "Bill", "Home", 1500, 10.0);
		addCommissionedEmployeeTransaction.execute();

		Date saleDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-01");
		SalesReceiptTransaction salesReceiptTransaction = new SalesReceiptTransaction(saleDate, 1000, empId);
		salesReceiptTransaction.execute();

		Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-04");
		PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
		paydayTransaction.execute();

		ValidateCommissionedPaycheck(paydayTransaction, empId, payDate, 1600);
	}

	@Test
	public void testPayCommissionedEmployeeOnePayslipBeforePayPeriod() throws ParseException {

		int empId = 77;
		AddCommissionedEmployeeTransaction addCommissionedEmployeeTransaction = new AddCommissionedEmployeeTransaction(
				empId, "Bill", "Home", 1400, 10.0);
		addCommissionedEmployeeTransaction.execute();

		Date saleDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-03-13");
		SalesReceiptTransaction salesReceiptTransaction = new SalesReceiptTransaction(saleDate, 1000, empId);
		salesReceiptTransaction.execute();

		Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-04");
		PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
		paydayTransaction.execute();

		ValidateCommissionedPaycheck(paydayTransaction, empId, payDate, 1400);
	}

	@Test
	public void testPayCommissionedEmployeeOnePayslipAfterPayPeriod() throws ParseException {

		int empId = 77;
		AddCommissionedEmployeeTransaction addCommissionedEmployeeTransaction = new AddCommissionedEmployeeTransaction(
				empId, "Bill", "Home", 1400, 10.0);
		addCommissionedEmployeeTransaction.execute();

		Date saleDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-10");
		SalesReceiptTransaction salesReceiptTransaction = new SalesReceiptTransaction(saleDate, 1000, empId);
		salesReceiptTransaction.execute();

		Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-04");
		PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
		paydayTransaction.execute();

		ValidateCommissionedPaycheck(paydayTransaction, empId, payDate, 1400);
	}

	@Test
	public void testPayCommissionedEmployeeOnePayslipWithinPayPeriodAndOnePaySlipOutsidePayPeriod()
			throws ParseException {

		int empId = 77;
		AddCommissionedEmployeeTransaction addCommissionedEmployeeTransaction = new AddCommissionedEmployeeTransaction(
				empId, "Bill", "Home", 1500, 10.0);
		addCommissionedEmployeeTransaction.execute();

		Date saleDateInPayPeriod = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-01");
		SalesReceiptTransaction salesReceiptTransactionInPayPeriod = new SalesReceiptTransaction(
				saleDateInPayPeriod, 1000, empId);
		salesReceiptTransactionInPayPeriod.execute();

		Date saleDateOutsidePayPeriod = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-10");
		SalesReceiptTransaction salesReceiptTransactionOutsidePayPeriod = new SalesReceiptTransaction(
				saleDateOutsidePayPeriod, 2000, empId);
		salesReceiptTransactionOutsidePayPeriod.execute();

		Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-04");
		PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
		paydayTransaction.execute();

		ValidateCommissionedPaycheck(paydayTransaction, empId, payDate, 1600);
	}

	@Test
	public void testPayCommissionedEmployeeTwoPayslipsWithinPayPeriod() throws ParseException {

		int empId = 77;
		AddCommissionedEmployeeTransaction addCommissionedEmployeeTransaction = new AddCommissionedEmployeeTransaction(
				empId, "Bill", "Home", 1500, 10.0);
		addCommissionedEmployeeTransaction.execute();

		Date saleDateInPayPeriod = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-01");
		SalesReceiptTransaction salesReceiptTransaction = new SalesReceiptTransaction(saleDateInPayPeriod, 1000,
				empId);
		salesReceiptTransaction.execute();

		Date saleDateOutsidePayPeriod = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-03");
		SalesReceiptTransaction salesReceiptTransaction2 = new SalesReceiptTransaction(saleDateOutsidePayPeriod,
				2000, empId);
		salesReceiptTransaction2.execute();

		Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-04");
		PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
		paydayTransaction.execute();

		ValidateCommissionedPaycheck(paydayTransaction, empId, payDate, 1800);
	}

	private void ValidateCommissionedPaycheck(PaydayTransaction paydayTransaction, int empId, Date payDate,
			double pay) {
		Paycheck paycheck = paydayTransaction.getPaycheck(empId);

		assertNotNull(paycheck);
		assertEquals(payDate, paycheck.getPayPeriodEndDate());
		assertEquals(pay, paycheck.getGrossPay(), 0.0);
		assertEquals("Hold", paycheck.getField("Disposition"));
		assertEquals(pay, paycheck.getNetPay(), 0.0);

	}
}
