package com.huake.hkis.hkis;

import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.huake.hkis.hkis.model.User;
import com.huake.hkis.hkis.utils.Constants;

/**
 * Created by ysstech on 2017/6/7.
 */

public class SettingActivity extends AppCompatActivity {

    private ImageView backImg;
    private EditText addressEt;
    private EditText portEt;
    private Button settingBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        backImg = (ImageView) findViewById(R.id.img_back);
        addressEt = (EditText)findViewById(R.id.et_address);
        portEt = (EditText)findViewById(R.id.et_port);
        settingBtn = (Button) findViewById(R.id.bt_setting);

        SharedPreferences sp = getSharedPreferences(Constants.SP_STORE_KEY,MODE_PRIVATE);

        addressEt.setText(sp.getString(Constants.SP_ADDRESS_KEY,""));
        portEt.setText(sp.getString(Constants.SP_PORT_KEY,""));

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                SettingActivity.this.finish();
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = addressEt.getText().toString().trim();
                String port = portEt.getText().toString().trim();
                if(address.isEmpty() || port.isEmpty()){
                    Toast.makeText(getApplicationContext(),"服务器地址或者端口号为空",Toast.LENGTH_LONG).show();

                }else{
                        SharedPreferences sp = getSharedPreferences(Constants.SP_STORE_KEY,MODE_PRIVATE);
                        SharedPreferences.Editor spet = sp.edit();
                        spet.putString(Constants.SP_ADDRESS_KEY,address);
                        spet.putString(Constants.SP_PORT_KEY,port);
                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                        startActivity(intent);
                        SettingActivity.this.finish();
                }
            }
        });
    }
}
