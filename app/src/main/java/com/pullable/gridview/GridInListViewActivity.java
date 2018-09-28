package com.pullable.gridview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.pullable.gridview.adapter.FixListAdapter;
import com.pullable.gridview.bean.FixAreaBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/9/27.
 */

public class GridInListViewActivity extends Activity {

    private ArrayList<FixAreaBean> list_datas = new ArrayList<>();//整体数据集合
    private ListView listView;// 展示的listview
    private FixListAdapter fixListAdapter;// 展示的adapter

    ArrayList<FixAreaBean.FixContentBean> list_selected_datas = new ArrayList<>();// 已经选中的数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_in_list);

        list_selected_datas =(ArrayList<FixAreaBean.FixContentBean>) getIntent().getSerializableExtra("list");

        setData();

        judgeDatas();

        initListView();

        findViewById(R.id.tv_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LogUtil.v(getClass(), "onClick---" + ":" + fixListAdapter.getSelectedDatas());
                if (list_selected_datas != null && list_selected_datas.size() > 0) {
                    list_selected_datas.clear();
                }

                list_selected_datas.addAll(fixListAdapter.getSelectedDatas());

                /*String s = "";

                for (int i = 0; i < list_selected_datas.size(); i++) {
                    FixAreaBean.FixContentBean bean = list_selected_datas.get(i);
                    s = s + "-" + bean.spaceName;
                }
                LogUtil.v(getClass(), "onClick---" + ":" + s);*/
            }
        });

    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.list_view_fix_area);
        fixListAdapter = new FixListAdapter(this, list_datas);
        fixListAdapter.setSelectedDatas(list_selected_datas);
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
    
     /**
      * 判断数据中是否包含已经选中的数据，是修改ischecked为true,并且让selectedNum+1
      */
    private void judgeDatas(){
        try {
            for (int i = 0; i < list_datas.size(); i++) {
                ArrayList<FixAreaBean.FixContentBean> list_grid_datas = list_datas.get(i).fixAreaNames;

                for (int j = 0; j < list_grid_datas.size(); j++) {
                    FixAreaBean.FixContentBean bean = list_grid_datas.get(j);

                    for (int k = 0; k < list_selected_datas.size(); k++) {
                        if (bean.spaceName.equals(list_selected_datas.get(k).spaceName)) {
                            bean.isChecked = true;
                            list_datas.get(i).selectedNum ++ ;
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
          LogUtil.e(getClass(), "judgeDatas", e);
        }
    }
}
