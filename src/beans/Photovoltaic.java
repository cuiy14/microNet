package beans;

public class Photovoltaic extends BaseModel {
	// average efficiency of transforming light into electricity
	/**
	 * the private fields include
	 * 
	 * protected int id; // primary key
	 * protected String serial; // serial name
	 * protected int normalPower; // normal power, output power
	 * protected Date date; // the date of the machine put into service
	 * double efficiency;
	 */
	private double efficiency; 	

	public double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}
}
