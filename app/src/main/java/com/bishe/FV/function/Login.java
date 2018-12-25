package com.bishe.FV.function;

import org.apache.http.Header;
import org.json.JSONObject;

import com.bishe.FV.MainActivity;
import com.bishe.FV.R;
import com.bishe.FV.net.NetHttpData;
import com.loopj.android.http.JsonHttpResponseHandler;

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

public class Login extends Activity implements OnClickListener {

    private Button loginbtn;
    private TextView register;
    private EditText edtname, edtpwd;
    private ProgressDialog dialog;
    private String username, userpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fv_login);

        loginbtn = (Button) findViewById(R.id.login);
        edtname = (EditText) findViewById(R.id.editText1);
        edtpwd = (EditText) findViewById(R.id.editText2);
        register = (TextView) findViewById(R.id.register);
        loginbtn.setOnClickListener(this);
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent rester=new Intent();
             //  rester.setClass(this,Register.class);

            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        username = edtname.getText().toString();
//        userpwd = edtpwd.getText().toString();
//
//        NetHttpData.getHttpDao().getLogin(username, userpwd, new JsonHttpResponseHandler("utf-8") {
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                int success = response.optInt("success");
//                if (success == 1) {
//                    Intent i = new Intent(Login.this, MainActivity.class);
//                    startActivity(i);
//                    SaveUser();
//                } else if (success == 0) {
//                    Toast.makeText(Login.this, "账号或密码错误,请重新登陆！", 1).show();
//                }
//            }
//
//        });
//    }

    @Override
   public void onClick(View v) {
        username = edtname.getText().toString();
        userpwd = edtpwd.getText().toString();
        if(username.equals("12345678")){
            Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
                   SaveUser();
        }
        else  {
           Toast.makeText(Login.this, "账号或密码错误,请重新登陆！", 1).show();
              }
    }
    public void SaveUser() {
        SharedPreferences ps = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        Editor ed = ps.edit();
        ed.putBoolean("isLogin", true);
        ed.putString("username", "guolu");
        ed.putString("dengji", "20");
        ed.commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SaveUser();
        finish();
    }
}
