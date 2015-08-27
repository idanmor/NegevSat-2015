/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package StatisticsItems;

import javafx.scene.control.TableView;

/**
 *
 * @author Max
 */
public abstract class StatisticsComboFiltersStrat {
    String name;
    
    public StatisticsComboFiltersStrat(String name){
        this.name = name;
    }
    public abstract void filterSelected(TableView table);
    
    @Override
    public String toString(){
        return name;
    }
}
