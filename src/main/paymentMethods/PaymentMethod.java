package paymentMethods;

import businessObjects.Paycheck;

public interface PaymentMethod {

	void pay(Paycheck paycheck);

}
