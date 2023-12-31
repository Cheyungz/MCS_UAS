package com.example.uas_mcs.view;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.uas_mcs.helper.DBHelper;
import com.example.uas_mcs.R;
import com.example.uas_mcs.adapter.NotificationAdapter;
import com.example.uas_mcs.model.Notification;

import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView mainRV;
    NotificationAdapter adapterNotif;
    Vector<Notification> notifications = new Vector<>();
    DBHelper dbOpenHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbOpenHelper = new DBHelper(getContext());

        mainRV = (RecyclerView) view.findViewById(R.id.rv_notif);
        mainRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapterNotif = new NotificationAdapter(this.getContext());
        mainRV.setAdapter(adapterNotif);

        getDataFromDB();
    }



    private void getDataFromDB(){
        Cursor cursor = dbOpenHelper.getData();
        notifications.clear();
        if(cursor.getCount() == 0){
            Toast.makeText(getContext(), "no notification", Toast.LENGTH_SHORT).show();
            Log.d("notif fragment", "no notification");
        }
        else {
            while (cursor.moveToNext()){
                notifications.add(new Notification(cursor.getString(0), cursor.getString(1)));
                Log.d("adaaaa", "haha");
                adapterNotif.notifyDataSetChanged();
                adapterNotif.setNotifications(notifications);
            }
        }
    }
}