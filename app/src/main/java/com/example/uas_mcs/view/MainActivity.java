package com.example.uas_mcs.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.uas_mcs.R;
import com.example.uas_mcs.adapter.MainAdapter;
import com.example.uas_mcs.model.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.android.volley.RequestQueue;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {


    public static Vector<Item> items = new Vector<>();
    BottomNavigationView bottonNav;
    MainFragment mainFragment = new MainFragment();
    MainAdapter adapterMain;
    NotificationFragment notificationFragment = new NotificationFragment();
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo = findViewById(R.id.logo);
        getSupportActionBar().hide();
        bottonNav = (BottomNavigationView) findViewById(R.id.nav);
        adapterMain = new MainAdapter(this);
        fetchData();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Token Failed", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        String token = task.getResult();
                        System.out.println(token);
                        Log.d("firebase token: ", token);
                    }
                });

        getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();
        bottonNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.mainFragment:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();
                    return true;

                case R.id.notificationFragment:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, notificationFragment).commit();
                    return true;
            }
            return false;
        });
    }

    void fetchData(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://jsonplaceholder.typicode.com/posts";


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response .Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i=0; i<response.length(); i++){

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int userId, id;
                        String title, body;

                        userId = jsonObject.getInt("userId");
                        id = jsonObject.getInt("id");
                        title = jsonObject.getString("title");
                        body = jsonObject.getString("body");

                        items.add(new Item(userId, id, title, body));

                        adapterMain.setItems(items);
                        adapterMain.notifyDataSetChanged();

                    }catch(Exception E){

                    }
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", "onErrorResponse: "+error.toString());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);

    }
}

