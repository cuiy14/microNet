package beans;

public class Battery extends BaseModel {
	/**
	 * the private fields include
	 * 
	 * protected int id; // primary key
	 * protected String serial; // serial name
	 * protected int normalPower; // normal power, output power
	 * protected Date date; // the date of the machine put into service
	 * int capacity
	 */
	private int capacity; // the capacity of the battery, *kwh
	public int getCapacity(){
		return capacity;
	}
	public void setCapacity(int capacity){
		this.capacity = capacity;
	}
}
