package Panels;

import SatteliteData.DataTabPane;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class SatteliteStatusPanel extends PanelsWithClickInterface {
	public SatteliteStatusPanel(BorderPane mainPane) {
		super(mainPane, "spinningEarth.gif");
		
	}
	
	public SatteliteStatusPanel(BorderPane mainPane , Parent parentTOCopy) {
		super(mainPane, parentTOCopy);
	}
	
	@Override
	public void applyClickOnPanel() {
		new DataTabPane(mainPane);
	}
	
}
