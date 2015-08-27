package orbit;

import java.util.Date;

public class Pass {
	private Date startTime;
	private Date endTime;
	
	public Pass (Date start, Date end) {
		this.startTime = start;
		this.endTime = end;
	}
	
	public long getStartTime () {
		return startTime.getTime();
	}
	
	public long getEndTime () {
		return endTime.getTime();
	}
	
	public boolean isInPassPhase () {
		Date now = new Date();
		if (now.getTime() >= this.getStartTime() 
				&& now.getTime() < this.getEndTime()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isOutOfDate () {
		Date now = new Date();
		if (now.getTime() >= this.getEndTime()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Get the time remaining to the Pass
	 * @return milliseconds to this pass from now, 0 if the Pass already started
	 */
	public long getTimeToPassStart () {
		Date now = new Date();
		long toPass = this.getStartTime() - now.getTime();
		if (toPass <= 0) {
			return 0;
		}
		else {
			return toPass;
		}
	}
	
	/**
	 * Get the time remaining to the Pass end
	 * @return milliseconds to this pass from now, 0 if not in this pass phase
	 */
	public long getTimeToPassEnd () {
		Date now = new Date();
		if (this.isInPassPhase()) {
			return this.getEndTime() - now.getTime();
		}
		else {
			return 0;
		}
	}
}
