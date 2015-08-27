package Utils;

import orbit.OrbitManager;
import negevsatgui.MainWindow.DataAcquisitionMode;
import negevsatgui.MainWindow.SatelliteMods;
import Panels.MissionWrapper;
import data.Command;
import data.DataManager;
import data.Satellite;
import data.Satellite.SatelliteState;

public class GuiManagerForNonGuiTests implements IGuiManager {

	@Override
	public void setNewSatteliteStatusInGui(String status) {
		}

	@Override
	public void sendImmidiateSatelliteModeCommand(SatelliteMods selectedItem) {
		}

	@Override
	public void sendImmidiateDataAquisitionCommand(
			DataAcquisitionMode dataAcquisitionMode) {
	
	}

	@Override
	public void sendImmidiateComponentStatusChange(Command command) {
	
	}

	@Override
	public void sendMission(MissionWrapper wrapper) {
	
	}

	@Override
	public void refreshSatelliteController(Satellite st) {
	
	}

	@Override
	public void addToLog(String data) {
	
	}

	@Override
	public void addToLog(String date, String data) {
	
	}

	@Override
	public boolean sendSatelliteModeCommand(String dateString, Command c) {
		return getInPassPhase();
	}

	@Override
	public SatelliteState getLastSatelliteState(){
		DataManager dm = DataManager.getInstance();
		return dm.getLastSateliteState();
	}
	@Override
	public boolean getInPassPhase() {
		return OrbitManager.getInstance().isPassPhase();
	}

	@Override
	public String getSatteliteMapView() {
		return "Unsupported";
	}

}
