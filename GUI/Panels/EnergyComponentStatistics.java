package Panels;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import Utils.Constants;
import data.Component;
import data.DataManager;
import misc.StatisticDataItemInterface;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;

public class EnergyComponentStatistics extends AbstractComponentStatistics{

	public EnergyComponentStatistics(BorderPane mainParentPane) {
		super(mainParentPane);

	}

	@Override
	protected String getExellFileLocationAndName() {
		 return Constants.STATISTICS_VOLTAGE_DISK_LOCATION;
	}

	@Override
	public List<Component> getComponent(Timestamp oldestTS, Timestamp TS) {		
		return new ArrayList<>(DataManager.getInstance().getEnergy(oldestTS, TS));
	}
	@Override
	public String getObjectName() {
		return Constants.ENERGY_COMPONENT_NAME;
	}


}
