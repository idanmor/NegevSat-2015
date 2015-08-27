package data;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TooManyListenersException;

import com.j256.ormlite.field.DatabaseField;

import Utils.GuiManager;
import communication.CommunicationManager;
import data.Satellite.SatelliteState;
import javafx.util.Pair;
import logger.Loggers;
import persistency.dbConnection;

public class DataManager {
	private static final String comPort = "COM2";
	private dbConnection db;
	private CommunicationManager comm;
	
	private static DataManager instance = null;
	
	private Satellite latestSatData;
	private boolean testMode;
	
	private DataManager() {
		db = dbConnection.getdbCon();
		comm = CommunicationManager.getInstance();
		db.createTables();
		latestSatData=db.getLatestSatelliteData();
		testMode = false;
			try {
				comm.connect(comPort);
			} catch (NoSuchPortException | PortInUseException
					| UnsupportedCommOperationException | IOException | UnsatisfiedLinkError
					| TooManyListenersException e) {
				System.out.println("ERROR: Could not connect to " + comPort + ". Running in offline mode.");
				System.out.println("Error message: " + e.getMessage());
			}
	}
	
	public static DataManager getInstance() {
		if (instance == null)
			instance = new DataManager();
		return instance;
	}
	
	public void setTestMode(boolean mode){
		testMode = mode;
	}
	public void setLatestSatData(Satellite sat){
		this.latestSatData=sat;
		if (testMode==false){
			GuiManager.getInstance().refreshSatelliteController(sat);
		}
	}
	
	public List<Temprature> getTemprature(Timestamp startDate, Timestamp endDate){
		return db.getTemprature(startDate, endDate);
	}
	
	public List<Energy> getEnergy(Timestamp startDate, Timestamp endDate){
		return db.getEnergy(startDate, endDate);
	}
	
	public List<Satellite> getSatellite(Timestamp startDate, Timestamp endDate){
		return db.getSatelliteData(startDate, endDate);
	}
	
	public Mission getMission(Timestamp creationTimestamp){
		return db.getMission(creationTimestamp);
	}
	
	public List<Mission> getMissions(Timestamp startDate, Timestamp endDate){
		return db.getMissions(startDate, endDate);
	}


	public Satellite getLatestSatData(){
		return(this.latestSatData);
	}
	
	public Map<String,Float> getReadingsPerSensor(Component component){
		
		return component.getSensorsValues();
	}

	
	 public Mission insertMission(Timestamp _missionExecutionTS, Command _command, int _priority){
		 return db.insertMission(_missionExecutionTS, _command, _priority);
	 }
	 
	 public Satellite insertSatellite(Status temp, Timestamp tempTS, Status energy, Timestamp energyTS, 
			 							Status Sband, Timestamp SbandTS, Status Payload, Timestamp PayloadTS, 
			 							Status SolarPanels, Timestamp SolarPanelsTS, Status Thermal, Timestamp ThermalTS){
		 return db.insertSatellite(temp, tempTS, energy, energyTS, Sband, SbandTS, Payload, PayloadTS, SolarPanels, 
				 					SolarPanelsTS, Thermal, ThermalTS);
	 }
	 
	 public Satellite insertSatellite(Satellite.SatelliteState state, Status temp, Timestamp tempTS, Status energy, 
			 					Timestamp energyTS, Status Sband, Timestamp SbandTS, Status Payload, Timestamp PayloadTS, 
			 					Status SolarPanels, Timestamp SolarPanelsTS, Status Thermal, Timestamp ThermalTS){
		 return db.insertSatellite(state, temp, tempTS, energy, energyTS, Sband, SbandTS, Payload, PayloadTS, 
				 				SolarPanels, SolarPanelsTS, Thermal, ThermalTS);
	 }
	 
	 public Temprature insertTemprature(float sensor1,float sensor2, float sensor3, Timestamp ts){
		 return db.insertTemprature(sensor1, sensor2, sensor3, ts);
	 }
	 
	 public Energy insertEnergy(float batt1V,float batt2V,float batt3V, float batt1C,float batt2C,float batt3C, Timestamp ts){
		 return db.insertEnergy(batt1V, batt2V, batt3V, batt1C, batt2C, batt3C, ts);
	 }
	 
	 public void deleteComponent(String component,Timestamp timeStamp) {
		 db.deleteComponent(component, timeStamp);
	 }
	    
	 public void deleteCompletedMission(Timestamp creationTimestamp){
		 db.deleteCompletedMission(creationTimestamp);
	 }
	 
	 public void setMission(Mission m, Timestamp missionExecutionTS, Command command, int priority){
		 if (m==null){
			 System.err.println("no mission object");
			 return;
		 }
		 if (missionExecutionTS!=null)
			 m.setMissionExecutionTS(missionExecutionTS);
		 if (command!=null)
			 m.setCommand(command);
		 if (priority!=0)
			 m.setPriority(priority);
		 
		 db.updateMission(m);
	 }
	 
	 public void setMissionSentTS(Mission m, Timestamp sentTime){
		 if (m==null){
			 System.err.println("no mission object");
			 return;
		 }
		 m.setSentTime(sentTime);
		 db.updateMission(m);
		 
	 }
	 
	 public SatelliteState getLastSateliteState(){
		 if(latestSatData == null){
			 return SatelliteState.UNKNOWN;
		 }
		 return latestSatData.getSatelliteState();
	 }
}
