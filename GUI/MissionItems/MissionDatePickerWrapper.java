/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MissionItems;

import java.time.LocalDate;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;



/**
 *
 * @author Max
 */
public class MissionDatePickerWrapper extends DatePicker implements MissionItemWrapper {

    
    public MissionDatePickerWrapper(LocalDate ld){
        super(ld);
    }
    @Override
    public String getMissionValue() {
        return this.getEditor().getText();
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
        return prefix.toString().trim() + " " + getMissionValue();
    }
	@Override
	public void clearSelection() {
		this.getEditor().clear();
	}
    
}
