/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huake.hkis.hkis.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.huake.hkis.hkis.HKISAPI;
import com.huake.hkis.hkis.db.HKISDb;
import com.huake.hkis.hkis.model.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * A task that reads the search result in the database and fetches the next page, if it has one.
 */
public class FetchNextSearchPageTask implements Runnable {
    private final MutableLiveData<Resource<Boolean>> liveData = new MutableLiveData<>();
    private final String query;
    private final HKISAPI hkisApi;
    private final HKISDb db;

    FetchNextSearchPageTask(String query, HKISAPI hkisApi, HKISDb db) {
        this.query = query;
        this.hkisApi = hkisApi;
        this.db = db;
    }

    @Override
    public void run() {
//        RepoSearchResult current = db.repoDao().findSearchResult(query);
//        if(current == null) {
//            liveData.postValue(null);
//            return;
//        }
//        final Integer nextPage = current.next;
//        if (nextPage == null) {
//            liveData.postValue(Resource.success(false));
//            return;
//        }

//        try {
//            Response<RepoSearchResponse> response = githubService
//                    .searchRepos(query, nextPage).execute();
//            ApiResponse<RepoSearchResponse> apiResponse = new ApiResponse<>(response);
//            if (apiResponse.isSuccessful()) {
//                // we merge all repo ids into 1 list so that it is easier to fetch the result list.
//                List<Integer> ids = new ArrayList<>();
//                ids.addAll(current.repoIds);
//                //noinspection ConstantConditions
//                ids.addAll(apiResponse.body.getRepoIds());
//                RepoSearchResult merged = new RepoSearchResult(query, ids,
//                        apiResponse.body.getTotal(), apiResponse.getNextPage());
//                try {
//                    db.beginTransaction();
//                    db.repoDao().insert(merged);
//                    db.repoDao().insertRepos(apiResponse.body.getItems());
//                    db.setTransactionSuccessful();
//                } finally {
//                    db.endTransaction();
//                }
//                liveData.postValue(Resource.success(apiResponse.getNextPage() != null));
//            } else {
//                liveData.postValue(Resource.error(apiResponse.errorMessage, true));
//            }
//        } catch (IOException e) {
//            liveData.postValue(Resource.error(e.getMessage(), true));
//        }
    }

    LiveData<Resource<Boolean>> getLiveData() {
        return liveData;
    }
}
