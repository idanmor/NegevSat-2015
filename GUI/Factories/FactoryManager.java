/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Factories;

/**
 *
 * @author Max
 */
public class FactoryManager {
    MenuFactory menuFactory;

  
      
    public FactoryManager(){
        menuFactory =  new DefaultMenuFactoryImpl();      
    }
    
    public MenuFactory getMenuFactory(){
        return menuFactory;
    }
    
}
