package transactions.paymentClassifications;

import java.util.Date;

import paymentClassifications.CommissionedClassification;
import businessObjects.Employee;
import businessObjects.PayrollDatabase;
import businessObjects.SalesReceipt;

public class SalesReceiptTransaction {

	private double amount;
	private Date date;
	private int empId;

	public SalesReceiptTransaction(Date date, double amount, int empId) {
		this.amount = amount;
		this.date = date;
		this.empId = empId;
	}

	public void execute() {
		Employee employee = PayrollDatabase.getEmployee(empId);

		try {
			if (employee != null) {
				CommissionedClassification commissionedClassification = (CommissionedClassification) employee
						.getPaymentClassification();
				commissionedClassification.addSalesReceipt(new SalesReceipt(date, amount));
			} else {
				throw new IllegalStateException("No such employee");
			}

		} catch (ClassCastException castException) {
			throw new IllegalStateException("Tried to add sales receipt to non-commissioned employee");

		}
	}

}
