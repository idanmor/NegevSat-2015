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
public class StatisticsTemperatureFilter extends StatisticsComboFiltersStrat{

    public StatisticsTemperatureFilter() {
        super("Temperature");
    }

    @Override
    public void filterSelected(TableView table) {
       // FilteredList list = table.getColumns().filtered(Predicate.isEqual(name));
        table.setItems(table.getItems().filtered(row -> row.equals(name)));
    }
    
}
