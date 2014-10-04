package transactions.paymentClassifications;

import java.util.Date;

import affiliations.UnionAffiliation;
import businessObjects.Employee;
import businessObjects.PayrollDatabase;
import businessObjects.ServiceCharge;

public class ServiceChargeTransaction {

	private final double charge;
	private final Date date;
	private final int memberId;

	public ServiceChargeTransaction(int memberId, Date date, double charge) {
		this.charge = charge;
		this.date = date;
		this.memberId = memberId;
	}

	public void execute() {
		Employee employee = PayrollDatabase.getUnionMember(memberId);

		try {
			if (employee != null) {
				UnionAffiliation unionAffiliation = (UnionAffiliation) employee.getAffiliation();
				unionAffiliation.addServiceCharge(new ServiceCharge(date, charge));
			} else {
				throw new IllegalStateException("No such union member");
			}

		} catch (ClassCastException classCastException) {
			throw new IllegalStateException(
					"Tries to add service charge to union member without union affiliation");
		}
	}
}
