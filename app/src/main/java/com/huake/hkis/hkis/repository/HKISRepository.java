package com.huake.hkis.hkis.repository;

import android.arch.lifecycle.LiveData;

import com.huake.hkis.hkis.model.AboutUs;
import com.huake.hkis.hkis.model.Check;
import com.huake.hkis.hkis.model.CheckDetail;
import com.huake.hkis.hkis.model.CheckParam;
import com.huake.hkis.hkis.model.MaterialDetails;
import com.huake.hkis.hkis.model.MaterialShelves;
import com.huake.hkis.hkis.model.ShelvesDetail;
import com.huake.hkis.hkis.model.Task;
import com.huake.hkis.hkis.model.User;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.Query;


/**
 *
 */
public interface HKISRepository {
    /**
     * 登陆接口
     * @param userName
     * @param pwd
     * @return
     */
    LiveData<User> getUser(String userName, String pwd);

    /**
     * 任务列表接口
     * @param userId
     * @param taskType
     * @param taskNO
     * @param documentsType
     * @param pageNo
     * @param pageSize
     * @return
     */
    LiveData<List<Task>> getTask(String userId,String taskType,String taskNO,String documentsType,int pageNo,int pageSize);

    /**
     * 任务详细接口
     * @param loginName
     * @param taskType
     * @param taskNO
     * @return
     */
    LiveData<List<ShelvesDetail>> getShelvesDetail(String loginName,String taskType,String taskNO);

    /**
     * 物料上/下架接口
     * @param materialShelves
     * @return
     */
    LiveData<Boolean> getMaterialShelves(MaterialShelves materialShelves);

    /**
     * 物料明细接口
     * @param userId
     * @param resourceStorageSpace
     * @param pageNo
     * @param pageSize
     * @return
     */
    LiveData<List<MaterialDetails>> getMaterialDetails(String userId, String resourceStorageSpace, int pageNo, int pageSize);

    /**
     * 移仓接口
     * @param userId
     * @param materialNO
     * @return
     */
    LiveData<String> insertMdetailed(String userId,String materialNO);

    /**
     *移仓上架
     * @param userId
     * @param resourceStorageSpace
     * @param targetStorageSpace
     * @return
     */
    LiveData<String> updataMdetailed(String userId,String resourceStorageSpace,String targetStorageSpace);

    /**
     * 明盘/暗盘接口
     * @param checkParam
     * @param pageNo
     * @param pageSize
     * @return
     */
    LiveData<List<Check>> getCheckList(CheckParam checkParam, int pageNo, int pageSize);

    /**
     * 盘点详细接口
     * @param userId
     * @param storageSpace
     * @param pageNo
     * @param pageSize
     * @return
     */
    LiveData<List<CheckDetail>> getCheckDetail(String userId, String storageSpace, int pageNo,int pageSize);

    /**
     * 更新盘点详情接口
     * @param userId
     * @param checkDetailId
     * @param checkAmount
     * @return
     */
    LiveData<String> updateCheckDetail(String userId, String checkDetailId, String checkAmount);

    /**
     * 退出接口
     * @param userId
     * @return
     */
    LiveData<Boolean> exit(String userId);

    /**
     * 修改密码
     * @param userId
     * @param password
     * @param newPw
     * @return
     */
    LiveData<Boolean> editPw(String userId, String password, String newPw);

    /**
     * 关于我们
     * @return
     */
    LiveData<AboutUs> aboutUs();

    /**
     * 任务单据号
     * @return
     */
    LiveData<List<String>> taskNoList();

    /**
     * 仓库号
     * @return
     */
    LiveData<List<String>> warehouseList();

    /**
     * 盘点单据号
     * @return
     */
    LiveData<List<String>> checkNoList();
}
