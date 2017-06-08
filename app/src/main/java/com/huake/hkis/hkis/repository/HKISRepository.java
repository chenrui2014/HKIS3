package com.huake.hkis.hkis.repository;

import android.arch.lifecycle.LiveData;

import com.huake.hkis.hkis.model.Repo;
import com.huake.hkis.hkis.model.User;

import java.util.List;


/**
 *
 */
public interface HKISRepository {
    LiveData<User> getUser(String userName,String pwd);

    LiveData<List<Repo>> getRepos(String userName);
}
