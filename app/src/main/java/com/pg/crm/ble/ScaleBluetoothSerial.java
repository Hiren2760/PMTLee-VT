package com.pg.crm.ble;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Created by Shekhar.
 */


public class ScaleBluetoothSerial {
    private String TAG = "ScaleBluetoothSerial";

    private BluetoothDevice mDevice;
    private OutputStream mOutputStream;
    private InputStream mInputStream;

    private boolean stopWorker;
    private int readBufferPosition;
    private byte[] readBuffer;
    private OnDataReceivedListener mOnDataReceivedListener;
    private Context context;

    /**
     * Interface for listening to data received on the called side
     */
    public interface OnDataReceivedListener {
        void onDataReceived(String data);
    }

    public void setmOnDataReceivedListener(OnDataReceivedListener mOnDataReceivedListener) {
        this.mOnDataReceivedListener = mOnDataReceivedListener;
    }

    public ScaleBluetoothSerial(BluetoothDevice mDevice,Context mContext) {
        this.mDevice = mDevice;
        this.context=mContext;
    }

    public void connect() throws IOException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        BluetoothSocket mSocket = mDevice.createRfcommSocketToServiceRecord(uuid);
        mSocket.connect();

        mOutputStream = mSocket.getOutputStream();
        mInputStream = mSocket.getInputStream();

    }

    public void tare() throws IOException {
        Log.w(TAG, "Taring scale");
        mOutputStream.write("t".getBytes());
        mOutputStream.flush();
    }

    public void listen() {
        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];

        final byte delimiter = 10; //This is the ASCII code for a newline character
       /*

        try {
            // check if there are data available
            int bytesAvailable = mInputStream.available();
//            if (bytesAvailable <= 0) continue;

            byte[] packetBytes = new byte[bytesAvailable];
            mInputStream.read(packetBytes);

            for (int i = 0; i < bytesAvailable; i++) {
                byte b = packetBytes[i];

                if (b != delimiter) {
                    readBuffer[readBufferPosition++] = b;
                }

                // build the string
                else {
                    String receivedData = new String(readBuffer, 0, readBufferPosition, "US-ASCII");
                    readBufferPosition = 0;

                    if (mOnDataReceivedListener != null) {
                        Log.e("ScaleBluetoothSerial","receivedData="+receivedData);
                        mOnDataReceivedListener.onDataReceived(receivedData);
                    }
                }
            }

        } catch (IOException ex) {
            stopWorker = true;
        }*/
        Thread workerThread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        // check if there are data available
                        int bytesAvailable = mInputStream.available();
                        if (bytesAvailable <= 0) continue;

                        byte[] packetBytes = new byte[bytesAvailable];
                        mInputStream.read(packetBytes);

                        for (int i = 0; i < bytesAvailable; i++) {
                            byte b = packetBytes[i];

                            if (b != delimiter) {
                                readBuffer[readBufferPosition++] = b;
                            } else {// build the string
                                String receivedData = new String(readBuffer, 0, readBufferPosition, StandardCharsets.US_ASCII);
                                readBufferPosition = 0;
                                if (mOnDataReceivedListener != null) {
                                    Log.e("ScaleBluetoothSerial","receivedData="+receivedData);
                                    mOnDataReceivedListener.onDataReceived(receivedData);
                                }
                            }
                        }

                    } catch (IOException ex) {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }
//please connect //
}
