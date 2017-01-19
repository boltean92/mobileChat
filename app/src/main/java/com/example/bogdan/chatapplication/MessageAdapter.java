package com.example.bogdan.chatapplication;

/**
 * Created by Bogdan on 1/16/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class MessageAdapter extends BaseAdapter {

    private List<Message> messageList;
    Context messageContext;
    private boolean isSelf;

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
        MessageViewHolder holder;
        if (view == null) {
            LayoutInflater messageInflater = (LayoutInflater) messageContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            view = messageInflater.inflate(R.layout.message_layout, null);
            holder = new MessageViewHolder();
            holder.senderView = (TextView) view.findViewById(R.id.message_sender);
            holder.bodyView = (TextView) view.findViewById(R.id.message_body);

            view.setTag(holder);
        } else {
            holder = (MessageViewHolder) view.getTag();
        }

        Message message = (Message) getItem(index);
        holder.bodyView.setText(message.text);
        holder.senderView.setText(message.user);


        return view;
    }

    public void isSelf(boolean isSelf) {
        this.isSelf = isSelf;
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