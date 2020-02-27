package com.example.fqs.main_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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


public class IdleActivity extends Fragment {


    ListView listView;
    private View mView;
    private EditText editText;
    private Button button;


    public ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //注意View对象的重复使用，以便节省资源
        if (mView == null) {
            Log.d("test", "mview");
            mView = inflater.inflate(R.layout.activity_idle, container, false);
            listView =  mView.findViewById(R.id.goods_list);
            editText=mView.findViewById(R.id.edittext);
            button=mView.findViewById(R.id.bt_search);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(TextUtils.isEmpty(editText.getText().toString()))
                    {
                        Toast.makeText(getContext(), "请输入商品名称", Toast.LENGTH_SHORT).show();
                        RequestData();
                    }
                    else{
                        Search(editText.getText().toString());
                    }
                }
            });




            //设置item对象组数据
           // System.out.println("mView1 = " + mView);
            RequestData();

            //IdleAdapter idleAdapter = new IdleAdapter(this.getActivity(), list);
            //listView.setAdapter(idleAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("position", String.valueOf(i));

                    Intent intent = new Intent(getActivity(), ComDetailActivity.class);
                    Log.d("test", list.get(i).get("name").toString());
                    intent.putExtra("id", list.get(i).get("id").toString());
                    intent.putExtra("icon", list.get(i).get("icon").toString());
                    intent.putExtra("desc", list.get(i).get("desc").toString());
                    intent.putExtra("name", list.get(i).get("name").toString());
                    intent.putExtra("price", list.get(i).get("price").toString());
                    intent.putExtra("kind",0);
                    startActivity(intent);
                }
            });

        }
        return mView;
    }



    //搜索请求---------------------------------------------------------------------------------------
    void Search(String name){

        final RequestBody formBody = new FormBody.Builder()
                .add("name",name)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(CommonParameter.url_root + "/goods/search")
                        .post(formBody)
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    String data = response.body().string();
                    Log.d("test", data);
                    jsonDecode(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void jsonDecode(String data)
    {
        if (data != null) {
            try {
                JSONObject jsonObject = new JSONObject(data);
                String code = jsonObject.getString("errCode");
                if (code.equals("0")) {
                    JSONArray json = jsonObject.getJSONArray("data");
                    list.clear();
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
                        handler_search.sendMessage(message);
                    }

                } else {
                    Message message = new Message();
                    message.what = 2;
                    handler_search.sendMessage(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public Handler handler_search = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Log.d("Handler_search", "数据接受正确！");
                    IdleAdapter idleAdapter = new IdleAdapter(getContext(),list);
                    listView.setAdapter(idleAdapter);
//                    IdleAdapter list_Item=new IdleAdapter(IdleActivity.this);
//                    listView.setAdapter(list_Item);
                    idleAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    list.clear();
                    IdleAdapter idleAdapter2 = new IdleAdapter(getContext(),list);
                    listView.setAdapter(idleAdapter2);
                    idleAdapter2.notifyDataSetChanged();
                    Toast.makeText(getContext(), "商品不存在", Toast.LENGTH_SHORT).show();
                    Log.d("Handler_search", "数据错误！");
                    break;

            }
        }
    };

    //初始请求---------------------------------------------------------------------------------------------------

    private void RequestData()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(CommonParameter.url_root + "/goods/showAll")
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
                    IdleAdapter idleAdapter = new IdleAdapter(getContext(),list);
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


    //Adapter


}
