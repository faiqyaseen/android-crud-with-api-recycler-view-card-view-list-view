package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    Context customAdapter;
    List<UserList> list;
    String edfname, edlname, edemail, edid;

    public CustomAdapter(Context customAdapter, List<UserList> list) {
        this.customAdapter = customAdapter;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(customAdapter).inflate(R.layout.mydata, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserList mylist = list.get(position);
        holder.fname.setText(mylist.getFname());
        holder.lname.setText(mylist.getLname());
        holder.email.setText(mylist.getEmail());
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(customAdapter,EditUser.class);
                intent.putExtra("id", mylist.getId());
                intent.putExtra("fname", mylist.getFname());
                intent.putExtra("lname", mylist.getLname());
                intent.putExtra("email", mylist.getEmail());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                customAdapter.startActivity(intent);
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(mylist.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fname, lname, email;
        Button editBtn, deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fname = itemView.findViewById(R.id.tvfname);
            lname = itemView.findViewById(R.id.tvlname);
            email = itemView.findViewById(R.id.tvemail);
            editBtn = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            editBtn.setBackgroundColor(Color.YELLOW);
            editBtn.setTextColor(Color.BLACK);
            deleteBtn.setBackgroundColor(Color.RED);
        }
    }

    public void delete(String myid) {
        String url = "https://medicinal-wall.000webhostapp.com/delete.php";
        StringRequest streq = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("deleted")) {
                            Toast.makeText(customAdapter, "Record has been deleted successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(customAdapter,FetchUser.class);
                            customAdapter.startActivity(intent);
                        } else{
                            Toast.makeText(customAdapter, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(customAdapter, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> mp = new HashMap<>();

                mp.put("id", myid);
                return  mp;
            }
        };
        RequestQueue reque = Volley.newRequestQueue(customAdapter);
        reque.add(streq);
    }
}
