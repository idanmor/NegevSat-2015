/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MissionFrames;

import MissionItems.AbstractMultipleItemsWrapper;
import MissionItems.MissionComboBoxWrapper;
import MissionItems.MissionDatePickerWrapper;
import MissionItems.MissionItemWrapper;
import MissionItems.MissionTableWrapper;
import MissionItems.MissionTextWrapper;
import MissionItems.TimeTextFieldWrapper;
import Utils.Constants;
import Utils.GuiManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import data.Command;
import negevsatgui.MainWindow.Component;
import negevsatgui.MainWindow.State;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
//import jfxtras.scene.control.agenda.Agenda;
import webmap.Coardinate;
import webmap.WebMap;

/**
 *
 * @author Max
 */
public class MissionSplitFrameIMPL implements MissionSplitFrameInterface{
	private BorderPane mainPane;
	private SplitPane mainSplitPane;
	private TreeView<MissionTreeItem> leftTree;
	private VBox rightPane;
	private Button confirmButton;
	private Button cancelButton;
	private Text missionSentStatus;
	private List<MissionTreeItem> mission_components_list;
	@SuppressWarnings("rawtypes")
	ObservableList<TreeItem> dateAndLocationlist;
	private final int MISSION_DESC = 0;
	private final int MISSION_DATE = 1;
	private final int MISSION_DATE_END = 2;

	private final int COMPONENT_ON_OFF_LOCATION = 4;
	private final int MISSION_LOCATION_TABLE = 5;
	private final int MISSION_MAX_NUM_ITEMS = 6;


	public MissionSplitFrameIMPL(BorderPane pane){
		mainSplitPane = new SplitPane();
		mainPane = new BorderPane();
		init();
		pane.setCenter(mainPane);

	}
	/**
	 * Create a mission according to the selected mission object - Mission Desc
	 * For adding more missions add a switch case that checks the mission_components_list.get(MISSION_DESC) value
	 * @throws Exception
	 */
	private void createMission() throws Exception{
		StringBuilder logUpdate = null;
		switch (mission_components_list.get(MISSION_DESC).getMissionType()) {
		case COMPONENT:
			logUpdate = createAndSendComponentMission();
			break;
		case PHOTO:
			//TODO
		case CUSTOM:
			//TODO
		default:
			break;
		}

		setSuccessStatus(logUpdate.toString());
		init();// inits right tree selection
	}
	/**
	 * Creates and sends a component mission to the database
	 */
	@SuppressWarnings("unchecked")
	private StringBuilder createAndSendComponentMission(){
		MissionTreeItem date = mission_components_list.get(MISSION_DATE);
		MissionComboBoxWrapper<Component> ComponentBox = (MissionComboBoxWrapper<Component>)mission_components_list.get(MISSION_DESC).getMissionItem();
		MissionComboBoxWrapper<State> stateBox = (MissionComboBoxWrapper<State>)mission_components_list.get(COMPONENT_ON_OFF_LOCATION).getMissionItem();
		Component selectedComponent = ComponentBox.getSelectionModel().getSelectedItem();
		Command c = selectedComponent.getCommand(stateBox.getSelectionModel().getSelectedItem());
		//;
		String dateString = date.getMissionStringValue();
		boolean isSent = GuiManager.getInstance().sendSatelliteModeCommand(dateString, c);
		MissionTreeItem endDate = mission_components_list.get(MISSION_DATE_END);
		if(endDate != null){
			String endDateString = date.getMissionStringValue();
			State originalState = stateBox.getSelectionModel().getSelectedItem();
			State endState = originalState == State.ON ? State.OFF : State.ON;
			isSent = isSent && GuiManager.getInstance().sendSatelliteModeCommand(endDateString, selectedComponent.getCommand(endState));
		}
		StringBuilder logUpdate = new StringBuilder();
		logUpdate.append(" A mission for component: ").append(selectedComponent.toString()).append(isSent ? " is sent." : " is stored");

		return logUpdate;


	}
	//If the mission was specified correctly(all the fields was correct) sets mission success text
	private void setSuccessStatus(String data){
		missionSentStatus.setText(data);
		missionSentStatus.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		missionSentStatus.setFill(Color.GREEN);
		GuiManager.getInstance().addToLog(data);
	}
	//If the mission was not specified correctly(one of the fields was incorrect) sets mission fail text

	private void setFailStatus(String data){
		missionSentStatus.setText(Constants.MISSION_SEND_FAILED);
		missionSentStatus.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		missionSentStatus.setFill(Color.RED);
		if(data != null){
			GuiManager.getInstance().addToLog(data);
		}
	}
	//initializes the panes

	private void init(){
		if(leftTree == null || rightPane == null){
			populateLeftList();
			mainSplitPane.setOrientation(Orientation.HORIZONTAL);
			rightPane = new VBox();
			mainSplitPane.getItems().addAll(leftTree,rightPane);

			mainPane.setCenter(mainSplitPane);
			mainPane.setBottom(getConfirmCancelButtons());
			mission_components_list = new ArrayList<>(MISSION_MAX_NUM_ITEMS);
		}
		rightPane.getChildren().clear();
		mission_components_list.clear();
		mission_components_list = new ArrayList<>(MISSION_MAX_NUM_ITEMS);
		for(int i = 0 ; i < MISSION_MAX_NUM_ITEMS ; i++){
			mission_components_list.add(null);
			rightPane.getChildren().add(new HBox());
		}
	}
	//Return a container with a confirm and cancel buttons
	private VBox getConfirmCancelButtons(){
		VBox bottomBox = new VBox();
		HBox box = new HBox();
		box.setPadding(new Insets(0, 0, 0, 200));
		box.setSpacing(200);
		box.getChildren().addAll(getConfirmButton(),getClearButton());
		box.setMaxHeight(0);
		missionSentStatus = new Text();
		bottomBox.getChildren().addAll(box, missionSentStatus);
		return bottomBox;
	}

	/**
	 * Returns the confirm button
	 * @return
	 */
	protected Button getConfirmButton(){
		if(confirmButton == null){
			confirmButton = new Button("Confirm Mission");
			confirmButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {
					try {
						createMission();
					} catch (Exception e) {
						setFailStatus(null);
						e.printStackTrace();
					}

				}
			});
		} 
		return confirmButton;

	}
	/**
	 * As of now it need to be implemented, this implementation is wrong
	 * @return
	 */
	private int getMaxLabelWidth(){
		//TODO
		return "Mission description:".length();      
	}
	/**
	 * Returns the clear/cancel button
	 * @return
	 */
	public Button getClearButton(){
		if(cancelButton == null){
			cancelButton = new Button("Cancel");
			cancelButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent t) {
					init();
				}
			});
		}
		return cancelButton;
	}
	/**
	 * Builds and populates the left list with Mission Items
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	public void populateLeftList() {
		if(leftTree == null){
			TreeItem root = initLeftTree();
			//Adding Items to the left tree
			TreeItem dateAndLocation = new TreeItem(new MissionTreeItem("Date and Location Items"));
			dateAndLocation.getChildren().addAll(getDateAndLocationItems());	
			TreeItem missionGeneral =  new TreeItem(new MissionTreeItem("General mission items"));
			missionGeneral.getChildren().addAll(getGeneralMissionDescription());

			TreeItem missionComponentsAction = new TreeItem(new MissionTreeItem("Components Actions"));
			missionComponentsAction.getChildren().addAll(getSatteliteComponentCommands());
			root.getChildren().addAll(dateAndLocation, missionGeneral, missionComponentsAction);

		}
	}
	@SuppressWarnings({"unchecked","rawtypes"})
	private TreeItem initLeftTree() {
		TreeItem root = new TreeItem(new MissionTreeItem("Build mission"));
		root.setExpanded(true);
		leftTree = new TreeView<>(root);
		leftTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);  
		leftTree.setOnMouseClicked(new EventHandler<MouseEvent>()
				{
			@Override
			public void handle(MouseEvent mouseEvent)
			{            
				TreeItem<MissionTreeItem> selectedItem = leftTree.getSelectionModel().getSelectedItem();
				if(selectedItem == null){
					mouseEvent.consume();
					return;
				}
				if(missionSentStatus != null){
					missionSentStatus.setText("");
				}
				if(mouseEvent.getClickCount() == 2)
				{
					MissionTreeItem item =  (MissionTreeItem) leftTree.getSelectionModel().getSelectedItem().getValue();
					if(item == null || item.getMissionItem() == null){ //nothing is selected, there is no actual input item
						mouseEvent.consume();
						return;
					}
					
					BorderPane box = new BorderPane();
					Button deletionButton = new Button("-");
					deletionButton.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent t) {
							int boxLocation = rightPane.getChildren().indexOf(box);
							mission_components_list.remove(boxLocation);
							mission_components_list.add(boxLocation,null);
							rightPane.getChildren().remove(box);
							rightPane.getChildren().add(boxLocation, new HBox());
							item.setAlreadySelected(false);
						}
					});
					box.setPadding(new Insets(10, 10, 10, 10));
					Label expLabel = item.getExplainLabel();

					if(expLabel != null){
						box.setLeft(item.getExplainLabel());
						box.setRight(new HBox(item.getMissionItem().getWrappedItem(),deletionButton));
					}else{
						box.getChildren().addAll(item.getMissionItem().getWrappedItem(),deletionButton); 
					}

					item.setAlreadySelected(true);
					
					rightPane.getChildren().remove(item.getMissionArrayLoc());
					rightPane.getChildren().add(item.getMissionArrayLoc(),box);
					mission_components_list.remove(item.getMissionArrayLoc());
					mission_components_list.add(item.getMissionArrayLoc(),item);
					item.getMissionItem().clearSelection();
				}
			}
				});
		return root;
	}
	/**
	 * The function returns a list of mission that is related to dates, locations, time ext...
	 * @return list of mission items 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected ObservableList<TreeItem> getDateAndLocationItems(){
		if(dateAndLocationlist == null){
			dateAndLocationlist = FXCollections.observableArrayList();
			MissionDatePickerWrapper picker = new MissionDatePickerWrapper(LocalDate.now());
			picker.setEditable(false);
			MissionDatePickerWrapper endPicker = new MissionDatePickerWrapper(LocalDate.now());
			endPicker.setEditable(false);
			AbstractMultipleItemsWrapper timeAndDate = new AbstractMultipleItemsWrapper(picker, new TimeTextFieldWrapper(LocalTime.now().toString()));
			AbstractMultipleItemsWrapper endTimeAndDate = new AbstractMultipleItemsWrapper(endPicker, new TimeTextFieldWrapper(LocalTime.now().toString()));

			dateAndLocationlist.add(new TreeItem (new MissionTreeItem(timeAndDate, "Mission Date", new Label("Date:"), MISSION_DATE)));
			dateAndLocationlist.add(new TreeItem(new MissionTreeItem(endTimeAndDate, "Mission end time", new Label("End mission:"), MISSION_DATE_END)));
			dateAndLocationlist.add(new TreeItem (getWebMapMissionItem()));
		}
		return dateAndLocationlist;
	}

	/**
	 * The function returns a list of missions that describe the mission for example: Take photo, measure temperature....
	 * @return list of mission items 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected ObservableList<TreeItem> getGeneralMissionDescription(){
		ObservableList<TreeItem> list = FXCollections.observableArrayList();
		MissionTextWrapper nonEditableField = new MissionTextWrapper("Take photo");
		nonEditableField.setEditable(false);
		list.add(new TreeItem (new MissionTreeItem(nonEditableField, "Take photo mission", new Label("Mission description:"),MISSION_DESC,MissionType.PHOTO)));
		list.add(new TreeItem (new MissionTreeItem(new MissionComboBoxWrapper<Component>(getListOfSatteliteComponents()), "Manage component mission", new Label("Choosen Component:"),MISSION_DESC, MissionType.COMPONENT)));
		list.add(new TreeItem (new MissionTreeItem(new MissionTextWrapper("Type Text Here"), "Custom mission", new Label("Mission description:"), MISSION_DESC, MissionType.CUSTOM)));
		return list;

	}
	/**
	 * Returns Commands that could be done on a component
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected ObservableList<TreeItem> getSatteliteComponentCommands(){
		ObservableList<TreeItem> list = FXCollections.observableArrayList();
		ObservableList<State> mode = FXCollections.observableArrayList(State.values());
		list.add(new TreeItem (new MissionTreeItem(new MissionComboBoxWrapper(mode), "Change Component Status", new Label("Mode:"),COMPONENT_ON_OFF_LOCATION)));      
		return list;
	} 
	/**
	 * Returns a list of components in the satellite
	 * @return
	 */
	private ObservableList<Component> getListOfSatteliteComponents(){
		return FXCollections.observableArrayList(Component.values());
	}

	/**
	 * This is not implemented fully, once we get the STK licence this function should be deleted  
	 * @return
	 */
	private MissionTreeItem getWebMapMissionItem(){
		MissionTreeItem item = new MissionTreeItem(generateLocationTable(), "SelectLocation", null, MISSION_LOCATION_TABLE){
			public void doAdditionalActionsOnSelection(){
				WebMap map = new WebMap();
				map.start();
			}
		};
		return item;                    
	}

	@Override
	public String buildMissionSummary() {
		return "unsupported";
	}
	
	
	/**
	 * This is not implemented fully, once we get the STK licence this function should be deleted  
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private MissionTableWrapper generateLocationTable(){
		MissionTableWrapper table = new MissionTableWrapper();

		table.setEditable(false);
		TableColumn lat = new TableColumn("Latitude");
		TableColumn lng = new TableColumn("Longtitude");
		// TableColumn Radius = new TableColumn("Radius");
		table.getColumns().addAll(lat, lng);

		//        Task<List<Coardinate>> task = new Task<List<Coardinate>>() {
		//
		//            @Override
		//            protected List<Coardinate> call() throws Exception {
		//                WebMap w = new WebMap();
		//                w.start();
		//                return w.getMarkers();
		//             }
		//        };
		//       //  w.start();
		//         task.run();
		//         
		//        try {
		//            addToTable(task.get(),table);
		//        } catch (InterruptedException ex) {
		//            Logger.getLogger(MissionItemsTakePhoto.class.getName()).log(Level.SEVERE, null, ex);
		//        } catch (ExecutionException ex) {
		//            Logger.getLogger(MissionItemsTakePhoto.class.getName()).log(Level.SEVERE, null, ex);
		//        }
		return table;
	}
	/**
	 * This is not implemented fully, once we get the STK licence this function should be deleted  
	 * @return
	 */
	private void addToTable(List <Coardinate> markers, TableView table){
		for(int i = 0; i < markers.size() ; i++){
			Coardinate single = markers.get(i);
			table.getItems().addAll(single.getLat(),single.getLng(), new DeleteButton(i, table));
		}

	}
	
	
	/**
	 * This class represents an Item in the left (mission components) tree,
	 * each MissionTreeItem is consisted of the item(such as combobox), the name to be displayed and a label if needed
	 * most importantly it has a missionArrayLocation by which the program will know where to place it on the screen
	 * @author Max
	 *
	 */
	private class MissionTreeItem{
		private MissionItemWrapper item = null;
		private String name = null;
		private Label explainLabel = null;
		private int missionArrayLocation = -1;
		private MissionType missionType;


		public MissionTreeItem(MissionItemWrapper item, String name, Label explainLabel, int missionArrayLocation, MissionType missionType){
			this.item = item;
			this.name = name;
			this.explainLabel = explainLabel;
			this.missionType = missionType;
			this.missionArrayLocation = missionArrayLocation;
		}
		public MissionTreeItem(MissionItemWrapper item, String name, Label explainLabel, int missionArrayLocation){
			this(item, name, explainLabel, missionArrayLocation, null);
		}

		public MissionType getMissionType() {
			return missionType;
		}

		public MissionTreeItem(String name){
			this.name = name;
		}

		@Override
		public String toString(){
			return name;
		}

		public MissionItemWrapper getMissionItem(){
			return item;
		}
		public int getMissionArrayLoc(){
			return missionArrayLocation;
		}
		public Label getExplainLabel(){
			return explainLabel;
		}

		@SuppressWarnings("unused")
		public void doAdditionalActionsOnSelection(){
			//if any additional actions needed override this function
		}
		public void setAlreadySelected(boolean isInSummary){
		}

		public String getMissionStringValue(){
			return item.getValueStringWithPreIfNeeded(null);
		}
	}
	/**
	 * Add new mission Types here
	 * @author Max
	 *
	 */
	private enum MissionType{
		COMPONENT,
		PHOTO,
		CUSTOM;
	}
	public class DeleteButton extends Button{
		private int tableIndex;


		public DeleteButton(int index, TableView table){
			index = tableIndex;
			this.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent t) {
					table.getItems().remove(tableIndex);
					mission_components_list.remove(tableIndex);
					mission_components_list.add(tableIndex, null);
				}
			});
		}


		@Override
		public String toString(){   
			return "Delete row";
		}
	}
}
