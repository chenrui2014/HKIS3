package com.huake.hkis.hkis;

import com.huake.hkis.hkis.model.Check;
import com.huake.hkis.hkis.model.CheckDetail;
import com.huake.hkis.hkis.model.CheckParam;
import com.huake.hkis.hkis.model.MaterialShelves;
import com.huake.hkis.hkis.model.MaterialDetails;
import com.huake.hkis.hkis.model.Repo;
import com.huake.hkis.hkis.model.ShelvesDetail;
import com.huake.hkis.hkis.model.Task;
import com.huake.hkis.hkis.model.UpdateDetailParam;
import com.huake.hkis.hkis.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 *
 */
public interface HKISAPI {

    String URL = "http://59.110.164.202:8082/storage/";

    @FormUrlEncoded
    @POST("/mine/login")
    Call<User> user(@Field("loginName") String loginName, @Field("password") String password);

    @FormUrlEncoded
    @POST("/mine/login")
    Call<User> login(@Field("loginName") String loginName, @Field("password") String password);

    @FormUrlEncoded
    @POST("/task/shelves")
    Call<List<Task>> task(@Field("userId") String userId, @Field("taskType") String taskType, @Field("taskNO") String taskNO);

    @FormUrlEncoded
    @POST("/task/shelvesDetail")
    Call<List<ShelvesDetail>> shelvesDetail(@Field("userId") String userId, @Field("taskType") String taskType, @Field("taskNO") String taskNO);

    @POST("/task/materialShelves")
    Call<String> materialShelves(@Body MaterialShelves materialShelves);

    @POST("/task/MaterialDetails")
    Call<List<MaterialDetails>> materialDetails(@Field("loginName") String loginName, @Field("wareHouseNum") String wareHouseNum);

    @POST("/task/insertMdetailed")
    Call<String> insertMdetailed(@Field("loginName") String loginName, @Field("materialNO") String materialNO);

    @POST("/user/register")
    Call<String> register(@Field("resourceStorageSpace") String resourceStorageSpace, @Field("targetStorageSpace") String targetStorageSpace);

    @POST("/check/checkList")
    Call<List<Check>> check(@Body CheckParam checkParam);

    @POST("/check/checkDetailList")
    Call<List<CheckDetail>> checkDetail(@Field("userId") String userId, @Field("storageSpace") String storageSpace);

    @POST("/check/updatacheckDetail")
    Call<String> updateCheckDetail(@Body UpdateDetailParam updateDetailParam);

    @POST("/mine/exit")
    Call<String> exit(@Field("userId") String userId);
}
