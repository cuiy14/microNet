package beans;

import java.sql.Date;
	/**
	 * this model is the super class of battery, windmachine & photovoltaic,
	 * it is also the model of load motor
	 * @author drift
	 *
	 */
public class BaseModel {
	protected int id; // primary key
	protected String serial; // serial name
	protected int normalPower; // normal power, output power
	protected Date date; // the date of the machine put into service

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public int getNormalPower() {
		return normalPower;
	}

	public void setNormalPower(int normalPower) {
		this.normalPower = normalPower;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
