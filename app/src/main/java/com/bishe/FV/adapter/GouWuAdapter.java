package com.bishe.FV.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.bishe.FV.R;
import com.bishe.FV.net.NetHttpData;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GouWuAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater = null;
	private ArrayList<HashMap<String, Object>> arrayList_cart = new ArrayList<HashMap<String,Object>>();

	/**
	 * Adapter??????
	 *
	 */
	public GouWuAdapter(Context context,ArrayList<HashMap<String, Object>> arrayList_cart){
		this.context = context;
		this.arrayList_cart = arrayList_cart;
	}

	/**
	 * ?????Popupwindow
	 *
	 * @param
	 */
	private void initPopupWindow(LayoutInflater inflater2) {
		// TODO Auto-generated method stub

	}

	public GouWuAdapter (Context context) {
		this.context = context;

	}
	/**
	 * ???????
	 */
	@Override
	public int getCount() {
		return (arrayList_cart != null && arrayList_cart.size() == 0) ? 0: arrayList_cart.size();
	}

	/**
	 * ???Item????
	 */
	@Override
	public Object getItem(int position) {
		return arrayList_cart.get(position);
	}

	/**
	 * ???Item??ID
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		GouWu g = null;
		if (convertView==null) {
			convertView = inflater.from(context).inflate(R.layout.aty_gouwu_itme,null);
			g = new GouWu();

			g.img = (SmartImageView) convertView.findViewById(R.id.aty_gouwu_img);
			g.name = (TextView) convertView.findViewById(R.id.aty_gouwu_tv_name);
			g.price = (TextView) convertView.findViewById(R.id.aty_gouwu_tv_price);
			convertView.setTag(g);

		}else {
			g = (GouWu) convertView.getTag();
		}
		// ???????????
		if (arrayList_cart.size()!=0) {
			g.img.setImageUrl(NetHttpData.dataIp + "/LiAng/images/" +arrayList_cart.get(position).get("images").toString());
			g.name.setText(arrayList_cart.get(position).get("name").toString());
			g.price.setText(arrayList_cart.get(position).get("price").toString());

		}
		return convertView;
	}

	static class GouWu{
		SmartImageView img;
		TextView name,price;
	}
}
