package com.huake.hkis.hkis.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.huake.hkis.hkis.HKISAPI;
import com.huake.hkis.hkis.model.AboutUs;
import com.huake.hkis.hkis.model.Check;
import com.huake.hkis.hkis.model.CheckDetail;
import com.huake.hkis.hkis.model.CheckParam;
import com.huake.hkis.hkis.model.HKISMessage;
import com.huake.hkis.hkis.model.MaterialDetails;
import com.huake.hkis.hkis.model.MaterialShelves;
import com.huake.hkis.hkis.model.MyResponsBody;
import com.huake.hkis.hkis.model.ShelvesDetail;
import com.huake.hkis.hkis.model.Task;
import com.huake.hkis.hkis.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HKISRepositoryImpl implements HKISRepository {

    private static final String SUCCESS_CODE = "0000";
    private static final String FAILURE_CODE = "0001";

    private final HKISAPI hkisAPI;

    public HKISRepositoryImpl(HKISAPI hkisAPI) {
        this.hkisAPI = hkisAPI;
    }

    @Override
    public LiveData<User> getUser(String userName, String pwd){
        final MutableLiveData<User> liveData = new MutableLiveData<>();
        hkisAPI.user(userName,pwd).enqueue(new Callback<MyResponsBody<User>>() {
            @Override
            public void onResponse(Call<MyResponsBody<User>> call, Response<MyResponsBody<User>> response) {
                if(response.isSuccessful()) {
                    MyResponsBody<User> rb = response.body();
                    User tempUser = rb.getData();
                    liveData.setValue(tempUser);
                }
            }

            @Override
            public void onFailure(Call<MyResponsBody<User>> call, Throwable t) {

            }
        });

        return liveData;
    }

    @Override
    public LiveData<List<Task>> getTask(String userId, String taskType, String taskNO,String documentsType,int pageNo,int pageSize) {
        final MutableLiveData<List<Task>> liveData = new MutableLiveData<>();
        hkisAPI.task(userId,taskType,taskNO,documentsType,pageNo,pageSize).enqueue(new Callback<MyResponsBody<List<Task>>>() {
            @Override
            public void onResponse(Call<MyResponsBody<List<Task>>> call, Response<MyResponsBody<List<Task>>> response) {
                if(response.isSuccessful()){

                    MyResponsBody<List<Task>> rb = response.body();
                    List<Task> tempTasks = rb.getData();
                    liveData.setValue(tempTasks);
                }
            }

            @Override
            public void onFailure(Call<MyResponsBody<List<Task>>> call, Throwable t) {

            }
        });

        return liveData;
    }

    @Override
    public LiveData<List<ShelvesDetail>> getShelvesDetail(String loginName, String taskType, String taskNO) {
        final MutableLiveData<List<ShelvesDetail>> liveData = new MutableLiveData<>();
        hkisAPI.shelvesDetail(loginName,taskType,taskNO).enqueue(new Callback<MyResponsBody<List<ShelvesDetail>>>() {
            @Override
            public void onResponse(Call<MyResponsBody<List<ShelvesDetail>>> call, Response<MyResponsBody<List<ShelvesDetail>>> response) {
                if(response.isSuccessful()){

                    MyResponsBody<List<ShelvesDetail>> rb = response.body();
                    List<ShelvesDetail> tempshelvesDetail = rb.getData();
                    liveData.setValue(tempshelvesDetail);
                }
            }

            @Override
            public void onFailure(Call<MyResponsBody<List<ShelvesDetail>>> call, Throwable t) {

            }
        });

        return liveData;
    }

    @Override
    public LiveData<Boolean> getMaterialShelves(MaterialShelves materialShelves) {
        final MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        hkisAPI.materialShelves(materialShelves).enqueue(new Callback<MyResponsBody<String>>() {
            @Override
            public void onResponse(Call<MyResponsBody<String>> call, Response<MyResponsBody<String>> response) {
                if(response.isSuccessful()){

                    MyResponsBody<String> rb = response.body();
                    if(SUCCESS_CODE.equals(rb.getMsg())){
                        liveData.setValue(true);
                    }
                    liveData.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<MyResponsBody<String>> call, Throwable t) {

                liveData.setValue(false);
            }
        });

        return liveData;
    }

    @Override
    public LiveData<List<MaterialDetails>> getMaterialDetails(String userId, String resourceStorageSpace, int pageNo, int pageSize) {
        final MutableLiveData<List<MaterialDetails>> liveData = new MutableLiveData<>();
        hkisAPI.materialDetails(userId,resourceStorageSpace,pageNo,pageSize).enqueue(new Callback<MyResponsBody<List<MaterialDetails>>>() {
            @Override
            public void onResponse(Call<MyResponsBody<List<MaterialDetails>>> call, Response<MyResponsBody<List<MaterialDetails>>> response) {
                if(response.isSuccessful()){

                    MyResponsBody<List<MaterialDetails>> rb = response.body();
                    List<MaterialDetails> tempshelvesDetail = rb.getData();
                    liveData.setValue(tempshelvesDetail);
                }
            }

            @Override
            public void onFailure(Call<MyResponsBody<List<MaterialDetails>>> call, Throwable t) {

            }
        });

        return liveData;
    }

    @Override
    public LiveData<String> insertMdetailed(String userId, String materialNO) {
        final MutableLiveData<String> liveData = new MutableLiveData<>();
        hkisAPI.insertMdetailed(userId,materialNO).enqueue(new Callback<MyResponsBody<String>>() {
            @Override
            public void onResponse(Call<MyResponsBody<String>> call, Response<MyResponsBody<String>> response) {
                if(response.isSuccessful()){

                    MyResponsBody<String> rb = response.body();
                    String tempshelvesDetail = "";
                    liveData.setValue(tempshelvesDetail);
                }
            }

            @Override
            public void onFailure(Call<MyResponsBody<String>> call, Throwable t) {

            }
        });

        return liveData;
    }

    @Override
    public LiveData<String> updataMdetailed(String userId, String resourceStorageSpace, String targetStorageSpace) {
        final MutableLiveData<String> liveData = new MutableLiveData<>();
        hkisAPI.updataMdetailed(userId,resourceStorageSpace,targetStorageSpace).enqueue(new Callback<MyResponsBody<String>>() {
            @Override
            public void onResponse(Call<MyResponsBody<String>> call, Response<MyResponsBody<String>> response) {
                if(response.isSuccessful()){

                    MyResponsBody<String> rb = response.body();
                    String tempshelvesDetail = rb.getData();
                    liveData.setValue(tempshelvesDetail);
                }
            }

            @Override
            public void onFailure(Call<MyResponsBody<String>> call, Throwable t) {

            }
        });

        return liveData;
    }

    @Override
    public LiveData<List<Check>> getCheckList(CheckParam checkParam, int pageNo, int pageSize) {
        final MutableLiveData<List<Check>> liveData = new MutableLiveData<>();
        hkisAPI.check(checkParam,pageNo+"",pageSize+"").enqueue(new Callback<MyResponsBody<List<Check>>>() {
            @Override
            public void onResponse(Call<MyResponsBody<List<Check>>> call, Response<MyResponsBody<List<Check>>> response) {
                if(response.isSuccessful()){

                    MyResponsBody<List<Check>> rb = response.body();
                    List<Check> tempshelvesDetail = rb.getData();
                    liveData.setValue(tempshelvesDetail);
                }
            }

            @Override
            public void onFailure(Call<MyResponsBody<List<Check>>> call, Throwable t) {

            }
        });

        return liveData;
    }

    @Override
    public LiveData<List<CheckDetail>> getCheckDetail(String userId, String storageSpace, int pageNo, int pageSize) {
        final MutableLiveData<List<CheckDetail>> liveData = new MutableLiveData<>();
        hkisAPI.checkDetail(userId,storageSpace,pageNo+"",pageSize+"").enqueue(new Callback<MyResponsBody<List<CheckDetail>>>() {
            @Override
            public void onResponse(Call<MyResponsBody<List<CheckDetail>>> call, Response<MyResponsBody<List<CheckDetail>>> response) {
                if(response.isSuccessful()){

                    MyResponsBody<List<CheckDetail>> rb = response.body();
                    List<CheckDetail> tempshelvesDetail = rb.getData();
                    liveData.setValue(tempshelvesDetail);
                }
            }

            @Override
            public void onFailure(Call<MyResponsBody<List<CheckDetail>>> call, Throwable t) {

            }
        });

        return liveData;
    }

    @Override
    public LiveData<String> updateCheckDetail(String userId, String checkDetailId, String checkAmount) {
        final MutableLiveData<String> liveData = new MutableLiveData<>();
        hkisAPI.updateCheckDetail(userId,checkDetailId,checkAmount).enqueue(new Callback<MyResponsBody<String>>() {
            @Override
            public void onResponse(Call<MyResponsBody<String>> call, Response<MyResponsBody<String>> response) {
                if(response.isSuccessful()){

                    MyResponsBody<String> rb = response.body();
                    String tempshelvesDetail = "更新盘点数据成功！";
                    liveData.setValue(tempshelvesDetail);
                }
            }

            @Override
            public void onFailure(Call<MyResponsBody<String>> call, Throwable t) {

            }
        });

        return liveData;
    }

    @Override
    public LiveData<Boolean> exit(String userId) {
        final MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        hkisAPI.exit(userId).enqueue(new Callback<MyResponsBody<String>>() {
            @Override
            public void onResponse(Call<MyResponsBody<String>> call, Response<MyResponsBody<String>> response) {
                if(response.isSuccessful()){

                    MyResponsBody<String> rb = response.body();
                    HKISMessage msg = rb.getMsg();
                    if(SUCCESS_CODE.equals(msg.getCode())){
                        liveData.setValue(true);
                    }
                }
                liveData.setValue(false);
            }

            @Override
            public void onFailure(Call<MyResponsBody<String>> call, Throwable t) {

                liveData.setValue(false);
            }
        });

        return liveData;
    }

    @Override
    public LiveData<Boolean> editPw(String userId, String password, String newPw) {
        final MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        hkisAPI.editPw(userId,password,newPw).enqueue(new Callback<MyResponsBody<String>>() {
            @Override
            public void onResponse(Call<MyResponsBody<String>> call, Response<MyResponsBody<String>> response) {
                if(response.isSuccessful()){
                    MyResponsBody<String> rb = response.body();
                    HKISMessage msg = rb.getMsg();
                    if(SUCCESS_CODE.equals(msg.getCode())){
                        liveData.setValue(true);
                    }
                }
                liveData.setValue(false);
            }

            @Override
            public void onFailure(Call<MyResponsBody<String>> call, Throwable t) {

                liveData.setValue(false);
            }
        });

        return liveData;
    }

    @Override
    public LiveData<AboutUs> aboutUs() {
        final MutableLiveData<AboutUs> liveData = new MutableLiveData<>();
        hkisAPI.aboutUs().enqueue(new Callback<MyResponsBody<AboutUs>>() {
            @Override
            public void onResponse(Call<MyResponsBody<AboutUs>> call, Response<MyResponsBody<AboutUs>> response) {
                if(response.isSuccessful()){

                    MyResponsBody<AboutUs> rb = response.body();
                    AboutUs tempshelvesDetail = rb.getData();
                    liveData.setValue(tempshelvesDetail);
                }
            }

            @Override
            public void onFailure(Call<MyResponsBody<AboutUs>> call, Throwable t) {

            }
        });

        return liveData;
    }

    @Override
    public LiveData<List<String>> taskNoList() {
        final MutableLiveData<List<String>> liveData = new MutableLiveData<>();
        hkisAPI.taskNoList().enqueue(new Callback<MyResponsBody<List<String>>>() {
            @Override
            public void onResponse(Call<MyResponsBody<List<String>>> call, Response<MyResponsBody<List<String>>> response) {
                if(response.isSuccessful()){

                    MyResponsBody<List<String>> rb = response.body();
                    List<String> tempshelvesDetail = rb.getData();
                    liveData.setValue(tempshelvesDetail);
                }
            }

            @Override
            public void onFailure(Call<MyResponsBody<List<String>>> call, Throwable t) {

            }
        });

        return liveData;
    }

    @Override
    public LiveData<List<String>> warehouseList() {
        final MutableLiveData<List<String>> liveData = new MutableLiveData<>();
        hkisAPI.warehouseList().enqueue(new Callback<MyResponsBody<List<String>>>() {
            @Override
            public void onResponse(Call<MyResponsBody<List<String>>> call, Response<MyResponsBody<List<String>>> response) {
                if(response.isSuccessful()){

                    MyResponsBody<List<String>> rb = response.body();
                    List<String> tempshelvesDetail = rb.getData();
                    liveData.setValue(tempshelvesDetail);
                }
            }

            @Override
            public void onFailure(Call<MyResponsBody<List<String>>> call, Throwable t) {

            }
        });

        return liveData;
    }

    @Override
    public LiveData<List<String>> checkNoList() {
        final MutableLiveData<List<String>> liveData = new MutableLiveData<>();
        hkisAPI.checkNoList().enqueue(new Callback<MyResponsBody<List<String>>>() {
            @Override
            public void onResponse(Call<MyResponsBody<List<String>>> call, Response<MyResponsBody<List<String>>> response) {
                if(response.isSuccessful()){

                    MyResponsBody<List<String>> rb = response.body();
                    List<String> tempshelvesDetail = rb.getData();
                    liveData.setValue(tempshelvesDetail);
                }
            }

            @Override
            public void onFailure(Call<MyResponsBody<List<String>>> call, Throwable t) {

            }
        });

        return liveData;
    }
}
