package paymentClassification;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

import transactions.add.AddHourlyEmployeeTransaction;
import transactions.paymentClassifications.ServiceChargeTransaction;
import affiliations.UnionAffiliation;
import businessObjects.Employee;
import businessObjects.PayrollDatabase;
import businessObjects.ServiceCharge;

public class AddServiceChargeTests {

	@Test
	public void addServiceChargeTest() {

		int empId = 2;
		Date expectedDate = new Date(System.currentTimeMillis());

		AddHourlyEmployeeTransaction addEmployeeTransaction = new AddHourlyEmployeeTransaction(empId, "Bill",
				"Home", 15.25);
		addEmployeeTransaction.execute();

		Employee employee = PayrollDatabase.getEmployee(empId);
		assertNotNull(employee);

		UnionAffiliation unionAffiliation = new UnionAffiliation();
		employee.setAffiliation(unionAffiliation);

		int memberId = 86; // Maxwell Smart
		PayrollDatabase.addUnionMember(memberId, employee);

		ServiceChargeTransaction serviceChargeTransaction = new ServiceChargeTransaction(memberId, expectedDate,
				12.95);
		serviceChargeTransaction.execute();

		ServiceCharge serviceCharge = unionAffiliation.getServiceCharge(expectedDate);
		assertNotNull(serviceCharge);
		assertEquals(12.95, serviceCharge.getAmount(), .0);
	}
}
