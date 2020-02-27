package com.example.fqs.commidity_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fqs.ComDetailActivity;
import com.example.fqs.CommonParameter;
import com.example.fqs.R;
import com.example.fqs.adapter.IdleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShoeGoodsActivity extends AppCompatActivity {

    ListView listView;

    ArrayList<Map<String,Object>> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoe_goods);

        listView=findViewById(R.id.showgoods_list);
        RequestData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("position", String.valueOf(i));

                Intent intent = new Intent(getApplication(), ComDetailActivity.class);
                Log.d("test", list.get(i).get("name").toString());
                intent.putExtra("id", list.get(i).get("id").toString());
                intent.putExtra("icon", list.get(i).get("icon").toString());
                intent.putExtra("desc", list.get(i).get("desc").toString());
                intent.putExtra("name", list.get(i).get("name").toString());
                intent.putExtra("price", list.get(i).get("price").toString());
                intent.putExtra("kind",1);
                startActivity(intent);
            }
        });
    }

    private void RequestData() {

        final RequestBody formBody = new FormBody.Builder()
                .add("kind",String.valueOf(2))
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(CommonParameter.url_root + "/service/showService")
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
                    JSONArray json = jsonObject.getJSONArray("data");

                    for (int i = 0; i < json.length(); i++) {
                        JSONObject object = json.getJSONObject(i);
                        Map<String, Object> maps = new HashMap<String, Object>();

                        maps.put("desc", object.getString("desc"));
                        maps.put("id", object.getInt("id"));
                        maps.put("icon", object.getString("icon"));
                        maps.put("name", object.getString("name"));
                        maps.put("price", object.getDouble("price"));
                        list.add(maps);
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }

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
                    IdleAdapter idleAdapter = new IdleAdapter(getApplicationContext(),list);
                    listView.setAdapter(idleAdapter);
//                    IdleAdapter list_Item=new IdleAdapter(IdleActivity.this);
//                    listView.setAdapter(list_Item);
                    idleAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    Log.d("Handler", "数据错误！");
                    break;

            }
        }
    };
}
