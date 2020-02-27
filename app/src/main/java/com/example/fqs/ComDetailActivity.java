package com.example.fqs;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

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

public class ComDetailActivity extends AppCompatActivity {

    private String id;
    private String price;
    private String name;
    private String desc;
    private String icon;
    private String kind;


    TextView pricetv=null;
    TextView price2tv=null;
    TextView nametv=null;
    TextView desctv=null;
    ImageView imageView=null;
    Button button=null;
    ImageView back_bar;
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        this.id=intent.getStringExtra("id");
//        this.desc=intent.getStringExtra("desc");
//        this.name=intent.getStringExtra("name");
//        this.icon=intent.getStringExtra("icon");
//        this.price=intent.getStringExtra("price");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_detail);
        this.id=getIntent().getStringExtra("id");
        this.kind=String.valueOf(getIntent().getIntExtra("kind",0));
        pricetv=findViewById(R.id.detail_price);
        price2tv=findViewById(R.id.detail_price2);
        nametv=findViewById(R.id.detail_name);
        desctv=findViewById(R.id.detail_desc);
        imageView=findViewById(R.id.detail_img);
        button=findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });
        back_bar=findViewById(R.id.back_bar);

        back_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();;
            }
        });

        nametv.setText(getIntent().getStringExtra("name"));
        pricetv.setText(getIntent().getStringExtra("price"));
        price2tv.setText(getIntent().getStringExtra("price"));
        desctv.setText(getIntent().getStringExtra("desc"));
        Glide.with(this).load(CommonParameter.url_root+"/"+getIntent().getStringExtra("icon")).into(imageView);
    }

    private void addToCart() {
        RequestBody formBody = new FormBody.Builder()
                .add("id",CommonParameter.id)
                .add("goods_id",id)
                .add("kind",this.kind)
                .build();

        //发起请求 .addHeader("receipt", CommonParameter.login_receipt)
        final Request request = new Request.Builder()
                .url(CommonParameter.url_root+"/buy")
                .addHeader("receipt",CommonParameter.receipt)
                .post(formBody)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("test",e.getMessage());
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        int errCode=-1;
                        String errMsg="";
                        if (response.code() >= 200 && response.code() < 300) {
                            String responseData="";
                            responseData=response.body().string();
                            System.out.println(responseData);
                            JSONObject jsonObject= null;
                            try {
                                jsonObject = new JSONObject(responseData);
                                errCode = jsonObject.getInt("errCode");
                                errMsg=jsonObject.getString("errMsg");
                                if(errCode==0){
                                    Looper.prepare();
                                    Toast.makeText(ComDetailActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                                else if(errCode==1){
                                    Looper.prepare();
                                    Toast.makeText(getBaseContext(),errMsg,Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

            }
        }).start();
    }


}
