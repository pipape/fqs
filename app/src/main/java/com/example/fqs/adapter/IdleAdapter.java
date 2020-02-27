package com.example.fqs.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fqs.CommonParameter;
import com.example.fqs.R;

import java.util.List;
import java.util.Map;

public class IdleAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String,Object>> list;
    private LayoutInflater layoutInflater;
    public IdleAdapter(Context context,List<Map<String,Object>> list)
    {
        this.context=context;
        this.list=list;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        System.out.println("view = " + view);
        Log.d("text", CommonParameter.url_root + "/" + list.get(i).get("icon"));
        ViewHolder viewHolder = null;
        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.care_item,null);
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.care_item,null);

            viewHolder.name = view.findViewById(R.id.goods_name);
            viewHolder.price = view.findViewById(R.id.goods_price);
            viewHolder.desc = view.findViewById(R.id.goods_desc);
            viewHolder.imageView=view.findViewById(R.id.goods_img);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.desc.setText(list.get(i).get("desc").toString());
        viewHolder.name.setText(list.get(i).get("name").toString());
        viewHolder.price.setText(String.valueOf(list.get(i).get("price")));
        viewHolder.imageView.setImageResource(R.drawable.bg_login);
        if(!TextUtils.isEmpty(list.get(i).get("icon").toString()))
        {
           
            Glide.with(context).load(CommonParameter.url_root+"/"+list.get(i).get("icon").toString()).into(viewHolder.imageView);
        }
        else
        {
            
            viewHolder.imageView.setImageResource(R.drawable.bg_login);
        }
        return view;
    }

    final class ViewHolder {
        TextView name;
        TextView price;
        ImageView imageView;
        TextView desc;
    }

}


