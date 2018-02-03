package com.bild4.mror;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFrame;


public class Connect extends Activity {
    private Boolean finish = false;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        handler = new Handler(Looper.getMainLooper());
        String message = getIntent().getStringExtra("message");
        if (message != null) {
            ((TextView) findViewById(R.id.errorMessage)).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.errorMessage)).setText(message);
        }

    }

    public void connect(View view) {
        String pass = ((EditText) findViewById(R.id.editText)).getText().toString();
        Log.d("connect", pass);
        findViewById(R.id.spinner).setVisibility(View.VISIBLE);
        findViewById(R.id.editText).setVisibility(View.INVISIBLE);
        findViewById(R.id.button).setVisibility(View.INVISIBLE);
        Comm.connect(pass);
        ConnectTask ct = new ConnectTask();
        ct.pass = pass;
        ct.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class ConnectTask extends AsyncTask<Void, Void, Boolean> {
        public String pass;

        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(Void... pass) {
            long current = System.currentTimeMillis();
            while (!Comm.isConnected()) {
                if (current + 25000 < System.currentTimeMillis())
                    return false;

            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                findViewById(R.id.spinner).setVisibility(View.GONE);
                findViewById(R.id.errorMessage).setVisibility(View.VISIBLE);

//                ((TextView) findViewById(R.id.errorMessage)).setText("connected");

                Comm.send("AUTH$cisco");
                finish = true;
                Log.d("random", "handler");
                Comm.getSocket().addListener(new WebSocketAdapter() {
                    @Override
                    public void onTextMessage(WebSocket websocket, String message) throws Exception {
                        Log.d("recieved", message);
                        if (message.startsWith("AUTH$")&&message.endsWith("$SUCCESS"))
                            handler.post(new Runnable() {
                                public void run() {
                                    Log.d("other random", "handler");
                                    ((TextView) findViewById(R.id.errorMessage)).setText("");
                                    Intent intent = new Intent(Connect.this, MainActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    finish();
                                }
                            });
                        else if(message.startsWith("AUTH$")&&message.endsWith("$WRONGPASS"))
                                handler.post(new Runnable() {
                                    public void run() {
                                        Log.d("other random", "handler");
                                        ((TextView) findViewById(R.id.errorMessage)).setText("wrong password");
                                    }
                                });
                        else if(message.startsWith("AUTH$")&&message.endsWith("$FAILED"))
                            handler.post(new Runnable() {
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(), Connect.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra("message", "Disconnected");
                                    startActivity(intent);
                                }
                            });
                    }

                    public void onDisconnected(WebSocket websocket,
                                               WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame,
                                               boolean closedByServer) throws Exception {
                        handler.post(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), Connect.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("message", "Disconnected");
                                startActivity(intent);
                            }
                        });
                    }
                });


            } else {
                findViewById(R.id.errorMessage).setVisibility(View.VISIBLE);
                findViewById(R.id.button).setVisibility(View.VISIBLE);
                findViewById(R.id.editText).setVisibility(View.VISIBLE);
                findViewById(R.id.spinner).setVisibility(View.GONE);
                findViewById(R.id.errorMessage).setVisibility(View.VISIBLE);
                Comm.disconnect();

            }
        }

    }


}
