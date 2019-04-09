package com.laughter.views.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laughter.views.R;
import com.laughter.views.adapter.ArticleAdapter;
import com.laughter.views.model.Article;
import com.laughter.views.util.HttpUtil;
import com.laughter.views.util.JsonUtil;
import com.laughter.views.views.LoadingListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoadingListViewFragment extends Fragment implements LoadingListView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, HttpUtil.HttpCallbackListener {

    @BindView(R.id.srl) SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.llv) LoadingListView mListView;

    @BindColor(R.color.colorPrimary) int colorPrimary;

    private Unbinder mUnbinder;
    private List<Article> articles;
    private ArticleAdapter mAdapter;

    private int curPage = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading_list_view, parent, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRefreshLayout.setColorSchemeColors(colorPrimary);
        mRefreshLayout.setOnRefreshListener(this);

        mListView.addFooterView(LayoutInflater.from(getContext()).inflate(R.layout.layout_footer_view, mListView, false));
        mListView.setOnLoadMoreListener(this);

        articles = new ArrayList<>();
        mAdapter = new ArticleAdapter(getContext(), articles);

        String url = "article/list/" + curPage +"/json";
        HttpUtil.get(url, 0, null, this);
    }

    @Override
    public void onRefresh() {
        curPage = 0;
        articles.clear();
        mAdapter.notifyDataSetChanged();
        String url = "article/list/" + curPage +"/json";
        HttpUtil.get(url, 0, null, this);
    }

    @Override
    public void onLoadMore() {
        curPage ++;
        String url = "article/list/" + curPage +"/json";
        HttpUtil.get(url, 0, null, this);
    }

    @Override
    public void onFinish(int requestId, String response, String cookie) {
        if (JsonUtil.getErrorCode(response) == 0){
            articles.addAll(JsonUtil.getArticles(response));
        }
        (getActivity()).runOnUiThread(() -> {
            if (mListView.getAdapter() == null){
                mListView.setAdapter(mAdapter);
            }else {
                mAdapter.notifyDataSetChanged();
            }
            mRefreshLayout.setRefreshing(false);
            mListView.setLoadCompleted();
        });
    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
    }

    @OnClick({R.id.but_close})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but_close:
                if (getActivity() != null){
                    getActivity().onBackPressed();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
