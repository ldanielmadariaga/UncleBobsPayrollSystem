package transactions.change.paymentmethod;

import paymentMethods.HoldMethod;
import paymentMethods.PaymentMethod;

public class ChangeHoldTransaction extends ChangeMethodTransaction {

	public ChangeHoldTransaction(int empId) {
		super(empId);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PaymentMethod getPaymentMethod() {
		return new HoldMethod();
	}

}
