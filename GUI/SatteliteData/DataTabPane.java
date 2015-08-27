package SatteliteData;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Utils.Constants;
import Utils.Utils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class DataTabPane {
	private final int NUM_OF_TABS = STabs.values().length;
	BorderPane pane = null;
	private List<Label> tabTitles;
	public DataTabPane(BorderPane mainPane){
		Group root = new Group();
		this.pane = mainPane;
		Scene scene = new Scene(root, 400, 250, Color.WHITE);
		TabPane tabPane = new TabPane();
		BorderPane borderPane = new BorderPane();
		tabTitles = new ArrayList<>();
		for (int i = 0; i < NUM_OF_TABS; i++) {
			tabTitles.add(STabs.values()[i].getLabelForTab());
			//Tab tab = new SatteliteTab();
//			tab.setGraphic(new Circle(0, 0, 10));
//			HBox hbox = new HBox();
//			hbox.getChildren().add(new Label("Tab" + i));
//			hbox.setAlignment(Pos.CENTER);
//			tab.setContent(hbox);
//			tabPane.getTabs().add(tab);
		}
		// bind to take available space
		borderPane.prefHeightProperty().bind(scene.heightProperty());
		borderPane.prefWidthProperty().bind(scene.widthProperty());

		borderPane.setCenter(tabPane);
		root.getChildren().add(borderPane);

	}
	
	private void initTabs(){
		
	}
	
	private void getDemoTemperatureTabs(){
		
	}
	
	public enum STabs{
		Temperature("Temperature", Constants.ICON_TEMPERATURE),
		Voltage("Voltage", Constants.ICON_VOLTAGE),
		CPU("CPU",Constants.ICON_CPU);
		
		
		private String name;
		private Image icon;
		
		private STabs(String name,String imageLocation){
			this.name = name;
			this.icon = new Image(getClass().getResourceAsStream(imageLocation));
		}
		
		public Label getLabelForTab(){
			return Utils.getTextPictureLabel(name, icon);
			
		}
	}
	
}

