package businessObjects;

import java.util.Date;

public class ServiceCharge {

	private double amount;
	private Date date;

	public ServiceCharge(Date date, double amount) {
		this.amount = amount;
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public double getAmount() {
		return amount;
	}

}
