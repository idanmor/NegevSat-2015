package Panels;

import MissionFrames.MissionSplitFrameIMPL;
import Utils.GuiManager;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MissionPanel  extends PanelsWithClickInterface  {
	
	public MissionPanel(BorderPane mainPane, Pane parent) {
		super(mainPane, GuiManager.getInstance().getSatteliteMapView());
		
//		this.view.fitWidthProperty().bind(parent.widthProperty());
//		this.view.fitHeightProperty().bind(parent.heightProperty());
	}
	@Override
	public void applyClickOnPanel() {
		new MissionSplitFrameIMPL(mainPane);
	}

}
