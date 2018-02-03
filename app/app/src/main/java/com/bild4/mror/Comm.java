package com.bild4.mror;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.neovisionaries.ws.client.OpeningHandshakeException;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

public class Comm {
    private static Comm instance = null;

    private static final String TAG = Comm.class.getSimpleName();

    static DatagramSocket mDataGramSocket;
    static private boolean connected = false;
    static private String pass;
    static private String host;
    static WebSocket ws;
    static WebSocketFactory factory = new WebSocketFactory();

    protected Comm() {
    }

    public static Comm getInstance() {

        return instance;
    }



    public static void connect(String pass) {
        if (instance == null) {
            instance = new Comm();
        }
        Comm.pass = pass;
        try {
            mDataGramSocket = new DatagramSocket(1984);
            mDataGramSocket.setReuseAddress(true);
            mDataGramSocket.setSoTimeout(1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        new findIPTask().execute();
    }


    private static class findIPTask extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... urls) {

            String text = "";
            byte[] message = new byte[1500];
            DatagramPacket p = new DatagramPacket(message, message.length);

            long current = System.currentTimeMillis();
            while (current + 20000 > System.currentTimeMillis()) {
                try {
                    try {
                        mDataGramSocket.receive(p);
                        text = p.getAddress().toString();
                        mDataGramSocket.close();
                        break;
                    } catch (SocketTimeoutException | NullPointerException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return text;
        }

        protected void onPostExecute(String result) {
//            connect(context, result, 1984);
            if (!result.equals("")) {
                result = result.substring(1);
                Log.d("findiptask", result);
                host = result;
                new ConnectTask().execute(result);
            }
        }
    }


    private static class ConnectTask extends AsyncTask<String, Void, Boolean> {

//        private Context context;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Boolean doInBackground(String... params) {

            try {
                ws = factory.createSocket("ws://" + params[0] + ":1880/ws/mror", 5000);
                try {
                    ws.connect();
                    return true;
                } catch (OpeningHandshakeException e) {
                } catch (WebSocketException e) {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Log.d("connectTask", "post");
            if (result) {
                Log.d("connectTask", "success");
                connected = true;
            }
            super.onPostExecute(result);
        }
    }

    public static void disconnect() {
        if (connected) {
            ws.disconnect();
            connected = false;
        }
    }


    public static boolean send(String command) {
        Log.d("send something", "");
        if (connected) {
            ws.sendText(command);
        }
        return false;
    }

    public static WebSocket getSocket() {
        return ws;
    }


    public static boolean isConnected() {
        return connected;
    }

}