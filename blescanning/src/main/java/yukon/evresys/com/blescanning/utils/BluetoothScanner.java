package yukon.evresys.com.blescanning.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.provider.Settings;
import android.util.Log;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.Region;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.EasyPermissions;
import yukon.evresys.com.blescanning.events.RangeBeacon;
import yukon.evresys.com.blescanning.events.RxBus;

/**
 * Created by Paul on 05.04.17. for project Evresys
 */

public class BluetoothScanner {
    private String TAG = "BluetoothScanner";

    private BeaconManager _beaconManager;
    private Context _context;
    private RxBus _rxBus;
    private EvresysBeaconManager evresysBeaconManager;

    @Inject
    public BluetoothScanner(BeaconManager _beaconManager, Context _context, RxBus _rxBus) {
        this._beaconManager = _beaconManager;
        this._context = _context;
        this._rxBus = _rxBus;
    }

    public void enableScan() {
        evresysBeaconManager = new EvresysBeaconManager();
    }

    public void unbindScanning() {
        evresysBeaconManager._unbindManager();
    }

    public void bindScanning() {
        evresysBeaconManager._bindManager();
    }



    /**
     * Created by Paul on 18.05.17. for project Evresys
     * Uses for scan beacons and works all the time from injecting BluetoothScanner.
     */
    private class EvresysBeaconManager implements BeaconConsumer {

        private EvresysBeaconManager() {
            _bindManager();
        }

        /**
         * This method is uses for creating scan service and find beacons in the devise range.
         * Refer {@link MainPresenter#getNewBeacons(Beacon beacon) getNewBeacons} to get beacons.
         */
        @Override
        public void onBeaconServiceConnect() {
            try {
                _beaconManager.addRangeNotifier((beacons, region) ->
                        _rxBus.send(new RangeBeacon(beacons)));
                _beaconManager.startRangingBeaconsInRegion(new Region("Evresys_Range",
                        null, null, null));
            } catch (Exception e) {
                _rxBus.sendError(e);
            }
        }

        @Override
        public Context getApplicationContext() {
            return _context;
        }

        /**
         * Enable beacon searching, unbind if it has been started and enabled it again.
         * Used in API >= 23, if user reject the permission he will get popup again :)
         */
        private void _bindManager() {
            if (EasyPermissions.hasPermissions(_context,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                _unbindManager();
                _beaconManager.bind(this);
            } else {
                _bindManager();
            }
        }

        /**
         * Disable BT scanning
         */
        private void _unbindManager() {
            _beaconManager.unbind(this);
            _beaconManager.removeAllRangeNotifiers();
        }

        @Override
        public void unbindService(ServiceConnection serviceConnection) {
            _context.unbindService(serviceConnection);
        }

        @Override
        public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
            return _context.bindService(intent, serviceConnection, i);
        }

    }

    public void setBackgroundScanPeriod(Long period) {
        _beaconManager.setBackgroundScanPeriod(period + 100L);
    }

    public void setForegroundScanPeriod(Long period) {
        _beaconManager.setForegroundScanPeriod(period + 100L);
    }

    public void setForegroundBetweenScanPeriod(Long period) {
        _beaconManager.setBackgroundBetweenScanPeriod(period);
    }

    public void setBackgroundBetweenScanPeriod(Long period) {
        _beaconManager.setForegroundBetweenScanPeriod(period);
    }
}