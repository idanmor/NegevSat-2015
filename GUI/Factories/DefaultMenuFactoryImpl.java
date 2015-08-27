/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Factories;

import MenuItems.MenuItemCommand;
import MenuItems.MenuItemExit;
import MenuItems.MenuItemHome;
import MenuItems.MenuItemSatteliteStatus;
import MenuItems.MenuItemUnsentMissions;
import MenuItems.AbstractMenuItem;
import MenuItems.MenuItemTableTemperature;
import MenuItems.MenuItemTableEnergy;
import MenuItems.MenuItemsAllMissions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.geometry.Orientation;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;

/**
 *This class represents the factory for Main Menu
 * For adding new  items for a menu add an abstractMenuItem to a corresponding menu for instance to View menu add to createViewMenu function 
 * @author Max
 */
public class DefaultMenuFactoryImpl implements MenuFactory{

    @Override
    public Menu createMenu(String name) {
        return new Menu(name);
      }

    /**
     * Use this function to create an expand menu
     * @param name - the name of the expand menu
     * @param menuItems - The Items that the expand menu will expand to
     */
    @Override
    public Menu createExpandMenu(String name, Collection<AbstractMenuItem> menuItems) {
        Menu menuEffect = new Menu(name);
        for (MenuItem childMenu : menuItems) {
            menuEffect.getItems().add(childMenu);
         }
        return menuEffect;
    }

    @Override
    public Menu createViewMenu() {
         // --- Menu View
        Menu menuView = new Menu("View");
        List<AbstractMenuItem> listOfMenuItems = new ArrayList<>();
        listOfMenuItems.add(new MenuItemCommand());
        listOfMenuItems.add(new MenuItemSatteliteStatus());
        Menu menuSceens = this.createExpandMenu("Screens", listOfMenuItems);
        List<AbstractMenuItem> listOfTablesItems = new ArrayList<>();
        listOfTablesItems.add(new MenuItemTableTemperature());
        listOfTablesItems.add(new MenuItemTableEnergy());
        Menu tableScreens = this.createExpandMenu("View Tables", listOfTablesItems);
        menuView.getItems().addAll(menuSceens,tableScreens, new MenuItemUnsentMissions(), new MenuItemsAllMissions());
        return menuView;
    }

    @Override
    public Menu createFileMenu() {
     Menu menuFile = this.createMenu("File");
     menuFile.getItems().addAll(new MenuItemHome(), new MenuItemExit());   
     return menuFile;
    }

	@Override
	public Menu createToolsMenu() {
		Menu tools = this.createMenu("Tools");
		return tools;
	}
    
	public Separator getSeparator(){
		return new Separator(Orientation.VERTICAL);
	}
    
    
}
