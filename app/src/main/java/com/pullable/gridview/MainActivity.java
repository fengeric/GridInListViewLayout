package com.pullable.gridview;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.pullable.gridview.bean.FixAreaBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    static final int MENU_SET_MODE = 0;

    private LinkedList<String> mListItems;
    private PullToRefreshGridView mPullRefreshGridView;
    private GridView mGridView;
    private ArrayAdapter<String> mAdapter;
    ArrayList<FixAreaBean.FixContentBean> list_selected_datas = new ArrayList<>();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr_grid);

        setInitData();
        
        Intent in = new Intent(MainActivity.this, GridInListViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", list_selected_datas);
        in.putExtras(bundle);
        startActivity(in);
        
        mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.pull_refresh_grid);
        mGridView = mPullRefreshGridView.getRefreshableView();

        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                Toast.makeText(MainActivity.this, "Pull Down!", Toast.LENGTH_SHORT).show();
                new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                Toast.makeText(MainActivity.this, "Pull Up!", Toast.LENGTH_SHORT).show();
                new GetDataTask().execute();
            }

        });

        mListItems = new LinkedList<String>();

        TextView tv = new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setText("Empty View, Pull Down/Up to Add Items");
        mPullRefreshGridView.setEmptyView(tv);

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);
        mGridView.setAdapter(mAdapter);
    }
    
    private void setInitData(){
        try {
            for (int i = 0; i < 2; i++) {
                for (int i1 = 0; i1 < 5; i1++) {
                    FixAreaBean.FixContentBean fixContentBean = new FixAreaBean.FixContentBean();
                    fixContentBean.spaceName = "内容" + (int) (Math.random()*30) + "-" + (int) (Math.random()*10);

                    list_selected_datas.add(fixContentBean);
                }
            }
        } catch (Exception e) {
          LogUtil.e(getClass(), "setInitData", e);
        }
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            return mStrings;
        }

        @Override
        protected void onPostExecute(String[] result) {
            mListItems.addFirst("Added after refresh...");
            mListItems.addAll(Arrays.asList(result));
            mAdapter.notifyDataSetChanged();

            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshGridView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_SET_MODE, 0,
                mPullRefreshGridView.getMode() == PullToRefreshBase.Mode.BOTH ? "Change to MODE_PULL_DOWN"
                        : "Change to MODE_PULL_BOTH");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem setModeItem = menu.findItem(MENU_SET_MODE);
        setModeItem.setTitle(mPullRefreshGridView.getMode() == PullToRefreshBase.Mode.BOTH ? "Change to MODE_PULL_FROM_START"
                : "Change to MODE_PULL_BOTH");

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_SET_MODE:
                mPullRefreshGridView
                        .setMode(mPullRefreshGridView.getMode() == PullToRefreshBase.Mode.BOTH ? PullToRefreshBase.Mode.PULL_FROM_START
                                : PullToRefreshBase.Mode.BOTH);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private String[] mStrings = {"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler"};
}
