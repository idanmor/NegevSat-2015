package MenuItems;

import Panels.AllMissionPanel;
import javafx.scene.layout.BorderPane;

public class MenuItemsAllMissions extends AbstractMenuItem {

	public MenuItemsAllMissions() {
		super("All Missions");
	}

	@Override
	public void applyActionOnGrid(BorderPane mainPane) {
		new AllMissionPanel(mainPane);

	}

}
