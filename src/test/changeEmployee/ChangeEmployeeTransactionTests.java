package changeEmployee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import paymentClassifications.CommissionedClassification;
import paymentClassifications.HourlyClassification;
import paymentClassifications.PaymentClassification;
import paymentClassifications.SalariedClassification;
import paymentMethods.DirectMethod;
import paymentMethods.HoldMethod;
import paymentMethods.MailMethod;
import paymentSchedules.BiweeklySchedule;
import paymentSchedules.MonthlySchedule;
import paymentSchedules.PaymentSchedule;
import paymentSchedules.WeeklySchedule;
import transactions.add.AddCommissionedEmployeeTransaction;
import transactions.add.AddHourlyEmployeeTransaction;
import transactions.change.ChangeMemberTransaction;
import transactions.change.ChangeUnaffiliatedTransaction;
import transactions.change.classification.ChangeCommissionedTransaction;
import transactions.change.classification.ChangeHourlyTransaction;
import transactions.change.classification.ChangeSalariedTransaction;
import transactions.change.employeeinformation.ChangeAddressTransaction;
import transactions.change.employeeinformation.ChangeNameTransaction;
import transactions.change.paymentmethod.ChangeDirectTransaction;
import transactions.change.paymentmethod.ChangeHoldTransaction;
import transactions.change.paymentmethod.ChangeMailTransaction;
import affiliations.Affiliation;
import affiliations.NoAffiliation;
import affiliations.UnionAffiliation;
import businessObjects.Employee;
import businessObjects.PayrollDatabase;

public class ChangeEmployeeTransactionTests {

	@Test
	public void testChangeNameTransaction() {

		int empId = 7;
		AddHourlyEmployeeTransaction addHourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId,
				"Bill", "Home", 15.25);
		addHourlyEmployeeTransaction.execute();

		ChangeNameTransaction changeNameTransaction = new ChangeNameTransaction(empId, "Bob");
		changeNameTransaction.execute();

		Employee employee = PayrollDatabase.getEmployee(empId);
		assertNotNull(employee);
		assertEquals("Bob", employee.getItsName());

	}

	@Test
	public void testChangeAddressTransaction() {

		int empId = 7;
		AddHourlyEmployeeTransaction addHourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId,
				"Bill", "Home", 15.25);
		addHourlyEmployeeTransaction.execute();

		ChangeAddressTransaction changeAddressTransaction = new ChangeAddressTransaction(empId, "Away from home");
		changeAddressTransaction.execute();

		Employee employee = PayrollDatabase.getEmployee(empId);
		assertNotNull(employee);
		assertEquals("Away from home", employee.getItsAddress());

	}

	@Test
	public void testChangeHourlyTransaction() {
		int empId = 3;
		AddCommissionedEmployeeTransaction addCommissionedEmployeeTransaction = new AddCommissionedEmployeeTransaction(
				empId, "Lance", "Home", 2500, 3.2);
		addCommissionedEmployeeTransaction.execute();

		ChangeHourlyTransaction changeHourlyTransaction = new ChangeHourlyTransaction(empId, 27.52);
		changeHourlyTransaction.execute();

		Employee employee = PayrollDatabase.getEmployee(empId);
		assertNotNull(employee);

		PaymentClassification paymentClassification = employee.getPaymentClassification();
		assertNotNull(paymentClassification);
		assertTrue(paymentClassification instanceof HourlyClassification);

		HourlyClassification hourlyClassification = (HourlyClassification) paymentClassification;
		assertEquals(27.52, hourlyClassification.getItsHourlyRate(), 0.0);

		PaymentSchedule paymentSchedule = employee.getPaymentSchedule();
		assertTrue(paymentSchedule instanceof WeeklySchedule);
	}

	@Test
	public void testChangeSalariedTransaction() {
		int empId = 3;
		AddCommissionedEmployeeTransaction addCommissionedEmployeeTransaction = new AddCommissionedEmployeeTransaction(
				empId, "Lance", "Home", 2500, 3.2);
		addCommissionedEmployeeTransaction.execute();

		ChangeSalariedTransaction changeSalariedTransaction = new ChangeSalariedTransaction(empId, 4000);
		changeSalariedTransaction.execute();

		Employee employee = PayrollDatabase.getEmployee(empId);
		assertNotNull(employee);

		PaymentClassification paymentClassification = employee.getPaymentClassification();
		assertNotNull(paymentClassification);
		assertTrue(paymentClassification instanceof SalariedClassification);

		SalariedClassification salariedClassification = (SalariedClassification) paymentClassification;
		assertEquals(4000, salariedClassification.getItsSalary(), 0.0);

		PaymentSchedule paymentSchedule = employee.getPaymentSchedule();
		assertTrue(paymentSchedule instanceof MonthlySchedule);
	}

	@Test
	public void testChangeCommissionedTransaction() {
		int empId = 3;
		AddHourlyEmployeeTransaction addHourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId,
				"Lance", "Home", 8.5);
		addHourlyEmployeeTransaction.execute();

		ChangeCommissionedTransaction changeCommissionedTransaction = new ChangeCommissionedTransaction(empId,
				2500, 10.01);
		changeCommissionedTransaction.execute();

		Employee employee = PayrollDatabase.getEmployee(empId);
		assertNotNull(employee);

		PaymentClassification paymentClassification = employee.getPaymentClassification();
		assertNotNull(paymentClassification);
		assertTrue(paymentClassification instanceof CommissionedClassification);

		CommissionedClassification commissionedClassification = (CommissionedClassification) paymentClassification;
		assertEquals(2500, commissionedClassification.getItsSalary(), 0.0);
		assertEquals(10.01, commissionedClassification.getItsCommissionRate(), 0.0);

		PaymentSchedule paymentSchedule = employee.getPaymentSchedule();
		assertTrue(paymentSchedule instanceof BiweeklySchedule);
	}

	@Test
	public void testChangeMailTransaction() {
		int empId = 3;
		AddHourlyEmployeeTransaction addHourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId,
				"Lance", "Home", 8.5);
		addHourlyEmployeeTransaction.execute();

		Employee employee = PayrollDatabase.getEmployee(empId);
		assertNotNull(employee);
		assertTrue(employee.getPaymentMethod() instanceof HoldMethod);

		ChangeMailTransaction changeMailTransaction = new ChangeMailTransaction(empId);
		changeMailTransaction.execute();
		assertTrue(employee.getPaymentMethod() instanceof MailMethod);
	}

	@Test
	public void testChangeDirectTransaction() {
		int empId = 3;
		AddHourlyEmployeeTransaction addHourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId,
				"Lance", "Home", 8.5);
		addHourlyEmployeeTransaction.execute();

		Employee employee = PayrollDatabase.getEmployee(empId);
		assertNotNull(employee);
		assertTrue(employee.getPaymentMethod() instanceof HoldMethod);

		ChangeDirectTransaction changeDirectTransaction = new ChangeDirectTransaction(empId);
		changeDirectTransaction.execute();
		assertTrue(employee.getPaymentMethod() instanceof DirectMethod);
	}

	@Test
	public void testChangeHoldTransaction() {
		int empId = 3;
		AddHourlyEmployeeTransaction addHourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId,
				"Lance", "Home", 8.5);
		addHourlyEmployeeTransaction.execute();

		Employee employee = PayrollDatabase.getEmployee(empId);
		assertNotNull(employee);
		assertTrue(employee.getPaymentMethod() instanceof HoldMethod);

		ChangeMailTransaction changeMailTransaction = new ChangeMailTransaction(empId);
		changeMailTransaction.execute();
		assertTrue(employee.getPaymentMethod() instanceof MailMethod);

		ChangeHoldTransaction changeHoldTransaction = new ChangeHoldTransaction(empId);
		changeHoldTransaction.execute();
		assertTrue(employee.getPaymentMethod() instanceof HoldMethod);

	}

	@Test
	public void testChangeUnionMember() {
		int empId = 3;
		AddHourlyEmployeeTransaction addHourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId,
				"Lance", "Home", 8.5);
		addHourlyEmployeeTransaction.execute();

		int memberId = 7743;
		ChangeMemberTransaction changeMemberTransaction = new ChangeMemberTransaction(empId, memberId, 99.42);
		changeMemberTransaction.execute();

		Employee employee = PayrollDatabase.getEmployee(empId);
		assertNotNull(employee);

		Affiliation affiliation = employee.getAffiliation();
		assertNotNull(affiliation);
		assertTrue(affiliation instanceof UnionAffiliation);

		UnionAffiliation unionAffiliation = (UnionAffiliation) affiliation;
		assertEquals(99.42, unionAffiliation.getDues(), 0.0);

		Employee unionMember = PayrollDatabase.getUnionMember(memberId);
		assertNotNull(unionMember);
		assertEquals(employee, unionMember);
	}

	@Test
	public void testChangeUnaffiliatedMember() {
		int empId = 3;
		AddHourlyEmployeeTransaction addHourlyEmployeeTransaction = new AddHourlyEmployeeTransaction(empId,
				"Lance", "Home", 8.5);
		addHourlyEmployeeTransaction.execute();

		int memberId = 7743;
		ChangeMemberTransaction changeMemberTransaction = new ChangeMemberTransaction(empId, memberId, 99.42);
		changeMemberTransaction.execute();

		Employee employee = PayrollDatabase.getEmployee(empId);
		assertNotNull(employee);

		Affiliation affiliation = employee.getAffiliation();
		assertNotNull(affiliation);
		assertTrue(affiliation instanceof UnionAffiliation);

		UnionAffiliation unionAffiliation = (UnionAffiliation) affiliation;
		assertEquals(99.42, unionAffiliation.getDues(), 0.0);

		Employee unionMember = PayrollDatabase.getUnionMember(memberId);
		assertNotNull(unionMember);
		assertEquals(employee, unionMember);

		ChangeUnaffiliatedTransaction changeUnaffiliatedTransaction = new ChangeUnaffiliatedTransaction(empId);
		changeUnaffiliatedTransaction.execute();
		assertTrue(employee.getAffiliation() instanceof NoAffiliation);
	}
}
