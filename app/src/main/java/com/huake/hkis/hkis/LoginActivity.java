package com.huake.hkis.hkis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 *
 */
public class LoginActivity extends AppCompatActivity {
    private static final String SP_USERNAME_KEY = "userName";
    private static final String SP_PWD_KEY = "pwd";

    private EditText userName;
    private EditText pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText)findViewById(R.id.et_name);
        pwd = (EditText)findViewById(R.id.et_pwd);
    }

}
