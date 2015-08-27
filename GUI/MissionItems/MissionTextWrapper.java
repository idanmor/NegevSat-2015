/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MissionItems;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Max
 */
public class MissionTextWrapper extends TextField implements MissionItemWrapper{

    @Override
    public String getMissionValue() {
        return this.getText();
    }
    
    public MissionTextWrapper(String text){
        super(text);
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
		this.clear();
		
	}
    
}
