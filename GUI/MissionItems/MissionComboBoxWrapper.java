/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MissionItems;

import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 *
 * @author Max
 * @param <T>
 */
public class MissionComboBoxWrapper<T> extends ComboBox<T>implements MissionItemWrapper{

    
    
    public MissionComboBoxWrapper(ObservableList<T> lst){
        super(lst);
    }
    @Override
    public String getMissionValue() {
    	Object selectedItem = this.getSelectionModel().getSelectedItem();
    	if(selectedItem == null){
    		return null;
    	}
        return selectedItem.toString();
    }

    @Override
    public Parent getWrappedItem() {
        return this;
    }
    
      @Override
    public String getValueStringWithPreIfNeeded(Label prefix) {
        if(prefix == null){
            return getMissionValue();
        }
        return prefix.getText().trim() + " " + getMissionValue();
    }
	@Override
	public void clearSelection() {
		this.getSelectionModel().clearSelection();
		
	}
}
