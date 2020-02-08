package com.assignment.heroapp.interfaces;

import com.assignment.heroapp.models.MainObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HeroApi {

    public static String base_url = "https://stark-spire-93433.herokuapp.com";

    @GET("/json")
    Call<MainObject> getFeed();
}
