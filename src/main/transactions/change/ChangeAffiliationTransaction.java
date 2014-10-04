package transactions.change;

import affiliations.Affiliation;
import businessObjects.Employee;

public abstract class ChangeAffiliationTransaction extends ChangeEmployeeTransaction {

	public ChangeAffiliationTransaction(int empId) {
		super(empId);
	}

	@Override
	protected void change(Employee employee) {
		RecordMembership(employee);
		employee.setAffiliation(getAffiliation());
	}

	protected abstract Affiliation getAffiliation();

	protected abstract void RecordMembership(Employee employee);
}
