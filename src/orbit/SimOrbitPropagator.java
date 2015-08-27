package orbit;

import java.util.Collection;
import java.util.Date;

public class SimOrbitPropagator implements OrbitPropagator {
	
	public SimOrbitPropagator () {
	}
	
	@Override
	public Pass getNextPass() {
		Date now = new Date();
		if (OrbitManager.getInstance().getMode() == OrbitManager.ALWAYS_PASS_MODE) {
			return new Pass (now, new Date(now.getTime() + 240000));
		}
		else if (OrbitManager.getInstance().getMode() == OrbitManager.MANUAL_PASS_MODE){
			return new Pass (new Date(now.getTime() + 240000), new Date(now.getTime() + 840000));
		}
		else {
			return null;
		}
	}

	@Override
	public Collection<Pass> getPassesBetween(long start, long end) {
		return null;
	}

}
