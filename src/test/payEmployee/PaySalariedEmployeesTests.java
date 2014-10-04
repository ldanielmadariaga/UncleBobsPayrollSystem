package payEmployee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import transactions.add.AddSalariedEmployeeTransaction;
import transactions.change.ChangeMemberTransaction;
import transactions.pay.PaydayTransaction;
import businessObjects.Paycheck;

public class PaySalariedEmployeesTests {

	@Test
	public void paySingleSalariedEmployee() throws ParseException {
		int empId = 1;
		AddSalariedEmployeeTransaction addSalariedEmployeeTransaction = new AddSalariedEmployeeTransaction(empId,
				"Bob", "Home", 1000.0);
		addSalariedEmployeeTransaction.execute();

		Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse("2001-11-30");
		PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
		paydayTransaction.execute();

		Paycheck paycheck = paydayTransaction.getPaycheck(empId);

		validateSalariedPaycheck(paydayTransaction, empId, payDate, 0.0, 1000.0);
	}

	@Test
	public void paySingleSalariedEmployeeOnWrongDate() throws ParseException {

		int empId = 1;
		AddSalariedEmployeeTransaction addSalariedEmployeeTransaction = new AddSalariedEmployeeTransaction(empId,
				"Bob", "Home", 1000.0);
		addSalariedEmployeeTransaction.execute();

		Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse("2001-12-30");
		PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
		paydayTransaction.execute();
		Paycheck paycheck = paydayTransaction.getPaycheck(empId);

		assertNull(paycheck);

	}

	@Test
	public void salariedUnionMemberDues() throws ParseException {
		int empId = 1;
		AddSalariedEmployeeTransaction addSalariedEmployeeTransaction = new AddSalariedEmployeeTransaction(empId,
				"Bob", "Home", 1000.0);
		addSalariedEmployeeTransaction.execute();

		int memberId = 7734;
		ChangeMemberTransaction changeMemberTransaction = new ChangeMemberTransaction(empId, memberId, 9.42);
		changeMemberTransaction.execute();

		Date payDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-30");
		PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
		paydayTransaction.execute();

		validateSalariedPaycheck(paydayTransaction, empId, payDate, 9.42 * 4, 1000);

	}

	private void validateSalariedPaycheck(PaydayTransaction paydayTransaction, int empId, Date payDate,
			double deductions, double pay) {
		Paycheck paycheck = paydayTransaction.getPaycheck(empId);

		assertNotNull(paycheck);
		assertEquals(payDate, paycheck.getPayPeriodEndDate());
		assertEquals(pay, paycheck.getGrossPay(), 0.0);
		assertEquals("Hold", paycheck.getField("Disposition"));
		assertEquals(deductions, paycheck.getDeductions(), 0.0);
		assertEquals(pay - deductions, paycheck.getNetPay(), 0.0);

	}
}
