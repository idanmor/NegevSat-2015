/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MenuItems;

import Panels.UnsentMissionsPanel;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Max
 */
public class MenuItemUnsentMissions extends AbstractMenuItem{

    public MenuItemUnsentMissions() {
        super("Unsent Missions");
    }

    @Override
    public void applyActionOnGrid(BorderPane mainPane) {
        new UnsentMissionsPanel(mainPane);
    }
    
}
