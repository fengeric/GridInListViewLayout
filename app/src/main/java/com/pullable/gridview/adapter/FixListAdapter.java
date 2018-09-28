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

import com.pullable.gridview.LogUtil;
import com.pullable.gridview.MyGridView;
import com.pullable.gridview.R;
import com.pullable.gridview.bean.FixAreaBean;

import java.util.ArrayList;
import java.util.List;


public class FixListAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	public List<FixAreaBean> list_datas;// 整体listview的数据源
	private FixGridAdapter reAdapter;// GridView的适配器
	private ArrayList<FixAreaBean.FixContentBean> list_selected_datas = new ArrayList<>();// 选中的数据集合

	public ArrayList<FixAreaBean.FixContentBean> getSelectedDatas(){
		return list_selected_datas;
	}

	public void setSelectedDatas(ArrayList<FixAreaBean.FixContentBean> list_selected_datas){
		try {
		    this.list_selected_datas.addAll(list_selected_datas);
		} catch (Exception e) {
		  LogUtil.e(getClass(), "setSelectedDatas", e);
		}
	}

	public FixListAdapter(Context context, List<FixAreaBean> arrayList) {
		this.context = context;
		this.list_datas = arrayList;
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
			reAdapter = new FixGridAdapter(context, fixAreaBean.fixAreaNames);
			viewHolder.childGridView.setAdapter(reAdapter);

			setTitleColorChangeListener(reAdapter, fixAreaBean, viewHolder.titleName);

			setTextTitleColor(viewHolder.titleName, fixAreaBean.selectedNum);
			setViewVisiblity(viewHolder.childGridView, viewHolder.switchImag, fixAreaBean.isOpen);

			changeColor(viewHolder.listItemLayout, viewHolder.childGridView, viewHolder.switchImag, fixAreaBean);
		} catch (Exception e) {
			LogUtil.e(getClass(), "public View getView(final int position", e);
		}
		return convertView;
	}

	private void setTitleColorChangeListener(FixGridAdapter reAdapter, final FixAreaBean fixAreaBean, final TextView tvTitle){
		try {
			reAdapter.setCallBack(new FixGridAdapter.CallBack() {
				@Override
				public void onItemClick(FixAreaBean.FixContentBean bean) {

					fixAreaBean.selectedNum = bean.isChecked ? ++fixAreaBean.selectedNum : --fixAreaBean.selectedNum;

					setTextTitleColor(tvTitle, fixAreaBean.selectedNum);

					if (bean.isChecked) {
						list_selected_datas.add(bean);
					} else {
						for (int i = 0; i < list_selected_datas.size(); i++) {
							if (bean.spaceName.equals(list_selected_datas.get(i).spaceName)) {
								list_selected_datas.remove(i);
								break;
							}
						}
					}
				}
			});
		} catch (Exception e) {
		  LogUtil.e(getClass(), "setTitleColorChangeListener", e);
		}
	}
	
	 /**
	  * selectedNum 子元素被选中的个数
	  */
	private void setTextTitleColor(final TextView textViewTitle, int selectedNum){
		try {
			if (selectedNum > 0) {
				textViewTitle.setTextColor(context.getResources().getColor(R.color.colro_checked));
			} else {
				textViewTitle.setTextColor(context.getResources().getColor(R.color.select_stroke_color));
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
