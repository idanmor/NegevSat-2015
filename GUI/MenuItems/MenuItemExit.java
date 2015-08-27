/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MenuItems;


import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import negevsatgui.MainWindow;

/**
 *
 * @author Max
 */
public class MenuItemExit extends AbstractMenuItem {
    
    public MenuItemExit(){
        super("Exit");
    }
    @Override
    public void applyActionOnGrid(BorderPane mainPane) {
     System.exit(0);
     
    }
    
}
