package com.laughter.views.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.laughter.views.R;
import com.laughter.views.views.CheckView;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CheckViewFragment extends Fragment {

    @BindView(R.id.check_view) CheckView mCheckView;
    @BindView(R.id.but_check) Button butCheck;
    @BindColor(R.color.colorTheme) int mThemeColor;

    private final String CHECK = "Check";
    private final String UNCHECK = "UnCheck";

    private Unbinder mUnBinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_view, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCheckView.setThemeColor(mThemeColor);
        mCheckView.setAnimDuration(650);
    }

    @OnClick({R.id.but_close, R.id.but_check})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but_close:
                if (getActivity() != null){
                    getActivity().onBackPressed();
                }
                break;
            case R.id.but_check:
                if (mCheckView.getAnimState() == 0 && mCheckView.isChecked()){
                    butCheck.setText(CHECK);
                    mCheckView.unCheck();
                }else if (mCheckView.getAnimState() == 0 && (!mCheckView.isChecked())){
                    butCheck.setText(UNCHECK);
                    mCheckView.check();
                }
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
