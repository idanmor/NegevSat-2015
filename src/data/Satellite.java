package data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import data.Satellite.SatelliteState;

import java.sql.Timestamp;
import java.util.ArrayList;

@DatabaseTable(tableName="Satellite")
public class Satellite {
	
	public enum SatelliteState {
		UNKNOWN("Unknown"), OPERATIONAL("Operational"), SAFE_MODE("Safe Mode");
		private String name;
		private SatelliteState(String state){
			this.name = state;
		}
		@Override
		public String toString(){
			return name;
		}
	}
	 
    public static final String DATE_FIELD_NAME = "creationTimestamp";
    @DatabaseField(id = true) 
    private Timestamp creationTimestamp;
    @DatabaseField
    private SatelliteState satelliteState;
    @DatabaseField
    private Status TempratureStatus;
    @DatabaseField
    private Timestamp TempratureTimestamp;
    @DatabaseField
    private Status EnergyStatus;
    @DatabaseField
    private Timestamp EnergyTimestamp;
    @DatabaseField
    private Status SbandStatus;
    @DatabaseField
    private Timestamp SbandTimestamp;
    @DatabaseField
    private Status PayloadStatus;
    @DatabaseField
    private Timestamp PayloadTimestamp;
    @DatabaseField
    private Status SolarPanelsStatus;
    @DatabaseField
    private Timestamp SolarPanelsTimestamp;
    @DatabaseField
    private Status ThermalStatus;
    @DatabaseField
    private Timestamp ThermalTimestamp;
    DataManager dm;
    
    public Satellite(){}
    
    public Satellite(Status temp, Timestamp tempratureTS, Status energy,  Timestamp energyTS, Status Sband,  
    					Timestamp SbandTS, Status Payload,  Timestamp PayloadTS, Status SolarPanels,  
    					Timestamp SolarPanelsTS, Status Thermal,Timestamp ThermalTS) {
        java.util.Date date= new java.util.Date();
        Timestamp t=new Timestamp(date.getTime());
        this.creationTimestamp=t;
        this.satelliteState=SatelliteState.OPERATIONAL;
        this.TempratureStatus=temp;
        this.TempratureTimestamp=tempratureTS;
        this.EnergyStatus=energy;
        this.EnergyTimestamp=energyTS;
        this.SbandStatus=Sband;
        this.SbandTimestamp=SbandTS;
        this.PayloadStatus=Payload;
        this.PayloadTimestamp=PayloadTS;
        this.SolarPanelsStatus=SolarPanels;
        this.SolarPanelsTimestamp=SolarPanelsTS;
        this.ThermalStatus=Thermal;
        this.ThermalTimestamp=ThermalTS;
        
        dm=DataManager.getInstance();
        dm.setLatestSatData(this);
	}
    
    public Satellite(SatelliteState state, Status temp, Timestamp tempratureTS, Status energy,  
    		Timestamp energyTS, Status Sband,  Timestamp SbandTS, Status Payload,  
    		Timestamp PayloadTS, Status SolarPanels,  Timestamp SolarPanelsTS, Status Thermal,Timestamp ThermalTS) {
        java.util.Date date= new java.util.Date();
        Timestamp t=new Timestamp(date.getTime());
        this.creationTimestamp=t;
        this.satelliteState=state;
        this.TempratureStatus=temp;
        this.TempratureTimestamp=tempratureTS;
        this.EnergyStatus=energy;
        this.EnergyTimestamp=energyTS;
        this.SbandStatus=Sband;
        this.SbandTimestamp=SbandTS;
        this.PayloadStatus=Payload;
        this.PayloadTimestamp=PayloadTS;
        this.SolarPanelsStatus=SolarPanels;
        this.SolarPanelsTimestamp=SolarPanelsTS;
        this.ThermalStatus=Thermal;
        this.ThermalTimestamp=ThermalTS;
        
        dm=DataManager.getInstance();
        dm.setLatestSatData(this);
	}



	public Timestamp getObjectCreationTimestamp(){
    	return this.creationTimestamp;
    }
    public Status getTempratureStatus(){
    	return this.TempratureStatus;
    }   
    public Timestamp getTempratureTS(){
    	return this.TempratureTimestamp;
    }
    public Status getEnergyStatus(){
    	return this.EnergyStatus;
    }
    public Timestamp getEnergyTS(){
    	return this.EnergyTimestamp;
    }
    public Status getSbandStatus(){
    	return this.SbandStatus;
    }
    public Timestamp getSbandTS(){
    	return this.TempratureTimestamp;
    }
    public Status getPayloadStatus(){
    	return this.PayloadStatus;
    }
    public Timestamp getPayloadTS(){
    	return this.TempratureTimestamp;
    }
    public Status getSolarPanelsStatus(){
    	return this.SolarPanelsStatus;
    }
    public Timestamp getSolarPanelsTS(){
    	return this.TempratureTimestamp;
    }
    public Status getThermalStatus(){
    	return this.ThermalStatus;
    }
    public Timestamp getThermalTS(){
    	return this.TempratureTimestamp;
    }
    public ArrayList<Status> getAllStatus(){
    	ArrayList<Status> statList= new ArrayList<Status>();
    	statList.add(getTempratureStatus());
    	statList.add(getEnergyStatus());
    	statList.add(getSbandStatus());
    	statList.add(getPayloadStatus());
    	statList.add(getSolarPanelsStatus());
    	statList.add(getThermalStatus());
    	return statList;
    }

	public SatelliteState getSatelliteState() {
		
		return satelliteState;
	}
}
