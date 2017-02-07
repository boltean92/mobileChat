package com.example.bogdan.chatapplication;

/**
 * Created by Bogdan on 1/16/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;


public class MessageAdapter extends BaseAdapter {

    private List<Message> messageList;
    private Context messageContext;

    public MessageAdapter(Context context, List<Message> messages) {
        this.messageList = messages;
        this.messageContext = context;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int index) {
        return messageList.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        MessageViewHolder holder = new MessageViewHolder();;
        LayoutInflater messageInflater = (LayoutInflater) messageContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message =  messageList.get(index);

        if (message.isSelf()) {
            Log.i("SOCLEEEELT", "this is self");
            view = messageInflater.inflate(R.layout.message_from_self, null);
        } else {
            view = messageInflater.inflate(R.layout.message_from_other, null);
            Log.i("SOCLEEEELT", "this is other");
        }
        holder.senderView = (TextView) view.findViewById(R.id.lblMsgFrom);
        holder.bodyView = (TextView) view.findViewById(R.id.txtMsg);
        holder.bodyView.setText(message.text);
        holder.senderView.setText(message.user);
        Log.i("SOCLEEEELT", "din adapter: "+ new Date()+ " --->" + message.user + " :"+ message.isSelf);
        return view;
    }


    private static class MessageViewHolder {
        public TextView senderView;
        public TextView bodyView;
    }

    public void add(Message message) {
        messageList.add(message);
        notifyDataSetChanged();
    }
}