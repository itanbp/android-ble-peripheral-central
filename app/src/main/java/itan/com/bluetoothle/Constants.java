package itan.com.bluetoothle;

import android.bluetooth.BluetoothGattCharacteristic;
import android.os.ParcelUuid;

import java.util.UUID;

/**
 * Created by itanbarpeled on 28/01/2018.
 */

public class Constants {


    public static final int SERVER_MSG_FIRST_STATE = 1;
    public static final int SERVER_MSG_SECOND_STATE = 2;

    /*
    TODO bluetooth
    better to use different Bluetooth Service,
    instead of Heart Rate Service:
    https://www.bluetooth.com/specifications/gatt/viewer?attributeXmlFile=org.bluetooth.service.heart_rate.xml.

    maybe Object Transfer Service is more suitable:
    https://www.bluetooth.com/specifications/gatt/viewer?attributeXmlFile=org.bluetooth.service.object_transfer.xml
     */
    public static final UUID HEART_RATE_SERVICE_UUID = UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb");
    public static final UUID BODY_SENSOR_LOCATION_CHARACTERISTIC_UUID = UUID.fromString("00002A38-0000-1000-8000-00805f9b34fb");




    private static UUID convertFromInteger(int i) {
        final long MSB = 0x0000000000001000L;
        final long LSB = 0x800000805f9b34fbL;
        long value = i & 0xFFFFFFFF;
        return new UUID(MSB | (value << 32), LSB);
    }
}
