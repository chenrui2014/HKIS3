package com.huake.hkis.hkis;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huake.hkis.hkis.dagger.AppComponent;
import com.huake.hkis.hkis.databinding.FragmentMainBinding;
import com.huake.hkis.hkis.model.Repo;
import com.huake.hkis.hkis.model.User;
import com.huake.hkis.hkis.viewmodel.ReposListViewModel;
import com.huake.hkis.hkis.viewmodel.UserViewModel;

import java.util.List;

import javax.inject.Inject;

public class MainFragment extends LifecycleFragment {

    public static Fragment newInstance() {
        return new MainFragment();
    }

    private FragmentMainBinding viewDataBinding;
    private ReposAdapter reposAdapter;

    @Inject
    UserViewModel userViewModel;

    @Inject
    ReposListViewModel reposListViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //get the databinding from the layout
        this.viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return viewDataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        reposAdapter = new ReposAdapter();
        viewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.recyclerView.setAdapter(reposAdapter);

        AppComponent.from(getContext()).inject(this);
        //inject the viewmodel responding to User
        //inject the viewmodel responding to List<Repo>

        //fetch the user from the datasource
        userViewModel.getUser("chenrui2014","pwd")
                .observe(this, new Observer<User>() {
                    @Override
                    public void onChanged(@Nullable User user) {
                        viewDataBinding.setUser(user);
                    }
                });

        //fetch the repos from the datasource
        reposListViewModel.getRepos("chenrui2014")
                .observe(this, new Observer<List<Repo>>() {
                    @Override
                    public void onChanged(@Nullable List<Repo> repos) {
                        //when available, send it to the recyclerview
                        reposAdapter.setRepos(repos);
                    }
                });
    }
}
