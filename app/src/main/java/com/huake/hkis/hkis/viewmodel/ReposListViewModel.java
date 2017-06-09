package com.huake.hkis.hkis.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.huake.hkis.hkis.model.Repo;
import com.huake.hkis.hkis.repository.HKISRepository;

import java.util.List;

import javax.inject.Inject;

public class ReposListViewModel extends ViewModel {

    private final HKISRepository hkisRepository;

    @Inject
    public ReposListViewModel(HKISRepository hkisRepository) {
        this.hkisRepository = hkisRepository;
    }

    public LiveData<List<Repo>> getRepos(String userName) {
        return null;
    }
}
