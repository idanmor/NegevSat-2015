package MissionItems;

import javafx.scene.Parent;
import javafx.scene.control.Label;

public class TimeTextFieldWrapper extends TimeTextField implements MissionItemWrapper {

	@Override
    public String getMissionValue() {
        return this.getText();
    }
    
    public TimeTextFieldWrapper(String text){
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
    
    public TimeTextFieldWrapper clone(){
    	return new TimeTextFieldWrapper(this.getText());
    }

	@Override
	public void clearSelection() {
		this.clear();
		
	}
}
