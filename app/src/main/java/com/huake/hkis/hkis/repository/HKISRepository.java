package com.huake.hkis.hkis.repository;

import android.arch.lifecycle.LiveData;

import com.huake.hkis.hkis.model.Repo;
import com.huake.hkis.hkis.model.User;

import java.util.List;


/**
 *
 */
public interface HKISRepository {
    LiveData<User> getUser(String userName);

    LiveData<List<Repo>> getRepos(String userName);
}
