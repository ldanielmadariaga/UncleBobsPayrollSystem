package deleteEmployee;

import org.junit.Assert;
import org.junit.Test;

import transactions.add.AddCommissionedEmployeeTransaction;
import transactions.delete.DeleteEmployeeTransaction;
import businessObjects.Employee;
import businessObjects.PayrollDatabase;

public class DeleteEmployeeTests {

	private final static int EMPLOYEE_ID_TO_REMOVE = 999;
	private final static String EMPLOYEE_NAME = "John Doe";
	private final static String EMPLOYEE_ADDRESS = "123 Evergreen Terrace";
	private final static double EMPLOYEE_SALARY = 1000;
	private final static double EMPLOYEE_COMMISION_RATE = 15;

	@Test
	public void shallSuccessfulyDeleteEmployee() {
		createAndAddTestEmployee();
		Employee retrievedEmployee = PayrollDatabase
				.getEmployee(EMPLOYEE_ID_TO_REMOVE);
		Assert.assertNotNull("Employee should not be null", retrievedEmployee);
		Assert.assertEquals("Unexpected employee id", EMPLOYEE_ID_TO_REMOVE,
				retrievedEmployee.getEmpId());
		Assert.assertEquals("Unexpected employee name", EMPLOYEE_NAME,
				retrievedEmployee.getItsName());
		Assert.assertEquals("Unexpected employee address", EMPLOYEE_ADDRESS,
				retrievedEmployee.getItsAddress());
		DeleteEmployeeTransaction deleteEmployeeTransaction = new DeleteEmployeeTransaction(
				EMPLOYEE_ID_TO_REMOVE);
		deleteEmployeeTransaction.execute();
		Assert.assertNull("Employee should be null",
				PayrollDatabase.getEmployee(EMPLOYEE_ID_TO_REMOVE));
	}

	private void createAndAddTestEmployee() {
		AddCommissionedEmployeeTransaction addCommissionedEmployeeTransaction = new AddCommissionedEmployeeTransaction(
				EMPLOYEE_ID_TO_REMOVE, EMPLOYEE_NAME, EMPLOYEE_ADDRESS,
				EMPLOYEE_SALARY, EMPLOYEE_COMMISION_RATE);
		addCommissionedEmployeeTransaction.execute();
	}
}
