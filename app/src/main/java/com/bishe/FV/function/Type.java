package com.bishe.FV.function;


import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bishe.FV.R;
import com.bishe.FV.adapter.HomeAdapter;
import com.bishe.FV.adapter.MenuAdapter;
import com.bishe.FV.entity.Catagories;
import com.facebook.drawee.backends.pipeline.Fresco;

import com.alibaba.fastjson.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Type extends Activity {

	private List<String> menuList = new ArrayList<>();
	private List<Catagories.DataBean> homeList = new ArrayList<>();
	private List<Integer> showTitle;

	private ListView lv_menu;
	private ListView lv_home;

	private MenuAdapter menuAdapter;
	private HomeAdapter homeAdapter;
	private int currentItem;

	private TextView tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.type);
		Fresco.initialize(this);
		initView();
		loadData();
	}


	private void loadData() {

		String json = getJson(this, "category.json");
		Catagories categoryBean = JSONObject.parseObject(json, Catagories.class);
		showTitle = new ArrayList<>();
		for (int i = 0; i < categoryBean.getData().size(); i++) {
			Catagories.DataBean dataBean = categoryBean.getData().get(i);
			menuList.add(dataBean.getModuleTitle());
			showTitle.add(i);
			homeList.add(dataBean);
		}
		tv_title.setText(categoryBean.getData().get(0).getModuleTitle());

		menuAdapter.notifyDataSetChanged();
		homeAdapter.notifyDataSetChanged();
	}

	private void initView() {
		lv_menu = (ListView) findViewById(R.id.lv_menu);
		tv_title = (TextView) findViewById(R.id.tv_titile);
		lv_home = (ListView) findViewById(R.id.lv_home);
		menuAdapter = new MenuAdapter(this, menuList);
		lv_menu.setAdapter(menuAdapter);

		homeAdapter = new HomeAdapter(this, homeList);
		lv_home.setAdapter(homeAdapter);

		lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				menuAdapter.setSelectItem(position);
				menuAdapter.notifyDataSetInvalidated();
				tv_title.setText(menuList.get(position));
				lv_home.setSelection(showTitle.get(position));
			}
		});


		lv_home.setOnScrollListener(new AbsListView.OnScrollListener() {
			private int scrollState;

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				this.scrollState = scrollState;
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
								 int visibleItemCount, int totalItemCount) {
				if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
					return;
				}
				int current = showTitle.indexOf(firstVisibleItem);
//				lv_home.setSelection(current);
				if (currentItem != current && current >= 0) {
					currentItem = current;
					tv_title.setText(menuList.get(currentItem));
					menuAdapter.setSelectItem(currentItem);
					menuAdapter.notifyDataSetInvalidated();
				}
			}
		});
	}

	/**
	 * 得到json文件中的内容
	 *
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String getJson(Context context, String fileName) {
		StringBuilder stringBuilder = new StringBuilder();
		//获得assets资源管理器
		AssetManager assetManager = context.getAssets();
		//使用IO流读取json文件内容
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
					assetManager.open(fileName), "utf-8"));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}



}
