package paymentClassifications;

import businessObjects.Paycheck;

public interface PaymentClassification {

	public double calculatePay(Paycheck paycheck);

}
