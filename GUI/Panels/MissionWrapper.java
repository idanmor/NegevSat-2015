package Panels;

import java.sql.Timestamp;

import data.Mission;

public interface MissionWrapper {

	/**
	 * Gets the time that this mission was created
	 */
	public abstract Timestamp getCreationTime();

	/**
	 * Gets the time that this mission will be executed
	 * @return
	 */
	public abstract Timestamp getExecutionTS();

	/**
	 * Gets the description of the mission
	 * @return
	 */
	public abstract String getDescription();

	/**
	 * Gets the mission in the node
	 * @return
	 */
	public abstract Mission getMission();

	/**
	 * Checks if the mission was sent already
	 * @return true if the mission was sent
	 */
	public abstract boolean getSent();

	/**
	 * Gets the time this mission was sent
	 * @return TimeStamp of sent time
	 */
	public abstract String getSentTime();

}