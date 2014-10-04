package affiliations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import businessObjects.DateUtils;
import businessObjects.Paycheck;
import businessObjects.ServiceCharge;

public class UnionAffiliation implements Affiliation {

	private double dues;
	private int memberId;
	private ArrayList<ServiceCharge> serviceCharges = new ArrayList<ServiceCharge>();

	public UnionAffiliation() {
	}

	public UnionAffiliation(int memberId, double dues) {
		this.dues = dues;
		this.memberId = memberId;
	}

	public double getDues() {
		return dues;
	}

	public int getMemberId() {
		return memberId;
	}

	public void addServiceCharge(ServiceCharge serviceCharge) {
		this.serviceCharges.add(serviceCharge);
	}

	public ServiceCharge getServiceCharge(Date expectedDate) {
		ServiceCharge datedServiceCharge = null;
		for (ServiceCharge serviceCharge : serviceCharges) {
			if (serviceCharge.getDate() == expectedDate) {
				datedServiceCharge = serviceCharge;
			}
		}
		return datedServiceCharge;
	}

	@Override
	public double calculateDeductions(Paycheck paycheck) {
		double totalDues = 0;
		int fridays = numberOfFridaysInPayPeriod(paycheck.getPayPeriodStartDate(), paycheck.getPayPeriodEndDate());
		totalDues = dues * fridays;
		totalDues += calculateServiceChargesDeductions(totalDues, paycheck);

		return totalDues;
	}

	private double calculateServiceChargesDeductions(double totalDues, Paycheck paycheck) {
		double serviceDues = 0;
		for (ServiceCharge serviceCharge : serviceCharges) {
			if (DateUtils.isInPayPeriod(serviceCharge.getDate(), paycheck)) {
				serviceDues += serviceCharge.getAmount();
			}

		}
		return serviceDues;
	}

	private int numberOfFridaysInPayPeriod(Date payPeriodStartDate, Date payPeriodEndDate) {
		Calendar endPayPeriodCalendar = Calendar.getInstance();
		endPayPeriodCalendar.setTime(payPeriodEndDate);
		endPayPeriodCalendar.add(Calendar.DATE, +1);
		Date afterEndDate = endPayPeriodCalendar.getTime();

		Calendar coutingDaysCalendar = Calendar.getInstance();

		int fridays = 0;
		for (coutingDaysCalendar.setTime(payPeriodStartDate); coutingDaysCalendar.getTime().before(afterEndDate); coutingDaysCalendar
				.add(Calendar.DATE, +1)) {
			if (coutingDaysCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
				fridays++;
			}
		}
		return fridays;
	}
}
