/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MissionItems;

import javafx.scene.Parent;
import javafx.scene.control.Label;

/**
 *
 * @author Max
 */
public interface MissionItemWrapper {
	
    public String getMissionValue();

    /**
     *
     * @return
     */
    public Parent getWrappedItem();
    
    public String getValueStringWithPreIfNeeded(Label prefix);

	public void clearSelection();
	
	
	
}
