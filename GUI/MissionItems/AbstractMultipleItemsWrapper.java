package MissionItems;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class AbstractMultipleItemsWrapper extends HBox implements MissionItemWrapper {
	List<MissionItemWrapper> missionItemWrappers;
	
	public AbstractMultipleItemsWrapper(MissionItemWrapper...missionItemWrappers) {
		super();
		this.missionItemWrappers = new ArrayList<MissionItemWrapper>();
		for(int i = 0; i < missionItemWrappers.length ; i++){
			this.getChildren().add(missionItemWrappers[i].getWrappedItem());
			this.missionItemWrappers.add(missionItemWrappers[i]);
		}
	}

	@Override
	public String getMissionValue() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < missionItemWrappers.size() ; i++){
			sb.append(missionItemWrappers.get(i).getMissionValue()).append(" ");
		}
		return sb.toString().trim();
	}

	@Override
	public Parent getWrappedItem() {	
		return this;
	}

	 public String getValueStringWithPreIfNeeded(Label prefix) {
	        if(prefix == null){
	            return getMissionValue();
	        }
	        return prefix.toString().trim() + " " + getMissionValue();
	  }

	@Override
	public  void clearSelection(){
		return;
	}
	 

}
