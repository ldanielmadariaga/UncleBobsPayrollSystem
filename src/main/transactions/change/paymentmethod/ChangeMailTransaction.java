package transactions.change.paymentmethod;

import paymentMethods.MailMethod;
import paymentMethods.PaymentMethod;

public class ChangeMailTransaction extends ChangeMethodTransaction {

	public ChangeMailTransaction(int empId) {
		super(empId);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PaymentMethod getPaymentMethod() {
		return new MailMethod();
	}

}
