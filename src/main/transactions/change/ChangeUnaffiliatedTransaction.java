package transactions.change;

import affiliations.Affiliation;
import affiliations.NoAffiliation;
import affiliations.UnionAffiliation;
import businessObjects.Employee;
import businessObjects.PayrollDatabase;

public class ChangeUnaffiliatedTransaction extends ChangeAffiliationTransaction {

	public ChangeUnaffiliatedTransaction(int empId) {
		super(empId);
	}

	@Override
	protected Affiliation getAffiliation() {
		return new NoAffiliation();
	}

	@Override
	protected void RecordMembership(Employee employee) {
		Affiliation affiliation = employee.getAffiliation();
		if (affiliation instanceof UnionAffiliation) {
			UnionAffiliation unionAffiliation = (UnionAffiliation) affiliation;
			int memberId = unionAffiliation.getMemberId();
			PayrollDatabase.removeUnionMember(memberId);
		}

	}

}
