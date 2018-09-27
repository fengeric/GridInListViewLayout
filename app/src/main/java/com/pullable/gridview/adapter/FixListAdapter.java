package com.pullable.gridview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pullable.gridview.FixAreaBean;
import com.pullable.gridview.GridItemClick;
import com.pullable.gridview.LogUtil;
import com.pullable.gridview.MyGridView;
import com.pullable.gridview.R;

import java.util.List;


public class FixListAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	public List<FixAreaBean> list_datas;// 整体listview的数据源
	private FixGridAdapter reAdapter;// GridView的适配器
	private GridItemClick gridItemClick;

	public FixListAdapter(Context context, List<FixAreaBean> arrayList,
                          GridItemClick gridItemClick) {
		this.context = context;
		this.list_datas = arrayList;
		this.gridItemClick = gridItemClick;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list_datas.size();
	}

	@Override
	public Object getItem(int position) {
		return list_datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		try {
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = inflater.inflate(
						R.layout.item_fix_list_adapter, null);
				viewHolder.titleName = (TextView) convertView
						.findViewById(R.id.titlename_tv);
				viewHolder.switchImag = (ImageButton) convertView
						.findViewById(R.id.titleimag);
				viewHolder.childGridView = (MyGridView) convertView
						.findViewById(R.id.gridview_item_recommend);
				viewHolder.view = convertView.findViewById(R.id.middle_view);
				viewHolder.listItemLayout = (LinearLayout) convertView
						.findViewById(R.id.list_item_layout);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			final FixAreaBean keyBean = list_datas.get(position);
			viewHolder.titleName.setText(keyBean.fixTitle);
			reAdapter = new FixGridAdapter(context, keyBean.fixAreaNames,
					gridItemClick);
			viewHolder.childGridView.setAdapter(reAdapter);

			setViewVisiblity(viewHolder.titleName, viewHolder.childGridView,
					viewHolder.switchImag, viewHolder.view, keyBean.isOpen);

			changeColor(viewHolder.listItemLayout, viewHolder.titleName,
					viewHolder.childGridView, viewHolder.switchImag,
					viewHolder.view, keyBean);
		} catch (Exception e) {
			LogUtil.e(getClass(), "public View getView(final int position", e);
		}
		return convertView;
	}

	private void changeColor(final LinearLayout listItemLayout,
                             final TextView titleName, final MyGridView childGridView,
                             final ImageView switchImag, final View view,
                             final FixAreaBean keyBean) {
		listItemLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (keyBean.isOpen) {
					keyBean.isOpen = false;
				} else {
					keyBean.isOpen = true;
				}
				setViewVisiblity(titleName, childGridView, switchImag, view,
						keyBean.isOpen);
			}
		});
	}

	/**设置gridview的显示和隐藏
	 * @param titleName
	 * @param childGridView
	 * @param switchImag
	 * @param view
	 * @param isVisible
	 */
	private void setViewVisiblity(TextView titleName, MyGridView childGridView,
                                  ImageView switchImag, View view, boolean isVisible) {
		try {
			if (isVisible) {
				titleName.setTextColor(Color.argb(255, 198, 181, 112));
				switchImag.setImageResource(R.drawable.record_up);
				view.setBackgroundColor(Color.argb(255, 198, 181, 112));
				childGridView.setVisibility(View.VISIBLE);
			} else {
				titleName.setTextColor(Color.argb(255, 91, 91, 91));
				switchImag.setImageResource(R.drawable.record_down);
				view.setBackgroundColor(Color.argb(255, 230, 230, 230));
				childGridView.setVisibility(View.GONE);
			}

		} catch (Exception e) {
			LogUtil.e(getClass(), "setViewVisiblity", e);
		}
	}

	public class ViewHolder {
		TextView titleName;
		MyGridView childGridView;
		ImageButton switchImag;
		View view;
		LinearLayout listItemLayout;
	}

}
