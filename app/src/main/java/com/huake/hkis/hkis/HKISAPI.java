package com.huake.hkis.hkis;

import com.huake.hkis.hkis.model.Repo;
import com.huake.hkis.hkis.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by florentchampigny on 30/07/15.
 */
public interface HKISAPI {

    String URL = "https://api.github.com/";

    @GET("/users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

    @GET("/users/{user}")
    Call<User> user(@Path("user") String user);

}
