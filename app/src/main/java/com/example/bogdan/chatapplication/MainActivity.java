package com.example.bogdan.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private EditText mInputMessageView;
    private MessageAdapter messageAdapter;

    private Button sendButton;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            socket = IO.socket("http://192.168.1.4:3000");
            socket.connect();
            Log.i("SOCLEEEELT", socket.toString());
            socket.on("message", handleIncomingMessages);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        this.sendButton = (Button) findViewById(R.id.button);
        this.mInputMessageView = (EditText) findViewById(R.id.editText);
        ListView messagesView = (ListView) findViewById(R.id.messages_view);
        this.messageAdapter = new MessageAdapter(this, new ArrayList<Message>());
        messagesView.setAdapter(messageAdapter);
        this.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String message = mInputMessageView.getText().toString().trim();
        mInputMessageView.setText("");
        JSONObject sendText = new JSONObject();
        Intent i = getIntent();
        String userName = i.getStringExtra("userName");
        try {
            sendText.put("text", message);
            sendText.put("user", userName);
            sendText.put("userId", socket.id());
            socket.emit("message", sendText);
        } catch (JSONException e) {

        }

    }


    private Emitter.Listener handleIncomingMessages = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    JSONObject data = (JSONObject) args[0];
                    Log.i("SOCLEEEELT", data.toString());
                    String meesageSocket ="";
                    try {
                         meesageSocket = data.getString("userId");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    boolean isSelf = socket.id().equals(meesageSocket)?true:false;
                    try {
                        data.put("isSelf",isSelf);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("SOCLEEEELT", data.toString());
                    //needs some checks
                    Message message = gson.fromJson(data.toString(), Message.class);
                    Log.i("SOCLEEEELT", message.user + " :"+ message.isSelf);
                    messageAdapter.add(message);
                }
            });
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
