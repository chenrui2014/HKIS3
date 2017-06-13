package com.huake.hkis.hkis;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huake.hkis.hkis.dagger.AppModule;
import com.huake.hkis.hkis.repository.HKISRepository;
import com.huake.hkis.hkis.utils.Constants;

/**
 * Created by chen on 2017/6/13.
 */

public class UpdatePasswordActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private HKISRepository hkisRep;

    private EditText oldPwdEt;
    private EditText newPwdEt1;
    private EditText newPwdEt2;

    private TextView confirmBt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_update_pwd);
        AppModule appModule = new AppModule(getApplication());
        hkisRep = appModule.providesHKISRepository(appModule.providesHKISApi());

        oldPwdEt = (EditText) findViewById(R.id.user_pwd_old);
        newPwdEt1 = (EditText) findViewById(R.id.user_pwd_new1);
        newPwdEt2 = (EditText) findViewById(R.id.user_pwd_new2);
        confirmBt = (TextView) findViewById(R.id.user_bt_update);

        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPwd = oldPwdEt.getText().toString().trim();
                String newPwd1 = newPwdEt1.getText().toString().trim();
                String newPwd2 = newPwdEt2.getText().toString().trim();

                if(oldPwd.isEmpty()){
                    Toast.makeText(getApplicationContext(),"原密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(newPwd1.isEmpty() || newPwd2.isEmpty()){
                    Toast.makeText(getApplicationContext(),"密码不能设置为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!newPwd1.equals(newPwd2)){
                    Toast.makeText(getApplicationContext(),"两次输入的新密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences sp = getSharedPreferences(Constants.SP_STORE_KEY,MODE_PRIVATE);
                String userId =sp.getString(Constants.SP_USER_ID_KEY,"");
                LiveData<Boolean> state = hkisRep.editPw(userId,oldPwd,newPwd1);
                state.observe(UpdatePasswordActivity.this, flag ->{
                    if(flag){
                        Toast.makeText(getApplicationContext(),"密码更新成功",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"密码更新失败",Toast.LENGTH_LONG).show();
                    }

                });
            }
        });
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
