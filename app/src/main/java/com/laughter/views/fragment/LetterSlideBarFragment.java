package com.laughter.views.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laughter.views.R;
import com.laughter.views.views.LetterSideBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LetterSlideBarFragment extends Fragment implements LetterSideBar.LetterTouchListener {

    @BindView(R.id.tv_letter) TextView tvLetter;
    @BindView(R.id.letter_side_bar)
    LetterSideBar mLetterSideBar;

    private Unbinder mUnBinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_letter_slide_bar, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLetterSideBar.setTouchListener(this);
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

    @Override
    public void onTouch(String letter, boolean isTouching) {
        if (isTouching && !letter.equals("❤") && !letter.equals("#")) {
            tvLetter.setText(letter);
            tvLetter.setVisibility(View.VISIBLE);
        }else {
            tvLetter.setVisibility(View.GONE);
        }
    }
}
