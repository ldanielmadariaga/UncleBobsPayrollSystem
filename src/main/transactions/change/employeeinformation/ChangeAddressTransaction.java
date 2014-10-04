package transactions.change.employeeinformation;

import transactions.change.ChangeEmployeeTransaction;
import businessObjects.Employee;

public class ChangeAddressTransaction extends ChangeEmployeeTransaction {

	private final String newAddress;

	public ChangeAddressTransaction(int empId, String newAddress) {
		super(empId);
		this.newAddress = newAddress;
	}

	@Override
	protected void change(Employee employee) {
		employee.setItsAddress(newAddress);
	}

}
