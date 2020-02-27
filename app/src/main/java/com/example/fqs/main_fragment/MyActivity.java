package com.example.fqs.main_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.fqs.CommonParameter;
import com.example.fqs.MYs.About;
import com.example.fqs.MYs.Account;
import com.example.fqs.MYs.CareOrder;
import com.example.fqs.MYs.Coupon;
import com.example.fqs.MYs.IdleList;
import com.example.fqs.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyActivity extends Fragment {

    private String nickname;
    private String icon;
    private String points;

    private CircleImageView profile;
    private TextView nametv;
    private TextView idtv;

    private TextView accountv;
    private TextView careordertv;
    private TextView idleordertv;
    private TextView coupontv;
    private TextView aboutv;

    private View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //注意View对象的重复使用，以便节省资源
        if(mView == null) {
            mView = inflater.inflate(R.layout.activity_my,container,false);
            nametv=mView.findViewById(R.id.user_name);
            profile=mView.findViewById(R.id.profile_image);
            idtv=mView.findViewById(R.id.user_id);

            accountv=mView.findViewById(R.id.account);
            careordertv=mView.findViewById(R.id.list_care);
            idleordertv=mView.findViewById(R.id.list_idle);
            coupontv=mView.findViewById(R.id.coupon);
            aboutv=mView.findViewById(R.id.about);

            accountv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getContext(), Account.class);
                    i.putExtra("points",points);
                    startActivity(i);
                }
            });

            careordertv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getContext(), CareOrder.class);
                    startActivity(i);
                }
            });

            idleordertv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getContext(), IdleList.class);
                    startActivity(i);
                }
            });

            coupontv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getContext(), Coupon.class);
                    startActivity(i);
                }
            });

            aboutv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getContext(), About.class);
                    startActivity(i);
                }
            });



            RequestData();
        }
        return mView;
    }


    //请求数据
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
                        nickname=object.getString("nickname");
                        icon=object.getString("icon");
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
                    nametv.setText(nickname);
                    Glide.with(getContext()).load(CommonParameter.url_root+"/"+icon).into(profile);
                    idtv.setText(CommonParameter.id);
                    break;
                case 2:
                    Log.d("Handler", "数据错误！");
                    break;

            }
        }
    };
}
