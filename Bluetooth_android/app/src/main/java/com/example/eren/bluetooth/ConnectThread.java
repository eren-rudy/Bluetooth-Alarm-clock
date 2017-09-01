package com.example.eren.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;


import java.io.IOException;
import java.util.UUID;

/**
 * Created by Eren on 29/08/2017.
 */

public class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private UUID DEVICE_UUID = null;
    private String NAME = "connectionName";


    public ConnectThread(BluetoothDevice device) {
        // Use a temporary object that is later assigned to mmSocket
        // because mmSocket is final.
        BluetoothSocket tmp = null;
        mmDevice = device;
//        DEVICE_UUID = mmDevice.getUuids()[0].getUuid();
        DEVICE_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            tmp = device.createInsecureRfcommSocketToServiceRecord(DEVICE_UUID);
        } catch (IOException e) {
            Log.e("Eren-ConnectThread", "Socket's create() method failed", e);
        }
        mmSocket = tmp;
        Log.d("Eren-ConnectThread", "Creating temporary socket");
    }

    public void run() {
        // Cancel discovery because it otherwise slows down the connection.
        if(mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            mmSocket.connect();

        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            Log.d("Eren-ConnectThreadRun", "Is connected? " + mmSocket.isConnected());
            try {
                Log.d("Eren-ConnectThreadRun", "Closing the client socket");
                mmSocket.close();
            } catch (IOException closeException) {
                Log.d("Eren-ConnectThreadRun", "Could not close the client socket");
                Log.e("Eren-ConnectThreadRun", "Could not close the client socket", closeException);
            }
            return;
        }

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.

    }

    public ConnectedThread getConnection() {
        Log.d("Eren-getConnection", "Getting connected thread..." + mmSocket.toString());
        ConnectedThread connection = new ConnectedThread(mmSocket);
//        connection.run();
        return connection;


    }

    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        try {
            Log.d("ConnectThreadCancel", "Closing the client socket");
            mmSocket.close();
        } catch (IOException e) {
            Log.e("ConnectThreadCancel", "Could not close the client socket", e);
        }
    }
}
