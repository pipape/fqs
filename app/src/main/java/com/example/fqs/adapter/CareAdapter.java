package com.example.fqs.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.fqs.enity.CareItem;

import java.util.List;

public class CareAdapter extends BaseAdapter {
    List<CareItem> careItemList;

    public CareAdapter(List<CareItem> careItemList)
    {
        this.careItemList=careItemList;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
