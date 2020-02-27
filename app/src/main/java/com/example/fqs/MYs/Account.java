package com.example.fqs.MYs;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fqs.CommonParameter;
import com.example.fqs.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Account extends AppCompatActivity {

    private String points;
    private TextView poi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ImageView imageView =findViewById(R.id.account_back_bar);
        TextView id=findViewById(R.id.id);
        poi=findViewById(R.id.points);
        TextView password=findViewById(R.id.password);
        id.setText("手机号:"+ CommonParameter.id);
        password.setText("密码:"+CommonParameter.password);
        RequestData();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void RequestData()
    {
        final RequestBody formBody = new FormBody.Builder()
                .add("id",CommonParameter.id)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("test",CommonParameter.id+"-"+CommonParameter.receipt);
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(CommonParameter.url_root + "/user/showUser")
                        .addHeader("receipt",CommonParameter.receipt)
                        .post(formBody)
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    String data = response.body().string();
                    Log.d("test", data);

                    jsonJX(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void jsonJX(String data) {
        if (data != null) {
            try {

                JSONObject jsonObject = new JSONObject(data);
                String code = jsonObject.getString("errCode");
                if (code.equals("0")) {
                    JSONObject object=jsonObject.getJSONObject("data");

                    points=object.getString("points");
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);

                } else {
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Log.d("Handler", "数据接受正确！");
                    poi.setText("积分:"+points);
                    break;
                case 2:
                    Log.d("Handler", "数据错误！");
                    break;

            }
        }
    };
}
