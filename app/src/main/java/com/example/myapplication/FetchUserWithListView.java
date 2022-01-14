package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchUserWithListView extends AppCompatActivity {
    ListView listView;
    List<UserList> userList;
    String url = "https://medicinal-wall.000webhostapp.com/fetch.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_user_with_list_view);

        userList = new ArrayList<>();
        listView = findViewById(R.id.user_list_view);
        getUsers();
    }

    public void getUsers() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("all");

                            for (int i = 0; i<jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                UserList userList1 = new UserList(
                                        jsonObject1.getString("id"),
                                        jsonObject1.getString("fname"),
                                        jsonObject1.getString("lname"),
                                        jsonObject1.getString("email")

                                );
                                userList.add(userList1);
                            }
                            ListViewCustomAdapter adapter = new ListViewCustomAdapter(
                                    userList,
                                    FetchUserWithListView.this
                            );
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FetchUserWithListView.this, error.toString(), Toast.LENGTH_LONG);
                    }
                }

        );
        RequestQueue requestQueue = Volley.newRequestQueue(FetchUserWithListView.this);
        requestQueue.add(stringRequest);
    }
}