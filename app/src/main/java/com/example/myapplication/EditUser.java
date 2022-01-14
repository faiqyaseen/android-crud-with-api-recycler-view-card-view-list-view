package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditUser extends AppCompatActivity {
EditText fname, lname, email;
Button updateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        fname = findViewById(R.id.upfname);
        lname = findViewById(R.id.uplname);
        email = findViewById(R.id.upemail);
        updateBtn = findViewById(R.id.updateBtn);
        Intent intent = getIntent();
        String myid = intent.getStringExtra("id");
        String myfname = intent.getStringExtra("fname");
        String mylname = intent.getStringExtra("lname");
        String myemail = intent.getStringExtra("email");
        fname.setText(myfname);
        lname.setText(mylname);
        email.setText(myemail);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(myid, fname.getText().toString(), lname.getText().toString(), email.getText().toString());
            }
        });
    }

    public void update(String myid, String myfname, String mylname, String mmyemail) {
        String url = "https://medicinal-wall.000webhostapp.com/update.php";
        StringRequest streq = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("updated")) {
                            Toast.makeText(EditUser.this, "Record updated successfully.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(EditUser.this, FetchUser.class);
                            startActivity(intent);
                        } else{
                            Toast.makeText(EditUser.this, response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditUser.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> mp = new HashMap<>();

                mp.put("id", myid);
                mp.put("fname", myfname);
                mp.put("lname", mylname);
                mp.put("email", mmyemail);
                return  mp;
            }
        };
        RequestQueue reque = Volley.newRequestQueue(EditUser.this);
        reque.add(streq);
    }
}