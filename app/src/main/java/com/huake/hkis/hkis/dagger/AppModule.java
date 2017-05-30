package com.huake.hkis.hkis.dagger;

import android.app.Application;

import com.huake.hkis.hkis.HKISAPI;
import com.huake.hkis.hkis.repository.HKISRepository;
import com.huake.hkis.hkis.repository.HKISRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public HKISAPI providesGithubApi(){
        return new Retrofit.Builder()
                .baseUrl(HKISAPI.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HKISAPI.class);
    }

    @Provides
    @Singleton
    public HKISRepository providesGithubRepository(HKISAPI hkisAPI){
        return new HKISRepositoryImpl(hkisAPI);
    }
}
