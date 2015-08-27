package Panels;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import data.DataManager;
import data.Mission;

public class AllMissionPanel extends AbstractMissionTablePanel {

	public AllMissionPanel(BorderPane mainPane) {
		super(mainPane);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void createColumns() {
		TableColumn date = new TableColumn("Creation Time");
		TableColumn component = new TableColumn("Start Time");
		TableColumn type = new TableColumn("Description");
		TableColumn sent = new TableColumn<>("Sent");
		
		date.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
		component.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
		type.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
		sent.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
		
		date.setCellValueFactory(
				new PropertyValueFactory<Mission,String>("creationTime")
				);
		component.setCellValueFactory(
				new PropertyValueFactory<Mission,String>("ExecutionTS")
				);
		type.setCellValueFactory(
				new PropertyValueFactory<Mission,String>("Description")
				);
		sent.setCellValueFactory(
				new PropertyValueFactory<Mission,String>("sentTime")
				);
		table.getColumns().addAll(date, component, type,sent);

		
	}
	
	@Override
	public List<Mission> getMissionsForLastMonth(){
		final long monthInMS = 26280000;//need to mult by 100
		Timestamp oldestTS=new Timestamp(System.currentTimeMillis() - monthInMS * 100);
		Timestamp TS=new Timestamp(System.currentTimeMillis());		
		return DataManager.getInstance().getMissions(oldestTS,TS);
	}

}
