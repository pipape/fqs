package com.example.fqs.begin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fqs.CommonParameter;
import com.example.fqs.R;
import com.gyf.immersionbar.ImmersionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class begin_register extends AppCompatActivity implements View.OnClickListener{

    EditText phone;
    EditText password;
    EditText name;
    String string_phone;
    String string_password;
    String string_name;
    Button register;
    String string_yzm;
    EditText yzm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ImmersionBar.with(this)
                .init();

        phone=(EditText) findViewById(R.id.begin_register_phone);
        password=(EditText) findViewById(R.id.begin_register_password);
        name=(EditText) findViewById(R.id.begin_register_name);
        yzm=(EditText) findViewById(R.id.begin_register_verification);
        //登录按钮点击事件
        register=(Button) findViewById(R.id.begin_register);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.begin_register:
                Log.d("begin_register","点击了注册按钮");
                string_name=name.getText().toString();
                string_phone=phone.getText().toString();
                string_password=password.getText().toString();
                string_yzm=yzm.getText().toString();
//                Toast.makeText(this,string_name+string_phone+" "+string_password,Toast.LENGTH_SHORT).show();
                //通过okhttp发起post请求
                postRequest(string_name,string_phone,string_password,string_yzm);
                break;
        }
    }

    /**
     * post请求后台
     * @param phone
     * @param password
     */
    private void postRequest(String name,String phone,String password,String yzm)  {
        //建立请求表单，添加上传服务器的参数
        RequestBody formBody = new FormBody.Builder()
                .add("nickname",name)
                .add("id",phone)
                .add("password",password)
                .add("yzm",yzm)
                .build();

        //发起请求
//      .addHeader("receipt", comParam.register_receipt)
        final Request request = new Request.Builder()
                .url(CommonParameter.url_root+"/account/regist")
                .post(formBody)
                .build();
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    client.newCall(request).enqueue(new Callback() {
                        public void onFailure(Call call, IOException e) {
                            System.out.println(e.getMessage());
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            int errCode=-1;
                            String errMsg="";
                            if (response.code() >= 200 && response.code() < 300) {
                                String reponseData=response.body().string();
                                System.out.println("打印注册返回结果："+reponseData);
                                JSONObject jsonObject= null;
                                try {
                                    jsonObject = new JSONObject(reponseData);
                                    errCode = jsonObject.getInt("errCode");
                                    errMsg=jsonObject.getString("errMsg");
                                    if(errCode==0){
                                        Looper.prepare();
                                        Toast.makeText(getBaseContext(),"注册成功！",Toast.LENGTH_SHORT).show();
                                        //跳转至登录界面
                                        Intent i=new Intent(begin_register.this, begin_login.class);
                                        startActivity(i);
                                        finish();
                                        Looper.loop();

                                    }else if(errCode==1){
                                        Looper.prepare();
                                        Toast.makeText(getBaseContext(),errMsg,Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                } catch (JSONException e) {
                                    Looper.prepare();
                                    e.printStackTrace();
                                    Toast.makeText(getBaseContext(),"注册失败！",Toast.LENGTH_SHORT).show();
                                    Looper.loop();

                                }

                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 解析返回数据
     */
//    private void parseJSONWithJSONObject(String jsonData){
//        try{
//            JSONObject jsonObject=new JSONObject(jsonData);
//            errCode = jsonObject.getString("errCode");
//            ifLoginSuccess(errCode);
//            String errMsg = jsonObject.getString("errMsg");
//            Log.d("begin_login",errMsg);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    /**
     * 确认是否注册成功，并跳转至登录界面
     */
//    private void ifLoginSuccess(int code){
//        if(code==0){
//            Toast.makeText(getBaseContext(),"成功注册！",Toast.LENGTH_SHORT).show();
//            //跳转至登录界面
//            Intent i=new Intent(this, begin_login.class);
//            startActivity(i);
//            finish();
//        }
//        else
//            Toast.makeText(getBaseContext(),"注册失败！",Toast.LENGTH_SHORT).show();
//
//
//    }
}
