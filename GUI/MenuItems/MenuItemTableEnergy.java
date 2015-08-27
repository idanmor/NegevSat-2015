package MenuItems;

import Panels.EnergyComponentStatistics;
import javafx.scene.layout.BorderPane;

public class MenuItemTableEnergy extends AbstractMenuItem {

	public MenuItemTableEnergy() {
		super("Components Energy");
	}

	@Override
	public void applyActionOnGrid(BorderPane mainPane) {
		new EnergyComponentStatistics(mainPane);
		
	}

}
