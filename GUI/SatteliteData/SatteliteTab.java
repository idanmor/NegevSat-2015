package SatteliteData;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SatteliteTab extends Tab {
	private final int NUM_OF_OBJECTS_PER_LINE = 6;

	public SatteliteTab(Label title, Label... objects){
		super();
		this.setGraphic(title);
		initContent(objects);
	}
	
	private void initContent(Label... objects){
		if(objects == null || objects.length == 0){
			return;
		}
		int numOfColums =  /*objects.length < NUM_OF_OBJECTS_PER_LINE ? 1 :*/ objects.length / NUM_OF_OBJECTS_PER_LINE;
		//this.setText(title.getText());
		VBox mainVertical = new VBox();
		int objectIndex = 0;
		for(int i = 0 ; i < numOfColums + 1 ; i++){
			HBox box = new HBox();
			for(int j = 0 ; j < Math.min(NUM_OF_OBJECTS_PER_LINE,objects.length); j++, objectIndex++){
				box.getChildren().add(objects[objectIndex]);
			}
			mainVertical.getChildren().add(box);
		}
		
	}
}
