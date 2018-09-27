package com.pullable.gridview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.pullable.gridview.adapter.FixListAdapter;
import com.pullable.gridview.bean.FixAreaBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/9/27.
 */

public class GridInListViewActivity extends Activity implements GridItemClick {

    private ArrayList<FixAreaBean> list_datas = new ArrayList<>();
    private ListView listView;
    private FixListAdapter fixListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_in_list);

        setData();
        initListView();

    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.list_view_fix_area);
        fixListAdapter = new FixListAdapter(this, list_datas, this);
        listView.setAdapter(fixListAdapter);
    }

    private void setData() {
        for (int i = 0; i < 30; i++) {
            FixAreaBean fixAreaBean = new FixAreaBean();
            fixAreaBean.fixTitle = "title" + i;

            for (int i1 = 0; i1 < 10; i1++) {
                FixAreaBean.FixContentBean fixContentBean = new FixAreaBean.FixContentBean();
                fixContentBean.spaceName = "内容" + i + "-" + i1;

                fixAreaBean.fixAreaNames.add(fixContentBean);
            }

            list_datas.add(fixAreaBean);
        }

    }

    @Override
    public void gridItemClick(String name) {

    }
}
