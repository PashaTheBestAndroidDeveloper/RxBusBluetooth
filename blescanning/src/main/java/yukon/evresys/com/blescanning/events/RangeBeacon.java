package yukon.evresys.com.blescanning.events;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Region;

import java.util.Collection;

/**
 * Created by Paul on 18.05.17.
 */

public class RangeBeacon {

    private Collection<Beacon> beacons;

    public RangeBeacon(Collection<Beacon> beacons) {
        this.beacons = beacons;
    }

    public Collection<Beacon> getBeacons() {
        return beacons;
    }

}
