/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Factories;

import MenuItems.AbstractMenuItem;
import java.util.Collection;
import javafx.scene.control.Menu;

/**
 *
 * @author Max
 */
public interface MenuFactory{
    
    public Menu createMenu(String name);
    public Menu createExpandMenu(String name, Collection <AbstractMenuItem> menuItems);
  /**
   * Creates a ViewMenu for the menu panel
   * @return
   */
    public Menu createViewMenu();
    /**
     * Creates a FileMenu for the menu panel
     * @return
     */
    
    public Menu createFileMenu();
    /**
     * Creates a toolsMenu for the menu panel
     * @return
     */
    
	public Menu createToolsMenu();
}
