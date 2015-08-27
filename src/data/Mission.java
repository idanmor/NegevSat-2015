package data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;

@DatabaseTable(tableName="Mission")
public class Mission {
	public static final String DATE_FIELD_NAME = "creationTimestamp";
    @DatabaseField(id = true ,columnName = DATE_FIELD_NAME)
    private Timestamp creationTimestamp;
    @DatabaseField
    private Timestamp missionExecutionTS;
    @DatabaseField
    private Command command;
    @DatabaseField
    private int priority;
    @DatabaseField
    private Timestamp sentTime;
    
    
    public Mission(){}
    
    public Mission(Timestamp _missionExecutionTS, Command _command, int _priority) {
        java.util.Date date= new java.util.Date();
        Timestamp t=new Timestamp(date.getTime());
        this.creationTimestamp=t;
        this.missionExecutionTS=_missionExecutionTS;
        this.command=_command;
        this.priority=_priority;
        this.sentTime=null;
	}
    
    public Timestamp getMissionExecutionTS(){
    	return this.missionExecutionTS;
    }

    public Command getCommand(){
    	return this.command;
    }
    public int getPriority(){
    	return this.priority;
    }
          
    public Timestamp getExecutionTime () {
    	return this.missionExecutionTS;
    }
    
    public Timestamp getSentTime () {
    	return this.sentTime;
    }

    public Timestamp getCreationTimestamp(){
    	return this.creationTimestamp;
    }
    
    public void setMissionExecutionTS(Timestamp _missionExecutionTS){
    	this.missionExecutionTS=_missionExecutionTS;
    }
    
    public void setCommand(Command _command){
    	this.command=_command;
    }
    
    public void setPriority(int _priority){
    	this.priority=_priority;
    }
    
    public void setSentTime(Timestamp _sentTime){
    	this.sentTime=_sentTime;
    }
    public String getDescription(){
    	return this.command.getDescription();
    }
}
