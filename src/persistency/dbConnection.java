package persistency;



import data.*;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.jdbc.*;
import com.j256.ormlite.support.*;
import com.j256.ormlite.table.TableUtils;

import java.util.List;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.sql.SQLException;
import java.sql.Timestamp;

public class dbConnection {
    private static dbConnection dbcon;
    static ConnectionSource connectionSource;
    static Dao<Energy, Timestamp> energyDao;
    static Dao<Temprature, Timestamp> tempratureDao;
    static Dao<Satellite, Timestamp> satelliteDao;
    static Dao<Mission, Timestamp> missionDao;
    



    private dbConnection(){
        try{
        	//Files.deleteIfExists(FileSystems.getDefault().getPath("C://sqlite", "negevSatDB.db"));
            connectionSource =new JdbcConnectionSource("jdbc:sqlite:c:\\sqlite\\negevSatDB.db");
            energyDao =DaoManager.createDao(connectionSource, Energy.class);
            tempratureDao =DaoManager.createDao(connectionSource, Temprature.class);
            satelliteDao =DaoManager.createDao(connectionSource, Satellite.class);
            missionDao = DaoManager.createDao(connectionSource, Mission.class);
        }
        catch ( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               System.exit(0);
        }
    }
    
    public static dbConnection getdbCon(){ //singelton
        if(dbcon==null)
            dbcon=new dbConnection();
        return dbcon;
    }
    
    public void createTables(){
    	try{
    	TableUtils.createTableIfNotExists(connectionSource, Energy.class);
    	TableUtils.createTableIfNotExists(connectionSource, Temprature.class);
    	TableUtils.createTableIfNotExists(connectionSource, Satellite.class);
    	TableUtils.createTableIfNotExists(connectionSource, Mission.class);
    	}
    	catch ( SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    
    public void clearTables(){
    	try{
    	TableUtils.clearTable(connectionSource, Energy.class);
    	TableUtils.clearTable(connectionSource, Temprature.class);
    	TableUtils.clearTable(connectionSource, Satellite.class);
    	TableUtils.clearTable(connectionSource, Mission.class);
    	}
    	catch ( SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    
    public void dropTables(){
    	try{
    	TableUtils.dropTable(connectionSource, Energy.class, true);
    	TableUtils.dropTable(connectionSource, Temprature.class, true);
    	TableUtils.dropTable(connectionSource, Satellite.class, true);
    	TableUtils.dropTable(connectionSource, Mission.class, true);
    	}
    	catch ( SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    public List<Mission> getMissions(Timestamp startDate, Timestamp endDate) {
    	List<Mission> mission=null;
    	try{
    		mission = missionDao.queryBuilder().where().between(Mission.DATE_FIELD_NAME, startDate, endDate).query();
    	}
    	catch ( SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    	return mission;
	}
 
    public Mission getMission(Timestamp creationTimestamp){
    	List<Mission> missionlst=null;
    	Mission mission=null;
    	try{
    		missionlst=missionDao.queryBuilder().where().eq(Mission.DATE_FIELD_NAME, creationTimestamp).query();
    		if (!missionlst.isEmpty()){
    			mission=missionlst.get(0);
    		}
    		}
    	catch ( SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    	return mission;
    }
    
    public Satellite getLatestSatelliteData(){
    	Satellite satellite=null;
    	try{
    	long max = satelliteDao.queryRawValue("select max(creationTimestamp) from Satellite");
    		// now perform a second query to get the max row
    	satellite = satelliteDao.queryBuilder().where().eq("creationTimestamp", max).queryForFirst();
    	}
    	catch ( SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    	
    	return satellite;
    }
    
    public List<Satellite> getSatelliteData(Timestamp startDate, Timestamp endDate){
    	List<Satellite> data=null;
    	try{
    		data=satelliteDao.queryBuilder().where().between(Satellite.DATE_FIELD_NAME, startDate, endDate).query();
    		}
    	catch ( SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    	return data;
    }
    
    public List<Temprature> getTemprature(Timestamp startDate, Timestamp endDate){
        
        List<Temprature> data=null;
        try{
            data = tempratureDao.queryBuilder().where().between(Temprature.DATE_FIELD_NAME, startDate, endDate).query();
        }
        catch ( SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
        return data;
    }
    public List<Energy> getEnergy(Timestamp startDate, Timestamp endDate){
        
        List<Energy> data=null;
        try{
           data = energyDao.queryBuilder().where().between(Energy.DATE_FIELD_NAME, startDate, endDate).query();          
        }
        catch ( SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
        return data;
    }

 

    public Mission insertMission(Timestamp _missionExecutionTS, Command _command, int _priority){
        Mission mission=new Mission(_missionExecutionTS,_command,_priority);
        try{
            missionDao.create(mission);
            return mission;
        }
        catch ( SQLException e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
               return null;
        }
    }
    
    
    public Satellite insertSatellite(Status temp, Timestamp tempTS, Status energy, Timestamp energyTS, 
    									Status Sband, Timestamp sbandTS, Status Payload,Timestamp payloadTS, 
    									Status SolarPanels, Timestamp solarPanelsTS, Status Thermal, Timestamp ThermalTS){
        Satellite sat=new Satellite(temp,tempTS,energy,energyTS,Sband,sbandTS,Payload,payloadTS,SolarPanels,solarPanelsTS,Thermal,ThermalTS);
        try{
            satelliteDao.create(sat);
            return sat;
        }
        catch ( SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }
    
    public Satellite insertSatellite(Satellite.SatelliteState state, Status temp, Timestamp tempTS, Status energy, Timestamp energyTS, 
    										Status Sband, Timestamp sbandTS, Status Payload,Timestamp payloadTS, Status SolarPanels, 
    										Timestamp solarPanelsTS, Status Thermal, Timestamp ThermalTS){
        Satellite sat=new Satellite(state,temp,tempTS,energy,energyTS,Sband,sbandTS,Payload,payloadTS,
        							SolarPanels,solarPanelsTS,Thermal,ThermalTS);
        try{
            satelliteDao.create(sat);
            return sat;
        }
        catch ( SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }
    
    public Temprature insertTemprature(float sensor1,float sensor2, float sensor3, Timestamp timeStamp){
        Temprature tmp=new Temprature(timeStamp,sensor1,sensor2,sensor3);
        try{
//        	List<Temprature> dataFromTable=tempratureDao.queryBuilder().where().eq(Temprature.DATE_FIELD_NAME, timeStamp).query();
//        	if (dataFromTable!=null){
//        		System.err.println("TimeStamp already exists");
//        		return null;
//        	}

            tempratureDao.create(tmp);
            return tmp;
        }
        catch ( SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }
    
    public Energy insertEnergy(float batt1V,float batt2V,float batt3V, float batt1C,float batt2C,float batt3C, Timestamp timeStamp){
        try{
//        	List<Energy> dataFromTable=energyDao.queryBuilder().where().eq(Energy.DATE_FIELD_NAME, timeStamp).query();
//        	if(dataFromTable!=null){
//        		System.err.println("TimeStamp already exists");
//        		return null;
//        	}
        	Energy eng=new Energy(timeStamp,batt1V,batt2V,batt3V,batt1C,batt2C,batt3C);  
            energyDao.create(eng);
            return eng;
        }
        catch ( SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }
   

    
    public void deleteComponent(String component,Timestamp timestamp) {
    	if (component==null) return;
        try{
            if(component.equals("Energy"))
                energyDao.deleteById(timestamp);
             else if(component.equals("Temprature"))
                      tempratureDao.deleteById(timestamp);
            }
            catch ( Exception e ) {
               System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            } 
            
    }
    
    public void deleteCompletedMission(Timestamp creationTimestamp){
    	try{
    		missionDao.deleteById(creationTimestamp);
    	}
    	catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         }
    }

    public void updateMission(Mission m){
    	try{
    		missionDao.update(m);
    	}
    	catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    	
    }

	
}
