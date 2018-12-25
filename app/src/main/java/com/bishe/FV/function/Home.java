package com.bishe.FV.function;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bishe.FV.R;
import com.bishe.FV.adapter.RenMenGoodsAdapter;
import com.bishe.FV.adapter.ViewPagerAdapter;
import com.bishe.FV.entity.Goods;
import com.bishe.FV.entity.ViewPager;
import com.bishe.FV.net.NetHttpData;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Home extends Activity {

	private RadioButton gengd;
	private android.support.v4.view.ViewPager mViewPager;
	private TextView mIntroTv;
	private LinearLayout mDotLayout;
	private ViewPagerAdapter mViewPagerAdapter;
	private ListView reListView;
	private int page = 1;
	private RenMenGoodsAdapter reMenAdapter;
	private List<Goods> JsonList = new ArrayList<Goods>();

	/**
	 * handler处理定时任务
	 */
	private Handler mMyHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
			mMyHandler.sendEmptyMessageDelayed(0, 3000);
		}
	};

	private List<ViewPager> list = new ArrayList<ViewPager>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_itme);
		mViewPager = (android.support.v4.view.ViewPager) findViewById(R.id.viewPager);
		mIntroTv = (TextView) findViewById(R.id.tv_intro);
		mDotLayout = (LinearLayout) findViewById(R.id.dot_layout);
		reListView = (ListView) findViewById(R.id.zhuye_lv_remen);

		// 热门单件查看更多监听事件
//		gengd = (RadioButton) findViewById(R.id.zhuye_img_add);
//		gengd.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent i = new Intent(Home.this, Type.class);
//				startActivity(i);
//			}
//		});
		setLinstener();
		initData();
		JsonInfo();
		dianJi();


	}
	public void dianJi(){
		reListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Goods g = JsonList.get(position);
				Intent in = new Intent(Home.this,MoreMsg.class);
				Bundle b = new Bundle();
				b.putString("img", g.getsDrawable());
				b.putString("name", g.getsName());
				b.putString("price", g.getsPrice());
				b.putString("sales", g.getsSales());
				b.putString("addr", g.getsAddr());
				in.putExtras(b);
				startActivity(in);
			}
		});
	}

	//ListView+ListView 动态设置Item高度
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			if (listItem instanceof ViewGroup) {
				listItem.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	// 解析商品的JSON数据
	public void JsonInfo() {
		Log.v("TAG", "链接网络");

		NetHttpData.getHttpDao().getGoods(page+"", new JsonHttpResponseHandler("utf-8"){
			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
				Log.v("TAG", "网络连接失败");
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				Log.v("TAG", "网络连接成功");
				try {
					JSONArray array =response.getJSONArray("result");
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						Goods ge = new Goods();
						ge.setSid(obj.optString("sid"));
						ge.setsClassIfy(obj.optString("sClassIfy"));
						ge.setsName(obj.optString("sName"));
						ge.setsPrice(obj.optString("sPrice"));
						ge.setsInventory(obj.optString("sInventory"));
						ge.setsAddr(obj.optString("sAddr"));
						ge.setsSeller(obj.optString("sSeller"));
						ge.setsSales(obj.optString("sSales"));
						String Dimg = obj.optString("sImgas");
						ge.setsDrawable(Dimg);
						ge.setsColor(obj.optString("sColor"));
						ge.setsType(obj.optString("sType"));
						Log.v("TAG", "获取到的JSON数据："+ge.toString());
						Log.v("TAG", "获取到的图片："+Dimg.toString());
						JsonList.add(ge);
					}
					reMenAdapter = new RenMenGoodsAdapter(Home.this, JsonList);
					reListView.setAdapter(reMenAdapter);

				} catch (JSONException e) {
					Log.v("TAG", "获取JSON数据失败");
					e.printStackTrace();
				}
			}

		});
	}

	/**
	 * 设置事件监听
	 */
	private void setLinstener() {
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				updateIntroAndDot();
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		list.add(new ViewPager(R.drawable.img_home_banner1, "水果大换季，趣味无穷！"));
		list.add(new ViewPager(R.drawable.img_home_banner2, "水果拼盘！"));
		list.add(new ViewPager(R.drawable.img_home_banner3, "水果焕新季  优惠待你来！"));
		initDots();
		mViewPagerAdapter = new ViewPagerAdapter(Home.this, list);
		mViewPager.setAdapter(mViewPagerAdapter);

		// 默认在1亿多
		mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - ((Integer.MAX_VALUE / 2) % list.size()));
		// 3秒定时
		mMyHandler.sendEmptyMessageDelayed(0, 3000);
		updateIntroAndDot();

	}

	/**
	 * 初始化dot
	 */
	private void initDots() {
		for (int i = 0; i < list.size(); i++) {
			View view = new View(this);
			LayoutParams params = new LayoutParams(8, 8);
			if (i != 0) {
				params.leftMargin = 5;
			}
			view.setLayoutParams(params);
			view.setBackgroundResource(R.drawable.selector_dot);
			mDotLayout.addView(view);
		}
	}

	/**
	 * 更新文本
	 */
	private void updateIntroAndDot() {
		int currentPage = mViewPager.getCurrentItem() % list.size();
		mIntroTv.setText(list.get(currentPage).getIntro());

		for (int i = 0; i < mDotLayout.getChildCount(); i++) {
			mDotLayout.getChildAt(i).setEnabled(i == currentPage);// 设置setEnabled为true的话
			// 在选择器里面就会对应的使用白色颜色
		}
	}

}
