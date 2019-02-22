package com.laughter.views.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.laughter.views.R;
import com.laughter.views.fragment.CheckViewFragment;
import com.laughter.views.fragment.LetterSlideBarFragment;
import com.laughter.views.fragment.PieViewFragment;
import com.laughter.views.fragment.StepViewFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者： laughter_jiang
 * 创建时间： 2019/1/3
 * 描述： com.laughter.views.activity
 */

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.view_list)
    ListView mListView;

    private final String PIEVIEW = "PieView";
    private final String CHECKVIEW = "CheckView";
    private final String STEPVIEW = "QQStepView";
    private final String LETTERSLIDEBAR = "LetterSlideBar";

    private List<String> viewList = new ArrayList<>();
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        viewList.add(PIEVIEW);
        viewList.add(CHECKVIEW);
        viewList.add(STEPVIEW);
        viewList.add(LETTERSLIDEBAR);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, viewList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit);
        switch (viewList.get(position)) {
            case PIEVIEW:
                fragment = manager.findFragmentByTag(PIEVIEW);
                if (fragment == null){
                    fragment = new PieViewFragment();
                    transaction.add(R.id.view_layout, fragment, PIEVIEW);
                }
                transaction.show(fragment);
                break;
            case CHECKVIEW:
                fragment = manager.findFragmentByTag(CHECKVIEW);
                if (fragment == null){
                    fragment = new CheckViewFragment();
                    transaction.add(R.id.view_layout, fragment, PIEVIEW);
                }
                transaction.show(fragment);
                break;
            case STEPVIEW:
                fragment = manager.findFragmentByTag(STEPVIEW);
                if (fragment == null){
                    fragment = new StepViewFragment();
                    transaction.add(R.id.view_layout, fragment, PIEVIEW);
                }
                transaction.show(fragment);
                break;
            case LETTERSLIDEBAR:
                fragment = manager.findFragmentByTag(LETTERSLIDEBAR);
                if (fragment == null){
                    fragment = new LetterSlideBarFragment();
                    transaction.add(R.id.view_layout, fragment, PIEVIEW);
                }
                transaction.show(fragment);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (fragment != null && fragment.isVisible()){
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit);
            transaction.remove(fragment);
            transaction.commit();
        }else {
            finish();
        }
    }
}
