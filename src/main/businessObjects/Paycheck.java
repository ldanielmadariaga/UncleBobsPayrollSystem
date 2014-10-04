package businessObjects;

import java.util.Date;

public class Paycheck {

	private double deductions;
	private double grossPay;
	private Date payPeriodStartDate;
	private Date payPeriodEndDate;
	private double netPay;

	public Paycheck(Date payPeriodStartDate, Date payPeriodEndDate) {
		this.payPeriodStartDate = payPeriodStartDate;
		this.payPeriodEndDate = payPeriodEndDate;
	}

	public double getDeductions() {
		return deductions;
	}

	public void setDeductions(double deductions) {
		this.deductions = deductions;
	}

	public double getGrossPay() {
		return grossPay;
	}

	public void setGrossPay(double grossPay) {
		this.grossPay = grossPay;
	}

	public Date getPayPeriodStartDate() {
		return payPeriodStartDate;
	}

	public Date getPayPeriodEndDate() {
		return payPeriodEndDate;
	}

	public double getNetPay() {
		return netPay;
	}

	public void setNetPay(double netPay) {
		this.netPay = netPay;
	}

	public String getField(String string) {
		return "Hold";
	}

}
