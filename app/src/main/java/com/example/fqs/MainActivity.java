package com.example.fqs;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fqs.main_fragment.CareActivity;
import com.example.fqs.main_fragment.HomeActivity;
import com.example.fqs.main_fragment.IdleActivity;
import com.example.fqs.main_fragment.MyActivity;
import com.example.fqs.main_fragment.TopicActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private RadioGroup radioGroup;
    private RadioButton button_1;
    private RadioButton button_2;
    private RadioButton button_3;
    private RadioButton button_4;
    private RadioButton button_5;
    private HomeActivity main;
    private IdleActivity idle;
    private CareActivity care;
    private TopicActivity topic;
    private MyActivity my;
    private List<FrameLayout> list;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_bonav);
        //初始化页面
        initView();
    }

    //初始化页面
    private void initView() {
        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        //找到四个按钮
        button_1 = (RadioButton) findViewById(R.id.button_1);
        button_2 = (RadioButton) findViewById(R.id.button_2);
        button_3 = (RadioButton) findViewById(R.id.button_3);
        button_4 = (RadioButton) findViewById(R.id.button_4);
        button_5=(RadioButton) findViewById(R.id.button_5);

        //创建Fragment对象及集合
        main=new HomeActivity();
        idle=new IdleActivity();
        care=new CareActivity();
        topic=new TopicActivity();
        my=new MyActivity();

        //将Fragment对象添加到list中
        //list.add(main);
        //list.add(care);
        //list.add(idle);
        //list.add(topic);
        //list.add(my);

        //设置RadioGroup开始时设置的按钮，设置第一个按钮为默认值
        radioGroup.check(R.id.button_1);


        //设置按钮点击监听
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);

        //初始时向容器中添加第一个Fragment对象
        addFragment(main);
    }

    @Override
    public void finish() {
        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        viewGroup.removeAllViews();
        super.finish();
    }

    //点击事件处理
    @Override
    public void onClick(View v) {
        //我们根据参数的id区别不同按钮
        //不同按钮对应着不同的Fragment对象页面
        switch (v.getId()) {
            case R.id.button_1:
                addFragment(main);
                break;
            case R.id.button_2:
                addFragment(care);
                break;
            case R.id.button_3:
                addFragment(idle);
                break;
            case R.id.button_4:
                addFragment(topic);
                break;
            case R.id.button_5:
                addFragment(my);
                break;
            default:
                break;
        }

    }

    //向Activity中添加Fragment的方法
    public void addFragment(Fragment fragment) {

        //获得Fragment管理器
        FragmentManager fragmentManager = getSupportFragmentManager();
        //使用管理器开启事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //使用事务替换Fragment容器中Fragment对象
        fragmentTransaction.replace(R.id.framelayout,fragment);
        //提交事务，否则事务不生效
        fragmentTransaction.commit();
    }



}
