package com.pullable.gridview.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pullable.gridview.LogUtil;
import com.pullable.gridview.R;
import com.pullable.gridview.bean.FixAreaBean;

import java.util.List;


public class FixGridAdapter extends BaseAdapter {

    private Context context;// 上下文
    private LayoutInflater inflater;
    private List<FixAreaBean.FixContentBean> list_contents;

    public FixGridAdapter(Context context, List<FixAreaBean.FixContentBean> list_name) {
        this.context = context;
        this.list_contents = list_name;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return list_contents.size();
    }

    public Object getItem(int position) {
        return list_contents.get(position);
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

            FixAreaBean.FixContentBean fixContentBean = list_contents.get(position);

            holder.tv_name.setText(fixContentBean.spaceName);

            //holder.tv_name.setTag(R.id.content_checked, fixContentBean.isChecked);

            changeColor(holder.tv_name, fixContentBean);

            setClickListener(holder.tv_name, fixContentBean);
        } catch (Exception e) {
            LogUtil.e(getClass(), "public View getView(final int position", e);
        }

        return convertView;
    }

    private void setClickListener(final TextView tv_name, final FixAreaBean.FixContentBean fixContentBean){
        try {
            tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    fixContentBean.isChecked = !fixContentBean.isChecked;

                    if (callBack != null)
                        callBack.onItemClick(fixContentBean);

                    changeColor(tv_name, fixContentBean);
                }
            });
        } catch (Exception e) {
          LogUtil.e(getClass(), "setClickListener", e);
        }
    }

    private void changeColor(final TextView tv_name, final FixAreaBean.FixContentBean fixContentBean){
        try {
            GradientDrawable myGrad = (GradientDrawable) tv_name.getBackground();

            if (fixContentBean.isChecked) {
                tv_name.setTextColor(context.getResources().getColor(R.color.white));
                myGrad.setColor(context.getResources().getColor(R.color.colro_checked));
            } else {
                tv_name.setTextColor(context.getResources().getColor(R.color.select_stroke_color));
                myGrad.setColor(context.getResources().getColor(R.color.white));
            }
        } catch (Exception e) {
          LogUtil.e(getClass(), "changeColor", e);
        }
    }

    class MyHolder {
        TextView tv_name;
    }

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void onItemClick(FixAreaBean.FixContentBean fixContentBean);
    }
}
