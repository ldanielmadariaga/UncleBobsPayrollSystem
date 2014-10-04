package transactions.change;

import affiliations.Affiliation;
import affiliations.UnionAffiliation;
import businessObjects.Employee;
import businessObjects.PayrollDatabase;

public class ChangeMemberTransaction extends ChangeAffiliationTransaction {

	private double dues;
	private int memberId;

	public ChangeMemberTransaction(int empId, int memberId, double dues) {
		super(empId);
		this.dues = dues;
		this.memberId = memberId;
	}

	@Override
	protected Affiliation getAffiliation() {
		return new UnionAffiliation(memberId, dues);
	}

	@Override
	protected void RecordMembership(Employee employee) {
		PayrollDatabase.addUnionMember(memberId, employee);
	}

}
