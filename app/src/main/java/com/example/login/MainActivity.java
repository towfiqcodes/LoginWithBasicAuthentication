package com.example.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.model.User;
import com.example.login.util.ClientApi;
import com.example.login.util.ServiceGenerator;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText emailEt, passwordET;
    private CardView loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        emailEt = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        loginBtn = findViewById(R.id.cardView);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }

    private void userLogin() {
        String email = emailEt.getText().toString();
        String password = passwordET.getText().toString();
        if (email.isEmpty()) {
            emailEt.setError("Email is required");
            emailEt.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEt.setError("Enter a valid email");
            emailEt.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordET.setError("Password required");
            passwordET.requestFocus();
            return;
        }


        ClientApi.LoginService loginService =
                ServiceGenerator.createService(ClientApi.LoginService.class,email,password);
        Call<User> call = loginService.basicLogin();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user=response.body();

                if (response.isSuccessful()) {
                    Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                    startActivity(intent);


                } else {
                    Toasty.error(MainActivity.this,"error",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
            }


        });
    }

}



