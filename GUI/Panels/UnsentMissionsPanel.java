package Panels;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import data.DataManager;
import data.Mission;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class UnsentMissionsPanel extends AbstractMissionTablePanel {

	public UnsentMissionsPanel(BorderPane mainPane) {
		super(mainPane);
		
	}

	
	public List<Mission> getMissionsForLastMonth(){
		final long monthInMS = 26280000;//need to mult by 100
		Timestamp oldestTS=new Timestamp(System.currentTimeMillis() - monthInMS * 100);
		Timestamp TS=new Timestamp(System.currentTimeMillis());
		List<Mission> unfilteredMissions = DataManager.getInstance().getMissions(oldestTS,TS);
		if(unfilteredMissions == null){
			return new ArrayList<>();
		}
		List<Mission> filtered = new ArrayList<Mission>();
		for(Mission mission : unfilteredMissions){
			if(mission.getSentTime() == null){//not sent
				filtered.add(mission);
			}
		}
		return filtered;
	}

	
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void createColumns() {
		TableColumn date = new TableColumn("Creation Time");
		TableColumn component = new TableColumn("Start Time");
		TableColumn type = new TableColumn("Description");
		date.prefWidthProperty().bind(table.widthProperty().multiply(0.5));
		component.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
		type.prefWidthProperty().bind(table.widthProperty().multiply(0.25));

		date.setCellValueFactory(
				new PropertyValueFactory<Mission,String>("creationTime")
				);
		component.setCellValueFactory(
				new PropertyValueFactory<Mission,String>("ExecutionTS")
				);
		type.setCellValueFactory(
				new PropertyValueFactory<Mission,String>("Description")
				);
		table.getColumns().addAll(date, component, type);

		
	}


}
