package orbit;

import java.util.Date;

public class OrbitManager {
	public static final String tle = 
			"ISS (ZARYA)\n"
			+ "1 25544U 98067A   08264.51782528 -.00002182  00000-0 -11606-4 0  2927\n"
			+ "2 25544  51.6416 247.4627 0006703 130.5360 325.0288 15.72125391563537";

	private static OrbitManager instance = null;
	
	private Pass nextPass;
	private OrbitPropagator propagator;
	
	public static final int REAL_TIME_MODE = 0;
	public static final int ALWAYS_PASS_MODE = 1;
	public static final int MANUAL_PASS_MODE = 2;
	
	private int mode;
	
	private OrbitManager() {
		this.mode = MANUAL_PASS_MODE;
		this.propagator = new SimOrbitPropagator();
		this.nextPass = null;
	}
	
	public static OrbitManager getInstance() {
		if (instance == null)
			instance = new OrbitManager();
		return instance;
	}
	
	private Pass getNextPass() {
		if (nextPass == null || nextPass.isOutOfDate()) {
			nextPass = propagator.getNextPass();
		}
		return nextPass;
	}
	
	public boolean isPassPhase() {
		return this.getNextPass().isInPassPhase();
	}
	
	/**
	 * Get the time remaining to the Pass
	 * @return milliseconds to this pass from now, 0 if the Pass already started
	 */
	public long timeToPassStart() {
		return this.getNextPass().getTimeToPassStart();
	}
	
	/**
	 * Get the time remaining to the Pass end
	 * @return milliseconds to this pass from now, 0 if not in this pass phase
	 */
	public long timeToPassEnd() {
		return this.getNextPass().getTimeToPassEnd();
	}
	
	public int getMode() {
		return mode;
	}
	
	public void setPassPhase () {
		if (mode == OrbitManager.MANUAL_PASS_MODE) {
			Date now = new Date();
			this.nextPass = new Pass(now, new Date(now.getTime() + 600000));
		}
	}
	
	public void setNonPassPhase () {
		if (mode == OrbitManager.MANUAL_PASS_MODE) {
			Date now = new Date();
			this.nextPass = new Pass(new Date(now.getTime() + 300000), new Date(now.getTime() + 900000));
		}
	}
}
