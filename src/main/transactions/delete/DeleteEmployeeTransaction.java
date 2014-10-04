package transactions.delete;

import businessObjects.PayrollDatabase;

public class DeleteEmployeeTransaction {
	private int itsEmployeeId;

	public DeleteEmployeeTransaction(int employeeId) {
		this.itsEmployeeId = employeeId;
	}

	public void execute(){
		PayrollDatabase.deleteEmployee(itsEmployeeId);
	}
}
