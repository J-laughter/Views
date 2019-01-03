package com.laughter.views.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.laughter.views.R;
import com.laughter.views.model.PieData;
import com.laughter.views.views.PieView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： laughter_jiang
 * 创建时间： 2019/1/3
 * 版权：
 * 描述： com.laughter.views.activity
 */

public class MainActivity extends AppCompatActivity {

    private List<PieData> mDataList;
    private PieView mPieView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mPieView = findViewById(R.id.pie);
        mDataList = new ArrayList<>();
        mDataList.add(new PieData("jia", 50));
        mDataList.add(new PieData("jia", 100));
        mDataList.add(new PieData("jia", 150));
        mDataList.add(new PieData("jia", 200));

        mPieView.setData(mDataList);
        mPieView.setStartAngle(0);
    }
}
