package com.example.fqs;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    List<Conps> conpsList=new ArrayList<>();

    public MyAdapter(List<Conps> conpsList) {
        this.conpsList = conpsList;
    }

    @Override
    public int getCount() {
        return conpsList.size();
    }

    @Override
    public Object getItem(int i) {
        return conpsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
