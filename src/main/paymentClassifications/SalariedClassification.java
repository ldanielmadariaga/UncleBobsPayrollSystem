package paymentClassifications;

import businessObjects.Paycheck;

public class SalariedClassification implements PaymentClassification {

	private double itsSalary;

	public SalariedClassification(double salary) {
		this.itsSalary = salary;
	}

	public double getItsSalary() {
		return itsSalary;
	}

	@Override
	public double calculatePay(Paycheck paycheck) {
		return itsSalary;
	}

}
