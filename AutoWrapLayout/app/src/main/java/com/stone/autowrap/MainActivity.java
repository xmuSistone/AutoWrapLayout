package com.stone.autowrap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stone.autowrap.lib.AutoWrapLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AutoWrapLayout autoWrapLayout;
    private List<String> dataList = new ArrayList<>();
    private View.OnClickListener itemClickListener;
    private AutoWrapLayout.WrapAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoWrapLayout = (AutoWrapLayout) findViewById(R.id.wrap_layout);
        prepareDataList();
        prepareAdapter();

        autoWrapLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                autoWrapLayout.setAdapter(adapter);
            }
        }, 3000);
        autoWrapLayout.setAdapter(adapter);
    }

    private void prepareAdapter() {
        final LayoutInflater layoutInflater = LayoutInflater.from(this);
        itemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = Integer.parseInt(v.getTag().toString());
                Toast.makeText(MainActivity.this, "itemClick - " + dataList.get(index), Toast.LENGTH_LONG).show();
            }
        };
        adapter = new AutoWrapLayout.WrapAdapter() {

            @Override
            public int getItemCount() {
                return dataList.size();
            }

            @Override
            public TextView onCreateTextView(int index) {
                TextView itemTv = (TextView) layoutInflater.inflate(R.layout.item_wrap_tv, null);
                itemTv.setText(dataList.get(index));
                itemTv.setTag(index);
                itemTv.setOnClickListener(itemClickListener);
                return itemTv;
            }
        };
    }

    private void prepareDataList() {
        dataList.add("猩球崛起3:终极之战");
        dataList.add("蜘蛛侠：英雄归来");
        dataList.add("敦刻尔克");
        dataList.add("星际特工");
        dataList.add("赛车总动员");
        dataList.add("盗梦空间");
        dataList.add("机器人总动员");
        dataList.add("星际穿越");
        dataList.add("加勒比海盗");
        dataList.add("致命魔术");
        dataList.add("复仇者联盟");
        dataList.add("黑客帝国");
        dataList.add("驯龙高手");
        dataList.add("变形金刚");
        dataList.add("环太平洋");
        dataList.add("钢铁侠");
        dataList.add("奇异博士");
        dataList.add("金刚");
        dataList.add("侏罗纪世界");
        dataList.add("普罗米修斯");
        dataList.add("这个男人来自地球");
        dataList.add("神奇女侠");
        dataList.add("蚁人");
        dataList.add("海扁王");
        dataList.add("权力的游戏");
        dataList.add("风之谷");
        dataList.add("铁甲钢拳");
        dataList.add("狄仁杰之通天帝国");
        dataList.add("独立日");
        dataList.add("惊天魔盗团");
        dataList.add("金蝉脱壳");
        dataList.add("木乃伊");
        dataList.add("超人：钢铁之躯");
        dataList.add("九层妖塔");
        dataList.add("金刚：骷髅岛");
        dataList.add("终结者");
        dataList.add("异形：契约");
        dataList.add("移动迷宫");
        dataList.add("哥斯拉");
        dataList.add("星际迷航");
        dataList.add("安德的游戏");
        dataList.add("悟空传");
        dataList.add("永无止境");
        dataList.add("全面回忆");
        dataList.add("少数派报告");
        dataList.add("无敌浩克");
        dataList.add("国家公敌");
        dataList.add("忍者神龟：变种时代");
        dataList.add("异星觉醒");
    }
}
