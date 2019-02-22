package com.laughter.views.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.laughter.views.R;
import com.laughter.views.model.PieData;
import com.laughter.views.views.PieView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PieViewFragment extends Fragment {

    @BindView(R.id.pie_view) PieView mPieView;

    private Unbinder mUnBinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pie_view, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<PieData> mDataList = new ArrayList<>();
        mDataList.add(new PieData("jia", 50));
        mDataList.add(new PieData("jia", 100));
        mDataList.add(new PieData("jia", 150));
        mDataList.add(new PieData("jia", 200));

        mPieView.setData(mDataList);
        mPieView.setStartAngle(0);
    }

    @OnClick(R.id.but_close)
    public void onClick() {
        if (getActivity() != null){
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        mUnBinder.unbind();
        super.onDestroy();
    }
}
