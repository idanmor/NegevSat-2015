package data;

public enum Command implements CommandWithDescription {

	MOVE_TO_SAFE(1),
	MOVE_TO_STANDBY(2),
	MOVE_TO_OP(3),
	FORMAT_ENERGY(4),
	FORMAT_TEMP(5),
	FORMAT_STATIC(6),
	FORMAT_MIXED(7),
	SBAND_ON(8,"Set Sband on"),
	SBAND_STANDBY(9,"Set Sband to Standby"),
	PAYLOAD_ON(10,"Set Payload on"),
	PAYLOAD_STANDBY(11,"Set Payload to Standbye"),
	THERMAL_CRTL_ON(12,"Set Thermal control on"),
	THERMAL_CRTL_STANDBY(13,"Set Thermal control off"),
	MOVE_TO_PASS(14),
	MOVE_TO_EXP(18);
	
	private final int value;
	private final String description;
	private Command(final int newValue){
		this.value = newValue;
		description = null;
	}
	private Command(final int newValue, String description) {
	        value = newValue;
	        this.description = description;
	}

	public int getValue() { 
		return value; 
	}

	@Override
	public String getDescription() {
		return description;
	}
	    
}
