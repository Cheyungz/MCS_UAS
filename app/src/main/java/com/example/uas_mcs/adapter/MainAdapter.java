package com.example.uas_mcs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uas_mcs.model.Item;
import com.example.uas_mcs.R;

import java.util.Vector;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private Context context;
    public static Vector<Item> items = new Vector<>();

    public MainAdapter(Context context) {
        this.context = context;
    }

    public static void setItems(Vector<Item> newData) {
        items.clear();
        items.addAll(newData);
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        holder.userId.setText("User ID " + items.get(position).getUserId());
        holder.id.setText("ID " + items.get(position).getId());
        holder.title.setText(items.get(position).getTitle());
        holder.body.setText(items.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView userId, id, title, body;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.uid);
            id = itemView.findViewById(R.id.id);
            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
        }
    }
}
