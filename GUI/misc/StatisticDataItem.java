/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package misc;

/**
 *
 * @author Max
 */
public class StatisticDataItem implements StatisticDataItemInterface{
    private String date,component,type; 
    private String[][] data;
    
    
    public StatisticDataItem(String date, String component, String type, String[][] data){
        this.date = date;
        this.component = component;
        this.type = type;
        this.data = data;
    }
    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getComponent() {
       return component;
    }

    @Override
    public String getType() {
    return type;
    }

    @Override
    public String[][] getData() {
        return data;
    }
    
    public String getSimpleDate(){
    	if(date.contains(" ")){
    		return date.substring(0, date.indexOf(" "));
    	}
    	return date;
    }
}
