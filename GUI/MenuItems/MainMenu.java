package MenuItems;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Factories.MenuFactory;
import Factories.DefaultMenuFactoryImpl;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;



/**
 *Represents the main menu bar
 * @author Max
 */
public class MainMenu extends MenuBar  {
     
    public MainMenu(){
        init();
    }
    
    private void init(){
        MenuFactory factory = new DefaultMenuFactoryImpl();
        Menu menuFile = factory.createFileMenu();
        Menu menuView = factory.createViewMenu();
        Menu menuTools = factory.createToolsMenu();
        this.getMenus().addAll(menuFile,menuView,menuTools);
        
    }
    
}
