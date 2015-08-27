package Utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Panels.MissionWrapper;
import orbit.OrbitManager;
import javafx.application.Platform;
import communication.CommunicationManager;
import data.Command;
import data.DataManager;
import data.Mission;
import data.Satellite;
import data.Satellite.SatelliteState;
import negevsatgui.MainWindow;
import negevsatgui.MainWindow.DataAcquisitionMode;
import negevsatgui.MainWindow.SatelliteMods;
import negevsatgui.SattelitePictureController;

public class GuiManager implements IGuiManager {
	private static IGuiManager instance = null;
	private MainWindow mainWindow;
	private GuiManager(){
		this.mainWindow = MainWindow.getMainWindow();
	}

	public static IGuiManager getInstance(){
		if(instance == null){
			instance = new GuiManager();
		}
		return instance;
	}
	
	public static void overrideGuiManagerInstance(IGuiManager newGuiManager){
		instance = newGuiManager;
	}
	
	
	/* (non-Javadoc)
	 * @see Utils.IGuiManager#setNewSatteliteStatusInGui(java.lang.String)
	 */
	@Override
	public void setNewSatteliteStatusInGui(String status){
		mainWindow.setSatelliteStatusText(status);
	}
	/* (non-Javadoc)
	 * @see Utils.IGuiManager#sendImmidiateSatelliteModeCommand(negevsatgui.MainWindow.SatelliteMods)
	 */
	@Override
	public void sendImmidiateSatelliteModeCommand(SatelliteMods selectedItem) {
		Mission newMission = DataManager.getInstance().insertMission(null, selectedItem.getCommand(), 1);
		addToLog("Sending mission to Satellite -" + selectedItem.toString());
		CommunicationManager.getInstance().sendMission(newMission);
	}
	/* (non-Javadoc)
	 * @see Utils.IGuiManager#sendImmidiateDataAquisitionCommand(negevsatgui.MainWindow.DataAcquisitionMode)
	 */
	@Override
	public void sendImmidiateDataAquisitionCommand(DataAcquisitionMode dataAcquisitionMode) {
		Mission newMission =  DataManager.getInstance().insertMission(null, dataAcquisitionMode.getCommand(), 1);
		addToLog("Sending mission to Sattelite -" + dataAcquisitionMode.toString());
		CommunicationManager.getInstance().sendMission(newMission);
	}
	
	/* (non-Javadoc)
	 * @see Utils.IGuiManager#sendImmidiateComponentStatusChange(data.Command)
	 */
	@Override
	public void sendImmidiateComponentStatusChange(Command command) {
		Mission newMission =  DataManager.getInstance().insertMission(null,command, 1);
		addToLog("Sending change component status mission to Satellite");
		CommunicationManager.getInstance().sendMission(newMission);
	}
	
	
	/* (non-Javadoc)
	 * @see Utils.IGuiManager#sendMission(Panels.MissionWrapper)
	 */
	@Override
	public void sendMission(MissionWrapper wrapper){
		Mission newMission =  DataManager.getInstance().insertMission(wrapper.getExecutionTS(), wrapper.getMission().getCommand(), wrapper.getMission().getPriority());
		addToLog("Sending mission: " + newMission.getDescription() + "to Satellite");
		CommunicationManager.getInstance().sendMission(newMission);
	}
	/* (non-Javadoc)
	 * @see Utils.IGuiManager#refreshSatelliteController(data.Satellite)
	 */
	@Override
	public void refreshSatelliteController(Satellite st){
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				addToLog("Satellite status data recieved");
				SattelitePictureController cont = MainWindow.getMainWindow().getSatellitePictureController();
				cont.nonSupdateSateliteStatus(st);
				MainWindow.getMainWindow().setSatelliteState(st);
				
			}
		});
	
	}
	/* (non-Javadoc)
	 * @see Utils.IGuiManager#addToLog(java.lang.String)
	 */
	@Override
	public void addToLog(String data){
		if(data == null){
			return;
		}
		
		DateFormat writeFormat = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss");
		
		String date = writeFormat.format(Calendar.getInstance().getTime());
		addToLog(date, data);
	}
	
	/* (non-Javadoc)
	 * @see Utils.IGuiManager#addToLog(java.lang.String, java.lang.String)
	 */
	@Override
	public void addToLog(String date,String data){
		if(data == null){
			return;
		}	
			mainWindow.addToLog("("+ date + ")" + data);
	}

	/* (non-Javadoc)
	 * @see Utils.IGuiManager#sendSatelliteModeCommand(java.lang.String, data.Command)
	 */
	@Override
	public boolean sendSatelliteModeCommand(String dateString, Command c) {
		DateFormat writeFormat = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss");
		dateString = dateString.replace("/", "-");
		Date date = null;
		try {
			date = writeFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		Timestamp ts = new Timestamp(date.getTime());
		Mission mission = new Mission(ts, c, 1);
		DataManager.getInstance().insertMission(ts, c, 1);
		if(OrbitManager.getInstance().isPassPhase()){
			CommunicationManager.getInstance().sendMission(mission);
			return true;
		}else{
			return false;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see Utils.IGuiManager#getLastSatelliteState()
	 */
	@Override
	public SatelliteState getLastSatelliteState(){
		DataManager dm = DataManager.getInstance();
		return dm.getLastSateliteState();
	}
	
	/* (non-Javadoc)
	 * @see Utils.IGuiManager#getInPassPhase()
	 */
	@Override
	public boolean getInPassPhase(){
		return OrbitManager.getInstance().isPassPhase();
	}

	/* (non-Javadoc)
	 * @see Utils.IGuiManager#getSatteliteMapView()
	 */
	@Override
	public String getSatteliteMapView() {
		return getInPassPhase() ? Constants.INPASS : Constants.NOT_PASS;
		
	}

}
