package communication;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.junit.Test;

import java.sql.Timestamp; 

import data.DataManager;
import data.Energy;
import data.Satellite;
import data.Temprature;
import static org.junit.Assert.*;

import org.junit.Test;

public class CommTest {

	@Test
	public final void testParseTemp() {

		// TO DO :: CLEAR ALL TEMP TABLE
		 DataManager.getInstance().setTestMode(true);
		long g =  Long.MAX_VALUE;
		//DataManager.getInstance().deleteComponent("Temprature", new Timestamp(g)); // clean this row	
		byte[] test= {2,1,0,0,18,81,90,99,53,-42,0,0,0,4};
		MessageParser.parseTemp(test);
		List<Temprature> t =	DataManager.getInstance().getTemprature(new Timestamp(0), new Timestamp(g)); 
		assertTrue(t.size()== 1); 
	}
	
	@Test
	public final void testParseEnergy() {
		DataManager.getInstance().setTestMode(true);
		long g =  Long.MAX_VALUE;
		//DataManager.getInstance().deleteComponent("Temprature", new Timestamp(g)); // clean this row	
		byte[] test= {1,6,0,0,18,81,90,99,53,-42,0,0,0,18,0,0,0,1,
				0,0,18,81,90,99,53,-43,0,0,0,22,0,0,0,6,
				0,0,18,81,90,99,53,-44,0,0,0,16,0,0,0,22,
				0,0,18,81,90,99,53,-45,0,0,0,32,0,0,0,13,
				0,0,18,81,90,99,53,-46,0,0,0,8,0,0,0,5,
				0,0,18,81,90,99,53,-47,0,0,0,21,0,0,0,44
				};
		MessageParser.parseEnergy(test); 
		List<Energy> t =	DataManager.getInstance().getEnergy(new Timestamp(0), new Timestamp(g)); 
		assertTrue(t.size()== 6); 
	}


	@Test
	public final void testEnergyGenerate (){
		DataManager.getInstance().setTestMode(true);
		Vector<Long> vector = new Vector<Long>();
		Random rand = new Random();	
		int intNumOfSampels = rand.nextInt(9)+1;
		byte[] temp = MessageParser.intToByteArray(intNumOfSampels);
		byte NumOfSampels = temp[3];
		Vector<Long> alltimes = new Vector<Long>();
		byte[] alldata = new byte[NumOfSampels*16 + 2];
		alldata[0]=1;
		alldata[1]=NumOfSampels;
		for (int i=0; i< NumOfSampels; i++){
			long time = rand.nextLong();
			alltimes.add(time);
			int current = rand.nextInt();
			int amp = rand.nextInt();
			byte[] timebyte = MessageParser.longToByteArray(time);
			byte[] currbyte = MessageParser.intToByteArray(current);
			byte[] ampbyte = MessageParser.intToByteArray(amp);
			for (int j = 0; j < 8; j++) {
				alldata[2+i*16+j]=timebyte[j];
			}
			for (int j = 0; j < 4; j++) {
				alldata[2+i*16+8+j]=currbyte[j];
			}
			for (int j = 0; j < 4; j++) {
				alldata[2+i*16+12+j]=ampbyte[j];
			}
			
		}
		
		MessageParser.parseEnergy(alldata); 
		List<Energy> t =	DataManager.getInstance().getEnergy(new Timestamp(Long.MIN_VALUE), new Timestamp(Long.MAX_VALUE)); 
		for (int k =0;k<t.size();k++)
			vector.add(new Long(t.get(k).getSampleTimestamp().getTime()));
		
		assertTrue(t.size()== NumOfSampels); 
		for (int i=NumOfSampels-1; i>-1; i--){
			String timestring  = Long.toString(alltimes.elementAt(i).longValue());
			long l1 = MessageParser.parseRTEMSTimestamp(timestring);
			long l2 = new Timestamp(l1).getTime();
			boolean bool = vector.contains(l2);
			assertTrue(bool);
		}
	}
	
	
	
	@Test
	public final void testParseStaticGenerate() {
		long g =  Long.MAX_VALUE;
		DataManager.getInstance().getSatellite(new Timestamp(0), new Timestamp(g));
		DataManager.getInstance().setTestMode(true);
		
		
		
		Random rand = new Random();	
		int intNumOfSampels = rand.nextInt(9)+1;
		byte[] temp = MessageParser.intToByteArray(intNumOfSampels);
		byte NumOfSampels = temp[3];
		byte[] alldata = new byte[NumOfSampels*10 + 2];
		alldata[0]=0;
		alldata[1]=NumOfSampels;
		for (int i=0; i< NumOfSampels; i++){
			
			int comcode =(int) Math.random()*10;
			byte[] comcodetemp = MessageParser.intToByteArray(comcode);
			byte comcodebyte = comcodetemp[3];
			
			int status =(int) Math.random()*10;
			byte[] statustemp = MessageParser.intToByteArray(status);
			byte statusbyte = statustemp[3];
			
			long time = rand.nextLong();
			byte[] timebyte = MessageParser.longToByteArray(time);
			
			alldata[2+i*10]=comcodebyte;
			for (int j = 0; j < 8; j++) {
				alldata[2+i*10+j+1]=timebyte[j];
			}
			alldata[2+i*10+9]=statusbyte;
		}
		
		
		MessageParser.parseStatic(alldata);
		List<Satellite> t =	DataManager.getInstance().getSatellite(new Timestamp(Long.MIN_VALUE), new Timestamp(Long.MAX_VALUE));
		assertTrue(t.size()== 1); 
	}
	
	@Test
	public final void testParseStatic() {
		long g =  Long.MAX_VALUE;
		DataManager.getInstance().getSatellite(new Timestamp(0), new Timestamp(g));
		DataManager.getInstance().setTestMode(true);
		byte[] test= {0,6,
				2,0,0,18,81,90,99,53,-42,3,
				3,0,0,18,81,90,99,53,-42,0,
				0,0,0,18,81,90,99,53,-42,0,
				4,0,0,18,81,90,99,53,-42,0,
				1,0,0,18,81,90,99,53,-42,3,
				5,0,0,18,81,90,99,53,-42,3
				};
		MessageParser.parseStatic(test);
	}
	 

	@Test
	public final void testTempGenerate() {
		DataManager.getInstance().setTestMode(true);
		Vector<Long> vector = new Vector<Long>();
		Random rand = new Random();	
		//byte NumOfSampels = MessageParser.intToByteArray(rand.nextInt(9)+2)[0] ;
		int intNumOfSampels = rand.nextInt(9)+1;
		byte[] temp = MessageParser.intToByteArray(intNumOfSampels);
		byte NumOfSampels = temp[3];
		//byte NumOfSampels =10;
		Vector<Long> alltimes = new Vector<Long>();
		byte[] alldata = new byte[NumOfSampels*12 + 2];
		alldata[0]=1;
		alldata[1]=NumOfSampels;
		for (int i=0; i< NumOfSampels; i++){
			long time = rand.nextLong();
			alltimes.add(time);
			int TEMPERATURE = rand.nextInt();
			byte[] timebyte = MessageParser.longToByteArray(time);
			byte[] TEMPERATUREbyte = MessageParser.intToByteArray(TEMPERATURE);
			for (int j = 0; j < 8; j++) {
				alldata[2+i*12+j]=timebyte[j];
			}
			for (int j = 0; j < 4; j++) {
				alldata[2+i*12+8+j]=TEMPERATUREbyte[j];
			}

		}

		long g =  Long.MAX_VALUE;
		MessageParser.parseTemp(alldata); 
		List<Temprature> t =	DataManager.getInstance().getTemprature(new Timestamp(Long.MIN_VALUE), new Timestamp(g)); 
		for (int k =0;k<t.size();k++)
			vector.add(new Long(t.get(k).getSampleTimestamp().getTime()));

		assertTrue(t.size()== NumOfSampels); 
		for (int i=NumOfSampels-1; i>-1; i--){
			String timestring  = Long.toString(alltimes.elementAt(i).longValue());
			long l1 = MessageParser.parseRTEMSTimestamp(timestring);
			long l2 = new Timestamp(l1).getTime();
			boolean bool = vector.contains(l2);
			assertTrue(bool);
		}
	}
	
}
