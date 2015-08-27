package Utils;

import negevsatgui.MainWindow.DataAcquisitionMode;
import negevsatgui.MainWindow.SatelliteMods;
import Panels.MissionWrapper;
import data.Command;
import data.Satellite;
import data.Satellite.SatelliteState;

public interface IGuiManager {

	/**
	 * Connected/Disconnected
	 * @param status
	 */
	public void setNewSatteliteStatusInGui(String status);

	/**
	 * Sends immediate satellite command to the satellite, only viable in pass mode
	 * The execution is immediate
	 * For example change the mode of the satellite to safe mode
	 * @param command
	 */
	public void sendImmidiateSatelliteModeCommand(SatelliteMods selectedItem);

	/**
	 * Sends immediate Data acquisition command to the satellite, only viable in pass mode.
	 * For example the gui asks the satellite for temperature statistics.
	 * The execution is immediate
	 * @param command 
	 */
	public void sendImmidiateDataAquisitionCommand(
			DataAcquisitionMode dataAcquisitionMode);

	/**
	 * Sends immediate component command to the satellite, only viable in pass mode
	 * The execution is immediate
	 * @param command
	 */
	public void sendImmidiateComponentStatusChange(Command command);

	public void sendMission(MissionWrapper wrapper);

	/**
	 * Function used when there is a new data from the satellite(for example entered pass mode)
	 * @param st
	 */
	public void refreshSatelliteController(Satellite st);

	/**
	 * Add data to the log, the date that will be written is the exact date that this function was called
	 * @param data
	 */
	public void addToLog(String data);

	/**
	 * Adds data to log
	 * @param date date for receiving the data
	 * @param data the data to be written to the log
	 */
	public void addToLog(String date, String data);

	/**
	 * Sends Mode command to the satellite
	 * @param dateString
	 * @param c
	 * @return 
	 */
	public boolean sendSatelliteModeCommand(String dateString, Command c);

	/**
	 * Gets the last satellite state recorded in the database
	 * @return
	 */
	public SatelliteState getLastSatelliteState();

	/**
	 * Checks and returns the current satellite pass status
	 * 
	 * @return true if in pass false otherwise
	 */
	public boolean getInPassPhase();

	public String getSatteliteMapView();

}