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


    /**
     * UUID identified with this app - set as Service UUID for BLE Advertisements.
     *
     * Bluetooth requires a certain format for UUIDs associated with Services.
     * The official specification can be found here:
     * @link https://www.bluetooth.org/en-us/specification/assigned-numbers/service-discovery
     */
    //public static final ParcelUuid SERVICE_UUID = ParcelUuid.fromString(UUID_STR);
    //private static final String UUID_STR = "0000b81d-0000-1000-8000-00805f9b34fb";


    /*
    public static final UUID HEART_RATE_MEASUREMENT_UUID = UUID.fromString("00002A37-0000-1000-8000-00805f9b34fb");
    public static final int HEART_RATE_MEASUREMENT_VALUE_FORMAT = BluetoothGattCharacteristic.FORMAT_UINT8;
    public static final UUID CHARACTERISTIC_USER_DESCRIPTION_UUID = UUID.fromString("00002901-0000-1000-8000-00805f9b34fb");
    public static final UUID CLIENT_CHARACTERISTIC_CONFIGURATION_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    */

    public static final UUID HEART_RATE_SERVICE_UUID = UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb");
    public static final UUID BODY_SENSOR_LOCATION_CHARACTERISTIC_UUID = UUID.fromString("00002A38-0000-1000-8000-00805f9b34fb");


    // from LEGatt
    /*
    public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    */



    private static UUID convertFromInteger(int i) {
        final long MSB = 0x0000000000001000L;
        final long LSB = 0x800000805f9b34fbL;
        long value = i & 0xFFFFFFFF;
        return new UUID(MSB | (value << 32), LSB);
    }
}
