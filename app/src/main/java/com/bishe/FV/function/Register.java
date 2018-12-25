package com.bishe.FV.function;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.FV.MainActivity;
import com.bishe.FV.R;
import com.bishe.FV.net.NetHttpData;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class Register extends Activity implements OnClickListener {

    private Button register;
    private EditText edtname, edtpwd;
    private ProgressDialog dialog;
    private String username, userpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fv_register);

        register = (Button) findViewById(R.id.login);
        edtname = (EditText) findViewById(R.id.editText1);
        edtpwd = (EditText) findViewById(R.id.editText2);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        username = edtname.getText().toString();
        userpwd = edtpwd.getText().toString();

        NetHttpData.getHttpDao().getLogin(username, userpwd, new JsonHttpResponseHandler("utf-8") {

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                int success = response.optInt("success");
                if (success == 1) {
                    Intent i = new Intent(Register.this, MainActivity.class);
                    startActivity(i);
                    SaveUser();
                } else if (success == 0) {
                    Toast.makeText(Register.this, "账号或密码错误,请重新登陆！", 1).show();
                }
            }

        });
    }

    public void SaveUser() {
        SharedPreferences ps = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        Editor ed = ps.edit();
        ed.putBoolean("isLogin", true);
        ed.putString("username", "里昂");
        ed.putString("dengji", "32");
        ed.commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SaveUser();
        finish();
    }
}
