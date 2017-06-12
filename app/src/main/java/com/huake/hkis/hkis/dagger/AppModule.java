package com.huake.hkis.hkis.dagger;

import android.app.Application;
import android.content.SharedPreferences;

import com.huake.hkis.hkis.HKISAPI;
import com.huake.hkis.hkis.repository.HKISRepository;
import com.huake.hkis.hkis.repository.HKISRepositoryImpl;
import com.huake.hkis.hkis.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

@Module
public class AppModule {

    private final Application application;

    public static final String IP_ADDRESS = "59.110.164.202";
    public static final String PORT= "8082";

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public HKISAPI providesHKISApi(){
        SharedPreferences sp =application.getSharedPreferences(Constants.SP_STORE_KEY,MODE_PRIVATE);
        String ipAddress = sp.getString(Constants.SP_ADDRESS_KEY,IP_ADDRESS);
        String port = sp.getString(Constants.SP_PORT_KEY,PORT);
        String url = "http://"+ipAddress + ":" + port + "/";
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HKISAPI.class);
    }

    @Provides
    @Singleton
    public HKISRepository providesHKISRepository(HKISAPI hkisAPI){
        return new HKISRepositoryImpl(hkisAPI);
    }
}
