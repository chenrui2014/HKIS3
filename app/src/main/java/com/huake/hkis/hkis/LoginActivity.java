package com.huake.hkis.hkis;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huake.hkis.hkis.dagger.AppModule;
import com.huake.hkis.hkis.model.User;
import com.huake.hkis.hkis.repository.HKISRepository;
import com.huake.hkis.hkis.utils.Constants;

import okhttp3.ResponseBody;

/**
 *
 */
public class LoginActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private EditText userNameEt;
    private EditText pwdEt;
    private Button loginBtn;
    private TextView settingTv;

    private HKISRepository hkisRep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppModule appModule = new AppModule(getApplication());
        hkisRep = appModule.providesHKISRepository(appModule.providesHKISApi());
        userNameEt = (EditText)findViewById(R.id.et_name);
        pwdEt = (EditText)findViewById(R.id.et_pwd);

        loginBtn = (Button) findViewById(R.id.bt_login);
        settingTv = (TextView) findViewById(R.id.tv_setting);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = userNameEt.getText().toString().trim();
                String pwd = pwdEt.getText().toString().trim();
                if(userName.isEmpty() || pwd.isEmpty()){
                    Toast.makeText(getApplicationContext(),"登录名或者密码为空",Toast.LENGTH_LONG).show();

                }else{
                    LiveData<User> user = hkisRep.getUser(userName,pwd);
                    user.observe(LoginActivity.this,myUser -> {
                        if(myUser != null){
                            SharedPreferences sp = getSharedPreferences(Constants.SP_STORE_KEY,MODE_PRIVATE);
                            SharedPreferences.Editor spet = sp.edit();
                            spet.putString(Constants.SP_USERNAME_KEY,myUser.getLoginName());
                            spet.putString(Constants.SP_PWD_KEY,pwd);
                            spet.putBoolean(Constants.SP_ISCHECK_KEY,true);
                            spet.putString(Constants.SP_USER_ID_KEY,myUser.getUserId());
                            spet.commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"服务器异常！",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        settingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
