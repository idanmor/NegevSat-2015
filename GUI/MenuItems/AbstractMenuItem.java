/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MenuItems;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import negevsatgui.MainWindow;

/**
 *
 * @author Max
 */
public abstract class AbstractMenuItem extends MenuItem{ 
   
    
    public AbstractMenuItem(String name){
       super(name);
      this.setOnAction(new EventHandler<ActionEvent>() {
          @Override
            public void handle(ActionEvent t) {
                applyActionOnGrid(MainWindow.getMainWindow().getMainPane());
            }
        });      
    }
    
    public abstract void applyActionOnGrid(BorderPane mainPane);
    
    /**
     * Clears the selected Pane from its components
     * @param mainPane 
     */
    public void cleanGrid(GridPane mainPane){
        mainPane.getChildren().clear();
    }
}
