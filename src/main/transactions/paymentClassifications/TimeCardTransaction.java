package transactions.paymentClassifications;

import java.util.Date;

import paymentClassifications.HourlyClassification;
import transactions.Transaction;
import businessObjects.Employee;
import businessObjects.PayrollDatabase;
import businessObjects.TimeCard;

public class TimeCardTransaction implements Transaction {

	private final Date date;
	private final double hours;
	private final int empId;

	public TimeCardTransaction(Date date, double hours, int empId) {
		this.date = date;
		this.hours = hours;
		this.empId = empId;
	}

	public void execute() {

		Employee employee = PayrollDatabase.getEmployee(empId);

		try {
			if (employee != null) {
				HourlyClassification hourlyClassification = (HourlyClassification) employee
						.getPaymentClassification();
				hourlyClassification.addTimeCard(new TimeCard(date, hours));
			} else {
				throw new IllegalStateException("No such employee");
			}

		} catch (ClassCastException castException) {
			throw new IllegalStateException("Tried to add timecard to non-hourly employee");

		}
	}
}
