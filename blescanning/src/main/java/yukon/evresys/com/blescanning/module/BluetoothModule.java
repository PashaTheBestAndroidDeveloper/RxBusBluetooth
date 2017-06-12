package yukon.evresys.com.blescanning.module;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Paul on 07.04.17. for project Evresys
 */

@Module
public class BluetoothModule {

    @Provides
    @Singleton
    BluetoothAdapter providesBluetoothAdapter() {
        return BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * @return provide all layout beacons
     */
    @Provides
    @Singleton
    BeaconManager providesBeaconManager(Context ctx) {
        BeaconManager instance = BeaconManager.getInstanceForApplication(ctx);

        // BeaconManager setup
        instance.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:0-3=4c000215,i:4-19,i:20-21,i:22-23,p:24-24"));
        // Detect the main identifier (All) frame:
        instance.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.ALTBEACON_LAYOUT));
        // Detect the main identifier (URI) frame:
        instance.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.URI_BEACON_LAYOUT));
        // Detect the main identifier (UID) frame:
        instance.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
        // Detect the telemetry (TLM) frame:
        instance.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_TLM_LAYOUT));
        // Detect the URL frame:
        instance.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT));

        return instance;
    }


}
