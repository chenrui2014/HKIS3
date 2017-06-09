package com.huake.hkis.hkis.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.huake.hkis.hkis.HKISAPI;
import com.huake.hkis.hkis.model.Repo;
import com.huake.hkis.hkis.model.ShelvesDetail;
import com.huake.hkis.hkis.model.Task;
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
    public LiveData<User> getUser(String userName,String pwd){
        final MutableLiveData<User> liveData = new MutableLiveData<>();

        hkisAPI.user(userName,pwd).enqueue(new Callback<User>() {
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
    public User login(String userName, String pwd) {
        final MutableLiveData<User> liveData = new MutableLiveData<>();
        hkisAPI.login(userName,pwd).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        return liveData.getValue();
    }

    @Override
    public List<Task> getTask(String userId, String taskType, String taskNO) {
        final MutableLiveData<List<Task>> liveData = new MutableLiveData<>();
        hkisAPI.task(userId,taskType,taskNO).enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if(response.isSuccessful()){
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {

            }
        });

        return liveData.getValue();
    }

    @Override
    public List<ShelvesDetail> getShelvesDetail(String loginName, String taskType, String taskNO) {
        final MutableLiveData<List<ShelvesDetail>> liveData = new MutableLiveData<>();
        hkisAPI.shelvesDetail(loginName,taskType,taskNO).enqueue(new Callback<List<ShelvesDetail>>() {
            @Override
            public void onResponse(Call<List<ShelvesDetail>> call, Response<List<ShelvesDetail>> response) {
                if(response.isSuccessful()){
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ShelvesDetail>> call, Throwable t) {

            }
        });

        return liveData.getValue();
    }
}
