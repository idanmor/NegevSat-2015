package data;

public enum Status {
	ON(1),
	MALFUNCTION(2),
	STANDBY(3), 
	NON_OPERATIONAL(4),
	UNKNOWN(5);

	private final int value;
	
	private Status(final int newValue) {
	        value = newValue;
	}

	public int getValue() { 
		return value; 
	}
	
	public String toString() {
		switch(this) {
		case ON:
			return "ON";
		case MALFUNCTION:
			return "MALFUNCTION";
		case STANDBY:
			return "STANDBY";
		case NON_OPERATIONAL:
			return "NON_OPERATIONAL";
		case UNKNOWN:
			return "UNKNOWN";
		default:
			return null;
		}
	}

}
