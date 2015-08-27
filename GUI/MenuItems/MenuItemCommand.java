/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MenuItems;


import MissionFrames.MissionSplitFrameIMPL;
import javafx.scene.layout.BorderPane;


/**
 *
 * @author Max
 */
public class MenuItemCommand extends AbstractMenuItem {
  

    public MenuItemCommand() {
        super("Mission Command");
    }

    @Override
    public void applyActionOnGrid(BorderPane mainPane) {
      //  CommandScreen factory = new CommandScreen();
     //   factory.createScreenPane(mainPane);
        MissionSplitFrameIMPL m = new MissionSplitFrameIMPL(mainPane);
        
    }
    
}
