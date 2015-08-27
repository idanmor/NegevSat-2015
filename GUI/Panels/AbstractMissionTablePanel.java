package Panels;

import java.sql.Timestamp;
import java.util.List;

import Utils.GuiManager;
import orbit.OrbitManager;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import data.DataManager;
import data.Mission;

public abstract class AbstractMissionTablePanel {
	protected BorderPane tablePane;
	protected TableView<TableNode> table;
	protected Button send;
	
	public AbstractMissionTablePanel(BorderPane mainPane) {
		initialize();
		mainPane.setCenter(tablePane);
	}
	private void initialize() {
		List<Mission> missions = getMissionsForLastMonth();
		tablePane = new BorderPane();

		HBox box = new HBox();
		box.setSpacing(400);
		Button deleteButton = new Button("Delete Mission");
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				List<TableNode> items = table.getSelectionModel().getSelectedItems();
				for(MissionWrapper item : items){
					DataManager.getInstance().deleteCompletedMission(item.getMission().getCreationTimestamp());
				}
				table.getItems().removeAll(items);
				table.getSelectionModel().clearSelection();
				

			}
		});
		send = new Button("Send");
		send.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				for(MissionWrapper node : table.getSelectionModel().getSelectedItems()){
					GuiManager.getInstance().sendMission(node);
				}

			}
		});

		createTable();
		populateTable(missions);
		box.getChildren().addAll(send,deleteButton);
		tablePane.setCenter(table);
		tablePane.setBottom(box);
		box.setPadding(new Insets(10, 10, 10, 10));
		box.setAlignment(Pos.CENTER);
	}


	private void populateTable(List<Mission> missions) {
		if(missions == null){
			return;
		}
		ObservableList<TableNode> nodes = FXCollections.observableArrayList();
		for(Mission mission : missions){
			nodes.add(new TableNode(mission));
		}
		table.getItems().addAll(nodes);
	}

	private void createTable(){
		if(table == null){
			table = new TableView<>();
			table.setPrefWidth(600);
			table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			createColumns();
			
		}		
	}
	
	// Abstract methods
	/**
	 * Creates the columns for the table
	 */
	public abstract void createColumns();
	/**
	 * Gets the missions that would be put to the table
	 * @return
	 */
	public abstract List<Mission> getMissionsForLastMonth();
	
	
	
	
	
	/**
	 * A class that is used by a TableView in order to get the data on a mission
	 * @author Max
	 *
	 */
	public class TableNode implements MissionWrapper{
		private Mission mission;
		private Timestamp creationTime;
		private Timestamp executionTS;
		private String description;
		private String sentTime;
		
		public TableNode(Mission mission){
			this.creationTime = mission.getCreationTimestamp();
			this.executionTS = mission.getExecutionTime();
			this.description = mission.getDescription();
			this.sentTime = mission.getSentTime() == null ? "Not Sent" : mission.getSentTime().toString();
			this.mission = mission;
		}
		/* (non-Javadoc)
		 * @see Panels.MissionWrapper#getCreationTime()
		 */
		@Override
		public Timestamp getCreationTime() {
			return creationTime;
		}
		/* (non-Javadoc)
		 * @see Panels.MissionWrapper#getExecutionTS()
		 */
		@Override
		public Timestamp getExecutionTS() {
			return executionTS;
		}
		/* (non-Javadoc)
		 * @see Panels.MissionWrapper#getDescription()
		 */
		@Override
		public String getDescription() {
			return description;
		}
		/* (non-Javadoc)
		 * @see Panels.MissionWrapper#getMission()
		 */
		@Override
		public Mission getMission(){
			return mission;
		}
		
		/* (non-Javadoc)
		 * @see Panels.MissionWrapper#getSent()
		 */
		@Override
		public boolean getSent(){
			return getSentTime().isEmpty();
		}
		/* (non-Javadoc)
		 * @see Panels.MissionWrapper#getSentTime()
		 */
		@Override
		public String getSentTime(){
			return sentTime;
		}
		
		
	}
}
