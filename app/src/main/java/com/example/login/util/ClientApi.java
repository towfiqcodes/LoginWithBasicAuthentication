package com.example.login.util;

import com.example.login.model.User;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ClientApi {
    public interface LoginService {
        @POST("login")
        Call<User>basicLogin();
    }
}
