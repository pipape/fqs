package com.example.fqs.main_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fqs.R;
import com.example.fqs.commidity_fragment.BagActivity;
import com.example.fqs.commidity_fragment.ClothesActivity;
import com.example.fqs.commidity_fragment.OrnamentActivity;
import com.example.fqs.commidity_fragment.RepairActivity;
import com.example.fqs.commidity_fragment.ShoeGoodsActivity;

public class CareActivity extends Fragment {
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //注意View对象的重复使用，以便节省资源
        if(mView == null) {
            mView = inflater.inflate(R.layout.activity_care,container,false);
            ImageButton imageButton1=mView.findViewById(R.id.bag_page);
            ImageButton imageButton2=mView.findViewById(R.id.shoes_page);
            ImageButton imageButton3=mView.findViewById(R.id.clothes_page);
            ImageButton imageButton4=mView.findViewById(R.id.orna_page);
            ImageButton imageButton5=mView.findViewById(R.id.repair_page);
            imageButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(),
                            "i am an ImageButton in TitleFragment ! ",
                            Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), BagActivity.class);
                    CareActivity.this.startActivity(i);
                }
            });

            imageButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(),
                            "i am an ImageButton in TitleFragment ! ",
                            Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), ShoeGoodsActivity.class);
                    CareActivity.this.startActivity(i);
                }
            });

            imageButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(),
                            "i am an ImageButton in TitleFragment ! ",
                            Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), ClothesActivity.class);
                    CareActivity.this.startActivity(i);
                }
            });

            imageButton4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(),
                            "i am an ImageButton in TitleFragment ! ",
                            Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), OrnamentActivity.class);
                    CareActivity.this.startActivity(i);
                }
            });

            imageButton5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(),
                            "i am an ImageButton in TitleFragment ! ",
                            Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getContext(), RepairActivity.class);
                    CareActivity.this.startActivity(i);
                }
            });

        }

        return mView;
    }
}
