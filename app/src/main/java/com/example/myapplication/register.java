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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    EditText fname, lname, email, password;
    Button registerBth, gotolginBth;
    String url = "https://medicinal-wall.000webhostapp.com/create.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fname = findViewById(R.id.edfname);
        lname = findViewById(R.id.edlname);
        email = findViewById(R.id.edemail);
        password = findViewById(R.id.edpassword);
        registerBth = findViewById(R.id.registerBtn);
        gotolginBth = findViewById(R.id.nologinBtn);
        gotolginBth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotolginBth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(register.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        registerBth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(
                        fname.getText().toString(),
                        lname.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString()
                );
            }
        });
    }

    public void register(String myfname, String mylname, String myemail, String mypassword) {
        StringRequest streq = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("inserted")) {
                            Toast.makeText(register.this, "You are register now please Login to continue..", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(register.this, MainActivity.class);
                            startActivity(intent);
                        } else{
                            Toast.makeText(register.this, response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(register.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> mp = new HashMap<>();

                mp.put("fname", myfname);
                mp.put("lname", mylname);
                mp.put("email", myemail);
                mp.put("password", mypassword);
                return  mp;
            }
        };
        RequestQueue reque = Volley.newRequestQueue(register.this);
        reque.add(streq);
    }
}