package businessObjects;

import java.util.Date;

import paymentClassifications.PaymentClassification;
import paymentMethods.PaymentMethod;
import paymentSchedules.PaymentSchedule;
import affiliations.Affiliation;

public class Employee {

	private int empId;
	private String itsName;
	private String itsAddress;
	private PaymentSchedule paymentSchedule;

	private PaymentClassification paymentClassification;
	private PaymentMethod paymentMethod;
	private Affiliation affiliation;

	public Employee(final int empId, final String itsName, final String itsAddress) {
		this.empId = empId;
		this.itsName = itsName;
		this.itsAddress = itsAddress;

	}

	public Affiliation getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(Affiliation affiliation) {
		this.affiliation = affiliation;
	}

	public void setPaymentSchedule(PaymentSchedule paymentSchedule) {
		this.paymentSchedule = paymentSchedule;
	}

	public void setPaymentClassification(PaymentClassification paymentClassification) {
		this.paymentClassification = paymentClassification;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public int getEmpId() {
		return empId;
	}

	public String getItsName() {
		return itsName;
	}

	public void setItsName(String itsName) {
		this.itsName = itsName;
	}

	public String getItsAddress() {
		return itsAddress;
	}

	public void setItsAddress(String itsAddress) {
		this.itsAddress = itsAddress;
	}

	public PaymentSchedule getPaymentSchedule() {
		return paymentSchedule;
	}

	public PaymentClassification getPaymentClassification() {
		return paymentClassification;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public boolean isPayDate(Date payDate) {
		return paymentSchedule.isPayDate(payDate);
	}

	public void payday(Paycheck paycheck) {
		double grossPay = paymentClassification.calculatePay(paycheck);
		double deductions = affiliation.calculateDeductions(paycheck);
		double netPay = grossPay - deductions;
		paycheck.setGrossPay(grossPay);
		paycheck.setDeductions(deductions);
		paycheck.setNetPay(netPay);
		paymentMethod.pay(paycheck);

	}

	public Date getPayPeriodStartDate(Date payDate) {
		return paymentSchedule.getPayPeriodStartDate(payDate);
	}

}
