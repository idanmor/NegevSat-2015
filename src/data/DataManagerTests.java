package data;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import javafx.util.Pair;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import data.Satellite.SatelliteState;
import persistency.dbConnection;

public class DataManagerTests {
	private DataManager dm;
	private dbConnection db;
	private final long dayinMS = 86400000;
	private final long hourInMS = 3600000;
	private final long monthInMS = 26280000;
	long now;
	Timestamp TSNow;
	Timestamp TSdayFromNow;
	Timestamp TSdayEarlier;
	Float one, two, three;
	@Before
	public void initObjects(){
		dm=DataManager.getInstance();
		db=dbConnection.getdbCon();
		now= System.currentTimeMillis();
		TSNow = new Timestamp(now);
		TSdayFromNow=new Timestamp(now+dayinMS);
		TSdayEarlier= new Timestamp(now-dayinMS);
		one = new Float(1);
		two = new Float(2);
		three = new Float(3);
		dm.setTestMode(true);
		db.clearTables();
	}

	
	//////insert//////
	@Test
	public void testInsertMission() {
    	Mission m = dm.insertMission(TSdayFromNow, Command.FORMAT_STATIC, 1);
		assertArrayEquals(new Object[]{TSdayFromNow, Command.FORMAT_STATIC, 1},new Object[]{m.getExecutionTime(),m.getCommand(),m.getPriority()});
	}
	@Test
	public void testInsertTemprature() {
		Temprature t = dm.insertTemprature(1, 2, 3, TSNow);
		assertArrayEquals(new Object[]{one, two, three, TSNow},new Object[]{t.getSensor1(),t.getSensor2(),t.getSensor3(), t.getSampleTimestamp()});
	}
	@Test
	public void testInsertEnergy() {
		Energy e = dm.insertEnergy(one, two, three, one, two, three, TSNow);
		assertArrayEquals(new Object[]{one, two, three, one, two, three, TSNow},new Object[]{e.getBatt1Voltage(),e.getBatt2Voltage(),e.getBatt3Voltage(),
				e.getBatt1Current(), e.getBatt2Current(), e.getBatt3Current(), e.getSampleTimestamp()});
		
	}
	@Test
	public void testInsertSatellite() {
		Satellite s = dm.insertSatellite(Status.ON, TSNow, Status.ON, TSNow, Status.ON, TSNow, Status.ON, TSNow, Status.ON, TSNow, Status.ON, TSNow);
		assertArrayEquals(new Object[]{Status.ON, TSNow, Status.ON, TSNow, Status.ON, TSNow, Status.ON, TSNow, Status.ON, TSNow, Status.ON, TSNow}, 
				new Object[]{s.getTempratureStatus(), s.getTempratureTS(), s.getEnergyStatus(), s.getEnergyTS(), s.getSbandStatus(), s.getSbandTS(), 
				s.getPayloadStatus(), s.getPayloadTS(), s.getSolarPanelsStatus(), s.getSolarPanelsTS(), s.getThermalStatus(),s.getThermalTS()});
	}
	@Test
	public void testInsertSatelliteWithState() {
		Satellite s = dm.insertSatellite(SatelliteState.OPERATIONAL, Status.ON, TSNow, Status.ON, TSNow, Status.ON, TSNow, Status.ON, TSNow, Status.ON, TSNow, Status.ON, TSNow);
		assertArrayEquals(new Object[]{Status.ON, TSNow, Status.ON, TSNow, Status.ON, TSNow, Status.ON, TSNow, Status.ON, TSNow, Status.ON, TSNow}, 
				new Object[]{s.getTempratureStatus(), s.getTempratureTS(), s.getEnergyStatus(), s.getEnergyTS(), s.getSbandStatus(), s.getSbandTS(), 
				s.getPayloadStatus(), s.getPayloadTS(), s.getSolarPanelsStatus(), s.getSolarPanelsTS(), s.getThermalStatus(),s.getThermalTS()});
	}
	//////get//////
	@Test
	public void testGetTempratureValid() {
		Temprature t = dm.insertTemprature(1, 2, 3, TSdayEarlier);
		assertEquals(1, dm.getTemprature(new Timestamp(now-2*dayinMS), TSdayEarlier).size());
	}
	@Test
	public void testGetTempratureInvalid() {
		Temprature t = dm.insertTemprature(1, 2, 3, TSNow);
		assertNotEquals(1, dm.getTemprature(TSNow, TSdayEarlier).size());
	}
	@Test
	public void testGetEnergyValid() {
		Energy e = dm.insertEnergy(one, two, three, one, two, three, TSdayEarlier);
		assertEquals(1, dm.getEnergy(new Timestamp(now-2*dayinMS), TSdayEarlier).size());
	}
	@Test
	public void testGetEnergyInvalid() {
		Energy e = dm.insertEnergy(one, two, three, one, two, three, TSNow);
		assertNotEquals(1,dm.getEnergy(TSNow, TSdayEarlier).size());
	}
	@Test
	public void testGetSatelliteValid() {
		Satellite s = dm.insertSatellite(Status.ON, TSdayEarlier, Status.ON, TSdayEarlier, Status.ON, TSdayEarlier, Status.ON, TSdayEarlier, Status.ON, TSdayEarlier, Status.ON, TSdayEarlier);
		assertEquals(1, dm.getSatellite(TSNow, new Timestamp(System.currentTimeMillis())).size());

	}
	@Test
	public void testGetSatelliteInvalid() {
		Satellite s = dm.insertSatellite(Status.ON, TSdayEarlier, Status.ON, TSdayEarlier, Status.ON, TSdayEarlier, Status.ON, TSdayEarlier, Status.ON, TSdayEarlier, Status.ON, TSdayEarlier);
		assertNotEquals(1, dm.getSatellite(TSNow, TSdayEarlier).size());

	}
	@Test
	public void testGetMissionValid() {
		Mission m = dm.insertMission(TSdayFromNow, Command.FORMAT_STATIC, 1);
		Timestamp t = m.getCreationTimestamp();
		assertEquals(t, dm.getMission(t).getCreationTimestamp());
	}
	@Test
	public void testGetMissionInvalid() {
		assertNull(dm.getMission(TSNow));
	}
	@Test
	public void testGetMissionsExist() {
    	Mission m = dm.insertMission(TSdayFromNow, Command.FORMAT_STATIC, 1);
    	assertEquals(1, dm.getMissions(TSdayEarlier, new Timestamp( System.currentTimeMillis())).size());
	}
	@Test
	public void testGetMissionsNotExist() {
    	Mission m = dm.insertMission(TSdayFromNow, Command.FORMAT_STATIC, 1);
    	assertNotEquals(1, dm.getMissions(new Timestamp( System.currentTimeMillis()), TSNow).size());
	
	}
	@Test
	public void testGetLatestSatelliteData() {
		Satellite s = dm.insertSatellite(Status.ON, TSdayEarlier, Status.ON, TSdayEarlier, Status.ON, TSdayEarlier, Status.ON, TSdayEarlier, Status.ON, TSdayEarlier, Status.ON, TSdayEarlier);
		assertEquals(s.getObjectCreationTimestamp(), dm.getLatestSatData().getObjectCreationTimestamp());
	}
	@Test
	public void testGetReadingsPerSensor() {
		Energy e = dm.insertEnergy(one, two, three, one, two, three, TSdayEarlier);
		Map<String,Float> map = dm.getReadingsPerSensor(e);
		assertEquals(one,map.get("batt1Voltage"));
	}
	//////set//////
	@Test
	public void testSetMissionAll() {
    	Mission m = dm.insertMission(TSdayFromNow, Command.FORMAT_STATIC, 1);
    	dm.setMission(m, TSNow, Command.FORMAT_MIXED, 2);
    	assertArrayEquals(new Object[]{TSNow, Command.FORMAT_MIXED, 2}, new Object[]{m.getExecutionTime(), m.getCommand(), m.getPriority()});
	}
	@Test
	public void testSetMissionInvalid() {
		Mission m = dm.insertMission(TSdayFromNow, Command.FORMAT_STATIC, 1);
    	dm.setMission(null, TSNow, Command.FORMAT_MIXED, 2);
    	assertEquals(1, m.getPriority());
	}
	@Test
	public void testSetMissionTime() {
		Mission m = dm.insertMission(TSdayFromNow, Command.FORMAT_STATIC, 1);
    	dm.setMission(m, TSNow, null, 0);
    	assertEquals(TSNow, m.getExecutionTime());
	}
	@Test
	public void testSetMissionCommand() {
		Mission m = dm.insertMission(TSdayFromNow, Command.FORMAT_STATIC, 1);
    	dm.setMission(m, null, Command.FORMAT_MIXED, 0);
    	assertEquals(Command.FORMAT_MIXED, m.getCommand());
	}
	@Test
	public void testSetMissionPriority() {
		Mission m = dm.insertMission(TSdayFromNow, Command.FORMAT_STATIC, 1);
    	dm.setMission(m, null, null, 2);
    	assertEquals(2, m.getPriority());
	}
	@Test
	public void testSetMissionSentTSValid() {
		Mission m = dm.insertMission(TSdayFromNow, Command.FORMAT_STATIC, 1);
		dm.setMissionSentTS(m, TSNow);
		assertEquals(TSNow, m.getSentTime());
	}
	@Test
	public void testSetMissionSentTSInvalid() {
		Mission m = dm.insertMission(TSdayFromNow, Command.FORMAT_STATIC, 1);
		dm.setMissionSentTS(null, TSNow);
		assertNull( m.getSentTime());
	}
	//////delete//////
	@Test
	public void testDeleteComponentNull() {
		int numberOfEng=dm.getEnergy(TSdayEarlier, TSNow).size();
		int numberOfTmps=dm.getTemprature(TSdayEarlier, TSNow).size();
		dm.deleteComponent(null, TSNow);
		assertArrayEquals(new int[]{numberOfEng, numberOfTmps}, new int[]{dm.getEnergy(TSdayEarlier, TSNow).size(), dm.getTemprature(TSdayEarlier, TSNow).size()});
	}
	@Test
	public void testDeleteComponentWrongString() {
		int numberOfEng=dm.getEnergy(TSdayEarlier, TSNow).size();
		int numberOfTmps=dm.getTemprature(TSdayEarlier, TSNow).size();
		dm.deleteComponent("wrongString", TSNow);
		assertArrayEquals(new int[]{numberOfEng, numberOfTmps}, new int[]{dm.getEnergy(TSdayEarlier, TSNow).size(), dm.getTemprature(TSdayEarlier, TSNow).size()});
	
	}
	@Test
	public void testDeleteComponentEnergy() {
		Energy e = dm.insertEnergy(one, two, three, one, two, three, TSNow);
		int numberOfEng=dm.getEnergy(TSdayEarlier, TSNow).size();
		dm.deleteComponent("Energy", e.getSampleTimestamp());
		assertEquals(numberOfEng-1, dm.getEnergy(TSdayEarlier, TSNow).size());
	}
	@Test
	public void testDeleteComponentTemprature() {
		Temprature t = dm.insertTemprature(1, 2, 3, TSNow);
		int numberOfTmps=dm.getTemprature(TSdayEarlier, TSNow).size();
		dm.deleteComponent("Temprature", t.getSampleTimestamp());
		assertEquals(numberOfTmps-1, dm.getTemprature(TSdayEarlier, TSNow).size());
	}
	@Test
	public void testDeleteComponentTSnull() {
		Temprature t = dm.insertTemprature(1, 2, 3, TSNow);
		int numberOfEng=dm.getEnergy(TSdayEarlier, TSNow).size();
		int numberOfTmps=dm.getTemprature(TSdayEarlier, TSNow).size();
		dm.deleteComponent("Temprature", null);
		assertArrayEquals(new int[]{numberOfEng, numberOfTmps}, new int[]{dm.getEnergy(TSdayEarlier, TSNow).size(), dm.getTemprature(TSdayEarlier, TSNow).size()});
	}
	@Test
	public void testDeleteComponentTSnotExist() {
		Temprature t = dm.insertTemprature(1, 2, 3, TSNow);
		int numberOfEng=dm.getEnergy(TSdayEarlier, TSNow).size();
		int numberOfTmps=dm.getTemprature(TSdayEarlier, TSNow).size();
		dm.deleteComponent("Temprature", TSdayEarlier);
		assertArrayEquals(new int[]{numberOfEng, numberOfTmps}, new int[]{dm.getEnergy(TSdayEarlier, TSNow).size(), dm.getTemprature(TSdayEarlier, TSNow).size()});
	
	}
	@Test
	public void testDeleteCompletedMissionNull() {
    	Mission m = dm.insertMission(TSdayFromNow, Command.FORMAT_STATIC, 1);
    	int numOfMissions=dm.getMissions(TSdayEarlier, TSdayFromNow).size();
		dm.deleteCompletedMission(null);
		assertEquals(numOfMissions, dm.getMissions(TSdayEarlier, TSdayFromNow).size());
	}
	@Test
	public void testDeleteCompletedMissionValid() {
    	Mission m = dm.insertMission(TSdayFromNow, Command.FORMAT_STATIC, 1);
    	int numOfMissions=dm.getMissions(TSdayEarlier, TSdayFromNow).size();
		dm.deleteCompletedMission(m.getCreationTimestamp());
		assertEquals(numOfMissions-1, dm.getMissions(TSdayEarlier, TSdayFromNow).size());
	}



}
