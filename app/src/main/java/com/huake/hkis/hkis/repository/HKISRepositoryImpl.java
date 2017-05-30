package com.huake.hkis.hkis.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.huake.hkis.hkis.HKISAPI;
import com.huake.hkis.hkis.model.Repo;
import com.huake.hkis.hkis.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HKISRepositoryImpl implements HKISRepository {

    private final HKISAPI hkisAPI;

    public HKISRepositoryImpl(HKISAPI hkisAPI) {
        this.hkisAPI = hkisAPI;
    }

    @Override
    public LiveData<User> getUser(String userName){
        final MutableLiveData<User> liveData = new MutableLiveData<>();

        hkisAPI.user(userName).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        return liveData;
    }

    @Override
    public LiveData<List<Repo>> getRepos(String userName){
        final MutableLiveData<List<Repo>> liveData = new MutableLiveData<>();

        hkisAPI.listRepos(userName).enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if(response.isSuccessful()){
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });

        return liveData;
    }
}
