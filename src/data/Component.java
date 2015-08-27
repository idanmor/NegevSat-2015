package data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javafx.util.Pair;

public abstract class Component {
	 protected Map<String, Float> sensorsValues = null;
	
	public abstract Timestamp getSampleTimestamp();
	public abstract Timestamp getReceivedTimestamp();
	public abstract Map<String, Float> getSensorsValues();
	
	public Set<String> getSetOfSensorsNames(){
		return getSensorsValues().keySet();
	}
	public Float getSensorValue(String sensor){
		return getSensorsValues().get(sensor);
	}
	
}
