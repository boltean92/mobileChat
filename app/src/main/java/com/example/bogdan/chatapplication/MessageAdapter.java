package com.example.bogdan.chatapplication;

/**
 * Created by Bogdan on 1/16/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> mMessages;

    public MessageAdapter(Context context, List<Message> messages) {
        mMessages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;

        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.layout_message, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Message message = mMessages.get(position);
        viewHolder.setMessage(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMessages.get(position).getType();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mMessageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mMessageView = (TextView) itemView.findViewById(R.id.message);
        }

        public void setMessage(String message) {
            if (null == mMessageView) return;
            mMessageView.setText(message);
        }

    }
}