package com.laughter.views.fragment;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.laughter.views.R;
import com.laughter.views.views.QQStepView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StepViewFragment extends Fragment {

    @BindView(R.id.step_view) QQStepView mStepView;
    @BindView(R.id.edit_step) EditText editStep;
    @BindView(R.id.but_update_step) Button butStep;

    private Unbinder mUnBinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_view, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mStepView.setStepNumMax(10000);
    }

    @OnClick({R.id.but_close, R.id.but_update_step})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but_close:
                if (getActivity() != null){
                    getActivity().onBackPressed();
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

    @Override
    public void onDestroy() {
        mUnBinder.unbind();
        super.onDestroy();
    }
}
