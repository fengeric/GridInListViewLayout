package com.pullable.gridview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pullable.gridview.GridItemClick;
import com.pullable.gridview.LogUtil;
import com.pullable.gridview.R;

import java.util.List;


public class FixGridAdapter extends BaseAdapter {

    private Context context;// 上下文
    private LayoutInflater inflater;
    private List<String> list_name;
    private GridItemClick gridItemClick;

    public FixGridAdapter(Context context, List<String> list_name, GridItemClick gridItemClick) {
        this.context = context;
        this.gridItemClick = gridItemClick;
        this.list_name = list_name;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return list_name.size();
    }

    public Object getItem(int position) {
        return list_name.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        try {
            MyHolder holder = null;
            if (convertView == null) {
                holder = new MyHolder();
                convertView = inflater.inflate(
                        R.layout.item_fix_grid_adapter, null);
                holder.tv_name = (TextView) convertView
                        .findViewById(R.id.recommend_text);
                convertView.setTag(holder);
            } else {
                holder = (MyHolder) convertView.getTag();
            }

            holder.tv_name.setText(list_name.get(position));

            convertView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    gridItemClick.gridItemClick(list_name.get(position));
                }
            });
        } catch (Exception e) {
            LogUtil.e(getClass(), "public View getView(final int position", e);
        }

        return convertView;
    }

    class MyHolder {
        TextView tv_name;
    }
}
