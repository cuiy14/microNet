package beans;

public class WindMachine extends BaseModel {
	/**
	 * the private fields include
	 * 
	 * protected int id; // primary key
	 * protected String serial; // serial name
	 * protected int normalPower; // normal power, output power
	 * protected Date date; // the date of the machine put into service
	 * private int lowWindScale
	 * private int highWindScale
	 * 
	 */
	private int lowWindScale; // the lowest wind scale to generate electricity
	private int highWindScale; // the highest ***

	public int getLowWindScale() {
		return lowWindScale;
	}

	public void setLowWindScale(int lowWindScale) {
		this.lowWindScale = lowWindScale;
	}

	public int getHighWindScale() {
		return highWindScale;
	}

	public void setHighWindScale(int highWindScale) {
		this.highWindScale = highWindScale;
	}

	public static void main(String args[]) {
		WindMachine machine = new WindMachine();
		machine.setLowWindScale(13);
		System.out.println("low " + machine.getLowWindScale());
	}

}
