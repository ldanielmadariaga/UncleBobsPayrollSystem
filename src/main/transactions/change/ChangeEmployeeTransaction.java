package transactions.change;

import transactions.Transaction;
import businessObjects.Employee;
import businessObjects.PayrollDatabase;

public abstract class ChangeEmployeeTransaction implements Transaction {

	private final int empId;

	public ChangeEmployeeTransaction(int empId) {
		this.empId = empId;
	}

	public void execute() {
		Employee employee = PayrollDatabase.getEmployee(empId);

		if (employee != null) {
			change(employee);

		} else {
			throw new IllegalStateException("No such employee");
		}
	}

	protected abstract void change(Employee employee);
}
