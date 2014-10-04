package payEmployee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import transactions.add.AddHourlyEmployeeTransaction;
import transactions.change.ChangeMemberTransaction;
import transactions.pay.PaydayTransaction;
import transactions.paymentClassifications.ServiceChargeTransaction;
import transactions.paymentClassifications.TimeCardTransaction;
import businessObjects.Paycheck;

public class PayHourlyEmployeesTests {

	@Test
	public void testPaySingleHourlyEmployeeNoTimeCards() throws ParseException {
		int empId = 2;
		AddHourlyEmployeeTransaction addHourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId,
				"Bill", "Home", 15.25);
		addHourlyEmployeeTransaction.execute();

		Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-11"); // Friday

		PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
		paydayTransaction.execute();

		validateHourlyPaycheck(paydayTransaction, empId, payDate, 0.0, 0.0);
	}

	@Test
	public void testPaySingleHourlyEmployeeOnWrongDate() throws ParseException {
		int empId = 2;
		AddHourlyEmployeeTransaction addHourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId,
				"Bill", "Home", 15.25);
		addHourlyEmployeeTransaction.execute();

		Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-10"); // Thursday

		TimeCardTransaction timeCardTransaction = new TimeCardTransaction(payDate, 2.0, empId);
		timeCardTransaction.execute();

		PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
		paydayTransaction.execute();

		Paycheck paycheck = paydayTransaction.getPaycheck(empId);
		assertNull(paycheck);
	}

	@Test
	public void testPaySingleHourlyEmployeeOneTimeCard() throws ParseException {
		int empId = 2;
		AddHourlyEmployeeTransaction addHourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId,
				"Bill", "Home", 15.25);
		addHourlyEmployeeTransaction.execute();

		Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-11"); // Friday

		TimeCardTransaction timeCardTransaction = new TimeCardTransaction(payDate, 2.0, empId);
		timeCardTransaction.execute();

		PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
		paydayTransaction.execute();

		validateHourlyPaycheck(paydayTransaction, empId, payDate, 0.0, 30.5);
	}

	@Test
	public void testPaySingleHourlyEmployeeTwoTimeCards() throws ParseException {
		int empId = 2;
		AddHourlyEmployeeTransaction addHourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId,
				"Bill", "Home", 15.25);
		addHourlyEmployeeTransaction.execute();

		Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-11"); // Friday
		Date secondPayDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-10"); // Thursday

		TimeCardTransaction timeCardTransaction = new TimeCardTransaction(payDate, 2.0, empId);
		timeCardTransaction.execute();

		TimeCardTransaction secondTimeCardTransaction = new TimeCardTransaction(secondPayDate, 5.0, empId);
		secondTimeCardTransaction.execute();

		PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
		paydayTransaction.execute();

		validateHourlyPaycheck(paydayTransaction, empId, payDate, 0.0, 7 * 15.25);
	}

	@Test
	public void testPaySingleHourlyEmployeeWithTimeCardsSpanningTwoPayPeriods() throws ParseException {
		int empId = 2;
		AddHourlyEmployeeTransaction addHourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId,
				"Bill", "Home", 15.25);
		addHourlyEmployeeTransaction.execute();

		Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-11"); // Friday
		Date secondPayDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-01"); // Previous period

		TimeCardTransaction timeCardTransaction = new TimeCardTransaction(payDate, 2.0, empId);
		timeCardTransaction.execute();

		TimeCardTransaction secondTimeCardTransaction = new TimeCardTransaction(secondPayDate, 5.0, empId);
		secondTimeCardTransaction.execute();

		PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
		paydayTransaction.execute();

		validateHourlyPaycheck(paydayTransaction, empId, payDate, 0.0, 2 * 15.25);
	}

	@Test
	public void testPaySingleHourlyEmployeeOvertimeOneTimeCard() throws ParseException {
		int empId = 2;
		AddHourlyEmployeeTransaction addHourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId,
				"Bill", "Home", 15.25);
		addHourlyEmployeeTransaction.execute();

		Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-11"); // Friday

		TimeCardTransaction timeCardTransaction = new TimeCardTransaction(payDate, 9.0, empId);
		timeCardTransaction.execute();

		PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
		paydayTransaction.execute();

		validateHourlyPaycheck(paydayTransaction, empId, payDate, 0.0, (8 + 1.5) * 15.25);
	}

	@Test
	public void testHourlyUnionMemberServiceCharge() throws ParseException {
		int empId = 2;
		AddHourlyEmployeeTransaction addHourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId,
				"Bill", "Home", 15.24);
		addHourlyEmployeeTransaction.execute();

		int memberId = 7734;
		ChangeMemberTransaction changeMemberTransaction = new ChangeMemberTransaction(empId, memberId, 9.42);
		changeMemberTransaction.execute();

		Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-11");

		ServiceChargeTransaction serviceChargeTransaction = new ServiceChargeTransaction(memberId, payDate, 19.42);
		serviceChargeTransaction.execute();

		TimeCardTransaction timeCardTransaction = new TimeCardTransaction(payDate, 8.0, empId);
		timeCardTransaction.execute();

		PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
		paydayTransaction.execute();

		validateHourlyPaycheck(paydayTransaction, empId, payDate, 9.42 + 19.42, 8 * 15.24);
	}

	@Test
	public void testUnionServiceChargesSpanningMultiplePayPeriods() throws ParseException {
		int empId = 2;
		AddHourlyEmployeeTransaction addHourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId,
				"Bill", "Home", 15.24);
		addHourlyEmployeeTransaction.execute();

		int memberId = 7734;
		ChangeMemberTransaction changeMemberTransaction = new ChangeMemberTransaction(empId, memberId, 9.42);
		changeMemberTransaction.execute();

		Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-11");
		Date earlyDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-04");
		Date lateDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-19");

		ServiceChargeTransaction serviceChargeTransaction = new ServiceChargeTransaction(memberId, payDate, 19.42);
		serviceChargeTransaction.execute();
		ServiceChargeTransaction earlyServiceChargeTransaction = new ServiceChargeTransaction(memberId, earlyDate,
				100);
		earlyServiceChargeTransaction.execute();
		ServiceChargeTransaction lateServiceChargeTransaction = new ServiceChargeTransaction(memberId, lateDate,
				200);
		lateServiceChargeTransaction.execute();

		TimeCardTransaction timeCardTransaction = new TimeCardTransaction(payDate, 8.0, empId);
		timeCardTransaction.execute();

		PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
		paydayTransaction.execute();

		validateHourlyPaycheck(paydayTransaction, empId, payDate, 9.42 + 19.42, 8 * 15.24);

	}

	private void validateHourlyPaycheck(PaydayTransaction paydayTransaction, int empId, Date payDate,
			double deductions, double pay) {
		Paycheck paycheck = paydayTransaction.getPaycheck(empId);

		assertNotNull(paycheck);
		assertEquals(payDate, paycheck.getPayPeriodEndDate());
		assertEquals(pay, paycheck.getGrossPay(), 0.0);
		assertEquals(deductions, paycheck.getDeductions(), 0.0);
		assertEquals("Hold", paycheck.getField("Disposition"));
		assertEquals(pay - deductions, paycheck.getNetPay(), 0.0);

	}
}
