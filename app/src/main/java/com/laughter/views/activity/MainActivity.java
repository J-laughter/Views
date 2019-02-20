package com.laughter.views.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.laughter.views.R;
import com.laughter.views.views.CheckView;
import com.laughter.views.model.PieData;
import com.laughter.views.views.PieView;
import com.laughter.views.views.QQStepView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者： laughter_jiang
 * 创建时间： 2019/1/3
 * 描述： com.laughter.views.activity
 */

public class MainActivity extends AppCompatActivity {

    private List<PieData> mDataList;
    private final static String CHECK = "Check";
    private final static String UNCHECK = "UnCheck";

    @BindView(R.id.pie_view) PieView mPieView;
    @BindView(R.id.check_view) CheckView mCheckView;
    @BindView(R.id.but_check) Button butCheck;
    @BindView(R.id.step_view) QQStepView mStepView;
    @BindView(R.id.edit_step) EditText editStep;
    @BindView(R.id.but_update_step) Button butStep;

    @BindColor(R.color.colorTheme) int mThemeColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mPieView = findViewById(R.id.pie_view);
        mDataList = new ArrayList<>();
        mDataList.add(new PieData("jia", 50));
        mDataList.add(new PieData("jia", 100));
        mDataList.add(new PieData("jia", 150));
        mDataList.add(new PieData("jia", 200));

        mPieView.setData(mDataList);
        mPieView.setStartAngle(0);

        mCheckView.setThemeColor(mThemeColor);
        mCheckView.setAnimDuration(650);

        mStepView.setStepNumMax(10000);
    }

    @OnClick({R.id.but_check, R.id.but_update_step})
    public void click(View v) {
        switch (v.getId()){
            case R.id.but_check:
                if (mCheckView.getAnimState() == 0 && mCheckView.isChecked()){
                    butCheck.setText(CHECK);
                    mCheckView.unCheck();
                }else if (mCheckView.getAnimState() == 0 && (!mCheckView.isChecked())){
                    butCheck.setText(UNCHECK);
                    mCheckView.check();
                }
                break;
            case R.id.but_update_step:
                int curStepNum = 0;
                if (!TextUtils.isEmpty(editStep.getText())){
                    curStepNum = Integer.valueOf(editStep.getText().toString());
                }
                ValueAnimator valueAnimator = ObjectAnimator.ofInt(0, curStepNum);
                valueAnimator.setDuration(1000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int curStepNum = (int)animation.getAnimatedValue();
                        mStepView.setCurStepNum(curStepNum);
                    }
                });
                valueAnimator.start();
                break;
            default:
                break;
        }
    }
}
