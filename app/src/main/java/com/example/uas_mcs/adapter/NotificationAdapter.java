package com.example.uas_mcs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uas_mcs.model.Notification;
import com.example.uas_mcs.R;

import java.util.Vector;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    private Context context;

    public NotificationAdapter(Context context) {
        this.context = context;
    }
    public static Vector<Notification> notifications = new Vector<>();

    public void setNotifications(Vector<Notification> newNotifications) {
        notifications = newNotifications;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        holder.title.setText(String.valueOf(notifications.get(position).getTitle()));
        holder.body.setText(String.valueOf(notifications.get(position).getBody()));
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, body;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
        }
    }
}
