package data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.util.Pair;

@DatabaseTable(tableName="Energy")
public class Energy extends Component {
	public static final String DATE_FIELD_NAME = "sampleTimestamp";
    @DatabaseField(id = true ,columnName = DATE_FIELD_NAME)
    private Timestamp sampleTimestamp;
    @DatabaseField
    private float batt1Voltage;
    @DatabaseField
    private float batt2Voltage;
    @DatabaseField
    private float batt3Voltage;
    @DatabaseField
    private float batt1Current;
    @DatabaseField
    private float batt2Current;
    @DatabaseField
    private float batt3Current;
    @DatabaseField
    private Timestamp timeReceivedTimestamp;
    
    public Energy(){}
    
    
    public Energy(Timestamp ts,float _batt1Voltage,float _batt2Voltage,float _batt3Voltage, float _batt1Current,float _batt2Current,float _batt3Current){
        java.util.Date date1= new java.util.Date();
        this.sampleTimestamp=ts;
        this.timeReceivedTimestamp= new Timestamp(date1.getTime());
        this.batt1Voltage=_batt1Voltage;
        this.batt2Voltage=_batt2Voltage;
        this.batt3Voltage=_batt3Voltage;
        this.batt1Current=_batt1Current;
        this.batt2Current=_batt2Current;
        this.batt3Current=_batt3Current;
    }
	

    public Timestamp getSampleTimestamp(){
        return sampleTimestamp;
    }
    public Timestamp getReceivedTimestamp(){
        return timeReceivedTimestamp;
    }
    public float getBatt1Voltage(){
        return batt1Voltage;
    }
    public float getBatt2Voltage(){
        return batt2Voltage;
    }
    public float getBatt3Voltage(){
        return batt3Voltage;
    }
    public float getBatt1Current(){
        return batt1Current;
    }
    public float getBatt2Current(){
        return batt2Current;
    }
    public float getBatt3Current(){
        return batt3Current;
    }
    
	public 	Map<String, Float> getSensorsValues() {
		if(sensorsValues == null){
			sensorsValues= new HashMap<String, Float>();
			sensorsValues.put("batt1Voltage",new Float(this.getBatt1Voltage()));
			sensorsValues.put("batt2Voltage",new Float(this.getBatt2Voltage()));
			sensorsValues.put("batt3Voltage",new Float(this.getBatt3Voltage()));
			sensorsValues.put("batt1Current",new Float(this.getBatt1Current()));
			sensorsValues.put("batt2Current",new Float(this.getBatt2Current()));
			sensorsValues.put("batt3Current",new Float(this.getBatt3Current()));
		}
		return sensorsValues;
	}
}
