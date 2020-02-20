package com.ayeshapp.imageeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private Button register;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        firstName = findViewById(R.id.reg_firstname);
        lastName = findViewById(R.id.reg_lastname);
        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.reg_password);
        register = findViewById(R.id.reg_btn);

        final OkHttpClient client = new OkHttpClient();

        final String url = "http://10.0.2.2:3000/users/register";

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = firstName.getText().toString().trim();
                String lname = lastName.getText().toString().trim();
                String emaill = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if(fname.equals("") || lname.equals("") || emaill.equals("") || pass.equals(""))
                {
                    Toast.makeText(RegisterActivity.this,"Please fill up all the fields",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("first_name",fname);
                        jsonObject.put("last_name",lname);
                        jsonObject.put("user_email",emaill);
                        jsonObject.put("user_password",password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());

                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if(response.isSuccessful())
                            {
                                final String myResponse = response.body().string();
                                RegisterActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("register",myResponse);
                                        Intent intent = new Intent(RegisterActivity.this,ProfileActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }
}
