package MenuItems;

import negevsatgui.MainWindow;
import javafx.scene.layout.BorderPane;

public class MenuItemHome extends AbstractMenuItem {

	public MenuItemHome() {
		super("Home");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void applyActionOnGrid(BorderPane mainPane) {
		MainWindow.getMainWindow().showMainScreen(mainPane);

	}

}
