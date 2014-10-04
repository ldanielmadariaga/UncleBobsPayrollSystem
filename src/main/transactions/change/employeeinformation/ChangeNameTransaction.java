package transactions.change.employeeinformation;

import transactions.change.ChangeEmployeeTransaction;
import businessObjects.Employee;

public class ChangeNameTransaction extends ChangeEmployeeTransaction {

	private final String newName;

	public ChangeNameTransaction(int empId, String newName) {
		super(empId);
		this.newName = newName;
	}

	@Override
	protected void change(Employee employee) {
		employee.setItsName(newName);
	}

}
