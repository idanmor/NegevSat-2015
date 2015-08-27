/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MissionItems;

import com.sun.org.apache.bcel.internal.generic.TABLESWITCH;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javax.swing.table.TableColumn;

/**
 *
 * @author Max
 */
public class MissionTableWrapper extends TableView implements MissionItemWrapper{

    @Override
    public String getMissionValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Parent getWrappedItem() {
        return this;
    }

    @Override
    public String getValueStringWithPreIfNeeded(Label prefix) {
        StringBuilder text = new StringBuilder();
        String endLine = "\n";
        //Set<Node> headers = this.lookupAll("HeaderTableRow");
        if(prefix != null){
            text.append(prefix.toString()).append(endLine);
        }
        ObservableList columns = this.getColumns();
        for(Object column : columns){
            if(column instanceof TableColumn){
                text.append(((TableColumn)column).getHeaderValue());
            }
        }
        for(int i = 0; i < this.getItems().size() ; i++){
            text.append(getItems().get(i).toString());
            if( i % columns.size() == 0){
                text.append(endLine);
            }
        }
        return text.toString();
    }

	@Override
	public void clearSelection() {
	this.getItems().clear();
		
	}
    
}
