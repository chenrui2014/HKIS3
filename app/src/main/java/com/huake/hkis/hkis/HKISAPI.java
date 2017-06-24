package com.huake.hkis.hkis;

import com.huake.hkis.hkis.model.AboutUs;
import com.huake.hkis.hkis.model.Check;
import com.huake.hkis.hkis.model.CheckDetail;
import com.huake.hkis.hkis.model.CheckParam;
import com.huake.hkis.hkis.model.MaterialShelves;
import com.huake.hkis.hkis.model.MaterialDetails;
import com.huake.hkis.hkis.model.MyResponsBody;
import com.huake.hkis.hkis.model.ShelvesDetail;
import com.huake.hkis.hkis.model.Task;
import com.huake.hkis.hkis.model.UpgradeVersion;
import com.huake.hkis.hkis.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 *
 */
public interface HKISAPI {

    //String URL = "http://59.110.164.202:8082/storage/";

    @FormUrlEncoded
    @POST("/storage/mine/login.do")
    Call<MyResponsBody<User>> user(@Field("loginName") String loginName, @Field("password") String password);

    @FormUrlEncoded
    @POST("/storage/task/shelves.do")
    Call<MyResponsBody<List<Task>>> task(@Field("userId") String userId, @Field("taskType") String taskType, @Field("taskNO") String taskNO,@Field("documentsType")String documentsType,@Field("wareHouseNum")String wareHouseNum,@Field("inStorageTime")String inStorageTime,@Field("pageNo") int pageNo,@Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("/storage/task/shelvesDetail.do")
    Call<MyResponsBody<List<ShelvesDetail>>> shelvesDetail(@Field("userId") String userId, @Field("taskType") String taskType, @Field("taskNO") String taskNO, @Field("pageNo") String pageNo, @Field("pageSize") String pageSize);

    @POST("/storage/task/materialShelves.do")
    Call<MyResponsBody<String>> materialShelves(@Body MaterialShelves materialShelves);

    @FormUrlEncoded
    @POST("/storage/task/Materialdetails.do")
    Call<MyResponsBody<List<MaterialDetails>>> changeMaterialDetails(@Field("userId") String userId, @Field("resourceStorageSpace") String resourceStorageSpace,@Field("pageNo") int pageNo,@Field("pageSize") int pageSize);

    @FormUrlEncoded
    @POST("/storage/task/insertMdetailed.do")
    Call<MyResponsBody<String>> insertMdetailed(@Field("userId") String userId, @Field("materialNO") String materialNO);

    @FormUrlEncoded
    @GET("/storage/task/updataMdetailed.do")
    Call<MyResponsBody<String>> updataMdetailed(@Query("userId") String userId,@Query("resourceStorageSpace") String resourceStorageSpace,@Query("targetStorageSpace") String targetStorageSpace);

    //@POST("/storage/user/register.do")
    //Call<String> register(@Field("resourceStorageSpace") String resourceStorageSpace, @Field("targetStorageSpace") String targetStorageSpace);

    @FormUrlEncoded
    @POST("/storage/check/checkList.do")
    Call<MyResponsBody<List<Check>>> check(@Body CheckParam checkParam,@Field("pageNo") String pageNo, @Field("pageSize") String pageSize);

    @FormUrlEncoded
    @POST("/storage/check/checkDetailList.do")
    Call<MyResponsBody<List<CheckDetail>>> checkDetail(@Field("userId") String userId, @Field("storageSpace") String storageSpace, @Field("checkNO") String checkNO,@Field("pageNo") String pageNo,@Field("pageSize") String pageSize);

    @FormUrlEncoded
    @POST("/storage/check/updatacheckDetail.do")
    Call<MyResponsBody<String>> updateCheckDetail(@Query("userId") String userId,@Query("checkDetailId") String checkDetailId,@Query("checkAmount") String checkAmount);

    @GET("/storage/mine/exit.do")
    Call<MyResponsBody<String>> exit(@Query("userId") String userId);

    @FormUrlEncoded
    @POST("/storage/mine/editPw.do")
    Call<MyResponsBody<String>> editPw(@Field("userId") String userId,@Field("password") String password,@Field("newPw") String newPw);

    @GET("/storage/mine/aboutUs.do")
    Call<MyResponsBody<AboutUs>> aboutUs();

    @GET("/storage/task/taskNoList.do")
    Call<MyResponsBody<List<String>>> taskNoList();
    @GET("/storage/repertory/WarehouseList.do")
    Call<MyResponsBody<List<String>>> warehouseList();
    @GET("/storage/check/CheckNoList.do")
    Call<MyResponsBody<List<String>>> checkNoList();

    @GET("/storage/check/CheckStorageList.do")
    Call<MyResponsBody<List<String>>> checkStorageList();

    @GET("/storage/version/updateVersion.do")
    Call<MyResponsBody<List<UpgradeVersion>>> updateVersion();

}
