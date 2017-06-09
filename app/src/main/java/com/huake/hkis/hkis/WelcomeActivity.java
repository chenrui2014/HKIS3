package com.huake.hkis.hkis;

import android.arch.lifecycle.LifecycleActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.huake.hkis.hkis.utils.Constants;

/**
 * Created by ysstech on 2017/6/2.
 */

public class WelcomeActivity extends LifecycleActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏标题栏以及状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题**/
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_welcome);
        handler.sendEmptyMessageDelayed(0,3000);
    }

    public void getHome(){
        float currentVersionCode = getVersionCode(WelcomeActivity.this);
        SharedPreferences sp = getSharedPreferences(Constants.SP_STORE_KEY,MODE_PRIVATE);
        float spVersionCode = sp.getFloat(Constants.SP_VERSION_KEY,0);
        if(currentVersionCode > spVersionCode){
            SharedPreferences.Editor editor = sp.edit();
            editor.putFloat(Constants.SP_VERSION_KEY,currentVersionCode);
            editor.commit();
            //首次启动调转到服务器设置界面
            Intent intent = new Intent(WelcomeActivity.this, SettingActivity.class);
            startActivity(intent);
            finish();
        }else{
            Boolean isCheck = sp.getBoolean(Constants.SP_ISCHECK_KEY,false);
            if(isCheck){
                //已登陆用户跳转到主界面
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                //未登陆用户跳转到登陆页面
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }

    }

    private float getVersionCode(Context context){
        float versionCode = 0;
        try{

            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionCode;
        }catch(PackageManager.NameNotFoundException e){

            e.printStackTrace();
        }
        return versionCode;
    }
}
