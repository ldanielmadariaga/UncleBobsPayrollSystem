package transactions.change.paymentmethod;

import paymentMethods.PaymentMethod;
import transactions.change.ChangeEmployeeTransaction;
import businessObjects.Employee;

public abstract class ChangeMethodTransaction extends ChangeEmployeeTransaction {

	public ChangeMethodTransaction(int empId) {
		super(empId);
	}

	@Override
	protected void change(Employee employee) {
		employee.setPaymentMethod(getPaymentMethod());
	}

	protected abstract PaymentMethod getPaymentMethod();

}
