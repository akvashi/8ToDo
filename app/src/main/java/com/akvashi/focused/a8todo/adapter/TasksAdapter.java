package com.akvashi.focused.a8todo.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.akvashi.focused.a8todo.R;
import com.akvashi.focused.a8todo.mvp.model.FriendlyMessage;
import com.bumptech.glide.Glide;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    Context context;
    List<FriendlyMessage> list;

    public TasksAdapter(Context context, List<FriendlyMessage> list) {
        this.context = context;
        this.list = list;
    }

    public Context getContext() {
        return context;
    }

    protected void populateViewHolder(MessageViewHolder viewHolder, FriendlyMessage friendlyMessage, int position) {
        viewHolder.messageTextView.setText(friendlyMessage.getText());
        viewHolder.messengerTextView.setText(friendlyMessage.getName());
        if (friendlyMessage.getPhotoUrl() == null) {
            viewHolder.messengerImageView
                    .setImageDrawable(ContextCompat
                            .getDrawable(getContext(),
                                    R.drawable.ic_account_circle_black_36dp));
        } else {
            Glide.with(getContext())
                    .load(friendlyMessage.getPhotoUrl())
                    .into(viewHolder.messengerImageView);
        }
    }

    public FriendlyMessage getItem(int position) {
        return list.get(position);
    }

    public void updateList(List<FriendlyMessage> tasks) {
        this.list = tasks;
        notifyDataSetChanged();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        FriendlyMessage model = getItem(position);
        populateViewHolder(holder, model, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        // http://stackoverflow.com/questions/5100071/whats-the-purpose-of-item-ids-in-android-listview-adapter
        return list.get(position).hashCode();
    }
}
