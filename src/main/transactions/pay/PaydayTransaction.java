package transactions.pay;

import java.util.Date;
import java.util.Hashtable;
import java.util.Set;

import transactions.Transaction;
import businessObjects.Employee;
import businessObjects.Paycheck;
import businessObjects.PayrollDatabase;

public class PaydayTransaction implements Transaction {

	private Date payDate;
	private Hashtable<Integer, Paycheck> paychecks = new Hashtable<Integer, Paycheck>();

	public PaydayTransaction(Date payDate) {
		this.payDate = payDate;
	}

	@Override
	public void execute() {
		Set<Integer> empIds = PayrollDatabase.getAllEmployeeIds();
		for (Integer empId : empIds) {
			Employee employee = PayrollDatabase.getEmployee(empId);
			if (employee.isPayDate(payDate)) {
				Date startDate = employee.getPayPeriodStartDate(payDate);
				Paycheck paycheck = new Paycheck(startDate, payDate);
				paychecks.put(empId, paycheck);
				employee.payday(paycheck);
			}

		}
	}

	public Paycheck getPaycheck(int empId) {
		return paychecks.get(empId);
	}
}
