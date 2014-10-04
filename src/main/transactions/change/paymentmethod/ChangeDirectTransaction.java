package transactions.change.paymentmethod;

import paymentMethods.DirectMethod;
import paymentMethods.PaymentMethod;

public class ChangeDirectTransaction extends ChangeMethodTransaction {

	public ChangeDirectTransaction(int empId) {
		super(empId);
	}

	@Override
	protected PaymentMethod getPaymentMethod() {
		return new DirectMethod();
	}

}
