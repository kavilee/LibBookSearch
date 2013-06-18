package com.daxia.util;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ModelListViewAdapter<T> extends BaseAdapter {

	/**
	 * 绑定事件监听接口
	 * 
	 * @author zhong
	 * 
	 * @param <T>
	 */
	public interface OnBindingListener<T> {
		void OnBinding(int position, T model, View layout);
	}

	Context context;
	List<T> data;
	int resource;
	private OnBindingListener<T> listener;

	/**
	 * 构造
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 * @param listener
	 */
	public ModelListViewAdapter(Context context, List<T> data, int resource,
			OnBindingListener<T> listener) {
		Initial(context, data, resource);
		this.listener = listener;
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	private void Initial(Context context, List<T> data, int resource) {
		this.context = context;
		this.data = data;
		this.resource = resource;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View layout, ViewGroup parent) {
		T model = data.get(position);
		if (layout == null) {
			layout = View.inflate(this.context, this.resource, null);
		}
		listener.OnBinding(position, model, layout);
		return layout;
	}

}
