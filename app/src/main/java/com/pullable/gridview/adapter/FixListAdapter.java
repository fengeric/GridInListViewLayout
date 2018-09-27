package com.pullable.gridview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pullable.gridview.GridItemClick;
import com.pullable.gridview.LogUtil;
import com.pullable.gridview.MyGridView;
import com.pullable.gridview.R;
import com.pullable.gridview.bean.FixAreaBean;

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
				viewHolder.listItemLayout = (LinearLayout) convertView
						.findViewById(R.id.list_item_layout);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			final FixAreaBean fixAreaBean = list_datas.get(position);
			viewHolder.titleName.setText(fixAreaBean.fixTitle);
			reAdapter = new FixGridAdapter(context, fixAreaBean.fixAreaNames,
					gridItemClick);
			viewHolder.childGridView.setAdapter(reAdapter);
			reAdapter.setCallBack(new FixGridAdapter.CallBack() {
				@Override
				public void onItemClick(boolean isPlus) {
					if (isPlus) {
						fixAreaBean.selectedNum++;
					} else {
						fixAreaBean.selectedNum --;
					}

					//setTextTitleColor(viewHolder.titleName, fixAreaBean.selectedNum);
				}
			});

			setTextTitleColor(viewHolder.titleName, fixAreaBean.selectedNum);
			setViewVisiblity(viewHolder.childGridView, viewHolder.switchImag, fixAreaBean.isOpen);

			changeColor(viewHolder.listItemLayout, viewHolder.childGridView, viewHolder.switchImag, fixAreaBean);

			/*viewHolder.childGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

					TextView tv_name = (TextView) view.findViewById(R.id.recommend_text);

					GradientDrawable myGrad = (GradientDrawable) tv_name.getBackground();

					boolean isChecked =(Boolean) tv_name.getTag(R.id.content_checked);
					if (isChecked) {
						tv_name.setTextColor(context.getResources().getColor(R.color.select_stroke_color));
						myGrad.setColor(context.getResources().getColor(R.color.white));
					} else {
						tv_name.setTextColor(context.getResources().getColor(R.color.white));
						myGrad.setColor(context.getResources().getColor(R.color.colro_checked));
					}

					tv_name.setTag(R.id.content_checked, !isChecked);
				}
			});*/
		} catch (Exception e) {
			LogUtil.e(getClass(), "public View getView(final int position", e);
		}
		return convertView;
	}
	
	 /**
	  * selectedNum 子元素被选中的个数
	  */
	private void setTextTitleColor(final TextView textViewTitle, int selectedNum){
		try {
			if (selectedNum > 0) {
				textViewTitle.setTextColor(context.getResources().getColor(R.color.colro_checked));
			} else {
				textViewTitle.setTextColor(context.getResources().getColor(R.color.select_new_light_purple));
			}
		} catch (Exception e) {
		  LogUtil.e(getClass(), "setTextTitleColor", e);
		}
	}

	private void changeColor(final LinearLayout listItemLayout, final MyGridView childGridView,
                             final ImageView switchImag, final FixAreaBean keyBean) {
		listItemLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (keyBean.isOpen) {
					keyBean.isOpen = false;
				} else {
					keyBean.isOpen = true;
				}
				setViewVisiblity(childGridView, switchImag , keyBean.isOpen);
			}
		});
	}

	/**设置gridview的显示和隐藏
	 * @param childGridView
	 * @param switchImag
	 * @param isVisible
	 */
	private void setViewVisiblity(MyGridView childGridView,
                                  ImageView switchImag, boolean isVisible) {
		try {
			if (isVisible) {
				switchImag.setImageResource(R.drawable.record_up);
				childGridView.setVisibility(View.VISIBLE);
			} else {
				switchImag.setImageResource(R.drawable.record_down);
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
		LinearLayout listItemLayout;
	}

}
