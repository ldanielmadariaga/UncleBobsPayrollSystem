package paymentClassifications;

import java.util.ArrayList;
import java.util.Date;

import businessObjects.DateUtils;
import businessObjects.Paycheck;
import businessObjects.SalesReceipt;

public class CommissionedClassification implements PaymentClassification {

	private double itsSalary;
	private double itsCommissionRate;
	private ArrayList<SalesReceipt> salesReceipts = new ArrayList<SalesReceipt>();

	public CommissionedClassification(double salary, double commissionRate) {
		this.itsSalary = salary;
		this.itsCommissionRate = commissionRate;
	}

	public double getItsSalary() {
		return itsSalary;
	}

	public double getItsCommissionRate() {
		return itsCommissionRate;
	}

	public void addSalesReceipt(SalesReceipt salesReceipt) {
		salesReceipts.add(salesReceipt);
	}

	public SalesReceipt getSalesReceipt(Date date) {
		SalesReceipt timedSalesReceipt = null;
		for (SalesReceipt salesReceipt : salesReceipts) {
			if (salesReceipt.getDate().equals(date)) {
				timedSalesReceipt = salesReceipt;
			}
		}
		return timedSalesReceipt;
	}

	@Override
	public double calculatePay(Paycheck paycheck) {
		double totalPay = itsSalary;
		for (SalesReceipt salesReceipt : salesReceipts) {
			if (DateUtils.isInPayPeriod(salesReceipt.getDate(), paycheck)) {
				totalPay += calculatePayForSalesReceipt(salesReceipt);
			}
		}
		return totalPay;
	}

	private double calculatePayForSalesReceipt(SalesReceipt salesReceipt) {
		return salesReceipt.getAmount() * (itsCommissionRate / 100);
	}
}
