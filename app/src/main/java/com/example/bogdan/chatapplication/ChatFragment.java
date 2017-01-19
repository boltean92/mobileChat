package com.example.bogdan.chatapplication;

/**
 * Created by Bogdan on 1/16/2017.
 */


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatFragment extends Fragment {

    private EditText mInputMessageView;
    private RecyclerView mMessagesView;
    private List<Message> mMessages = new ArrayList<Message>();
    private RecyclerView.Adapter mAdapter;

    private Socket socket;

    {
        try {
            socket = IO.socket("http://192.168.0.104:3000");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public ChatFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        socket.connect();
        socket.on("message", handleIncomingMessages);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAdapter = new MessageAdapter(context, mMessages);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMessagesView = (RecyclerView) view.findViewById(R.id.messages);
        mMessagesView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessagesView.setAdapter(mAdapter);

        ImageButton sendButton = (ImageButton) view.findViewById(R.id.send_button);
        mInputMessageView = (EditText) view.findViewById(R.id.message_input);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });


    }

    private void sendMessage() {
        String message = mInputMessageView.getText().toString().trim();
        mInputMessageView.setText("");
        //addMessage(message);
        JSONObject sendText = new JSONObject();
        try {
            sendText.put("text", message);
            sendText.put("id", socket.id());
            socket.emit("message", sendText);
        } catch (JSONException e) {

        }

    }

    private void addMessage(String message) {

        mMessages.add(new Message.Builder(Message.TYPE_MESSAGE)
                .message(message).build());
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();
    }

    private void scrollToBottom() {
        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
    }


    private Emitter.Listener handleIncomingMessages = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    JSONObject data = (JSONObject) args[0];
                    String message = "test";
                    try {
                        message = data.getString("id") + ": " + data.getString("text");

                    } catch (JSONException e) {
                        return;
                    }
                    addMessage(message);
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