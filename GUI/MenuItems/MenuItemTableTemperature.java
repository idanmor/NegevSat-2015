package MenuItems;

import Panels.TemperatureComponentStatistics;
import javafx.scene.layout.BorderPane;

public class MenuItemTableTemperature extends AbstractMenuItem {

	public MenuItemTableTemperature() {
		super("Components Temperature");
	}

	@Override
	public void applyActionOnGrid(BorderPane mainPane) {
		new TemperatureComponentStatistics(mainPane);
		
	}
}
