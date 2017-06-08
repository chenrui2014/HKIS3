package com.huake.hkis.hkis.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.huake.hkis.hkis.model.User;
import com.huake.hkis.hkis.repository.HKISRepository;

import javax.inject.Inject;

/**
 * Created by florentchampigny on 18/05/2017.
 */

public class UserViewModel extends ViewModel {

    private final HKISRepository hkisRepository;

    @Inject
    public UserViewModel(HKISRepository hkisRepository) {
        this.hkisRepository = hkisRepository;
    }

    public LiveData<User> getUser(String userName,String pwd) {
        //userLiveData will be notified when the user is fetched
        return hkisRepository.getUser(userName,pwd);
    }
}
