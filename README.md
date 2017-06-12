# RxBusBluetooth
RxBus Bluetooth library, for finding beacons 
Use include in your settings graddle.
Load dagger2 and use in your app:

@Inject 
BluetoothScanner bluetoothScanner;

bluetoothScanner. enableScan();
