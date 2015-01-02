package com.example.socketclient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;



public class MainActivity extends ActionBarActivity {

    TextView txtResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtResponse = (TextView) findViewById(R.id.txtResponse);

    }

    Thread checkForMessagesThread;
    static boolean go;

    private final Handler handler = new Handler();
    int x = 0;
    @Override
    protected void onResume() {
        super.onResume();
        go = true;
        checkForMessagesThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (go) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //write here code that checks for message from the server
                    final String message = "what's up dog?" + (x++);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtResponse.setText(message);
                        }
                    });
                }
            }
        });
        checkForMessagesThread.start();
        ;

    }

    @Override
    protected void onPause() {
        super.onPause();
        go = false;
        checkForMessagesThread = null;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnSend(View view) {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                //String response="";
                OutputStream outputStream = null;
                InputStream inputStream = null;
                try {
                    Socket socket = new Socket("10.0.25.8", 10000);
                    outputStream = socket.getOutputStream();
                    inputStream = socket.getInputStream();
                    String message = "what's up dog?";
                    byte[] messageBytes = new byte[message.length() + 3];
                    messageBytes[0] = 1;
                    messageBytes[1] = 102;
                    messageBytes[2] = -28;
                    byte[] temp = message.getBytes();
                    for (int i = 3; i < messageBytes.length; i++) {
                        messageBytes[i] = temp[i - 3];
                    }

                    outputStream.write(messageBytes);

                    byte[] bytes = new byte[1024];
                    int actuallyRead;
                    if ((actuallyRead = inputStream.read(bytes)) != -1) {
                        if (actuallyRead == 1 && bytes[0] == 14) {
                            //סימן שההודעה נשלחה בהצלחה

                        }
                    }

/*
                    boolean closed = false;
                    while((actuallyRead=inputStream.read(bytes))!=-1){

                        if(actuallyRead<1024) {
                            outputStream.close();
                            closed = true;
                        }
                    }
                    if(!closed)
                        outputStream.close();
*/
                    outputStream.close();
                    inputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    if (inputStream != null)
                        try {
                            inputStream.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    if (outputStream != null)
                        try {
                            outputStream.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                }
                return "";

            }

            @Override
            protected void onPostExecute(String s) {
                txtResponse.setText(s);
            }
        }.execute();


    }
}
