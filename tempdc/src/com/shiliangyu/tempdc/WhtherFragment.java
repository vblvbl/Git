package com.shiliangyu.tempdc;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.shiliangyu.domain.WhtherForecast;
import com.shiliangyu.domain.WhtherToday;
import com.shiliangyu.resource.HttpResource;
import com.shiliangyu.serviceutil.AboutXML;
import com.shiliangyu.serviceutil.MySharedPreferences;
import com.shiliangyu.tempdc.view.MyProgressDialog;
import com.shiliangyu.tempdc.view.MyViewPager;
import com.slidingmenu.lib.SlidingMenu;

public class WhtherFragment extends Fragment {
	private String tempcoor = "gcj02";
	private PullToRefreshScrollView pv;
	private TextView title;
	private WhtherToday todayIns;
	private ImageButton imLeft;
	private String dateOut;
	private MyViewPager vp;
	private List<ImageView> imageList;
	private int[] imageResId;
	private LinearLayout pointGroup;
	private ImageView[] imagePoint;
	private TextView onePartTv1;
	private TextView onePartTv2;
	private TextView twoPartTv1;
	private TextView twoPartTv2;
	private TextView twoPartTv3;
	private TextView threePartTv1;
	private TextView threePartTv2;
	private TextView threePartTv3;
	private ImageView threePartIv;
	private TextView threePartTv4;
	private TextView fourPartOneRowTv1;
	private ImageView fourPartOneRowIv;
	private TextView fourPartOneRowTv2;
	private TextView fourPartOneRowTv3;
	private TextView fourPartTwoRowTv1;
	private ImageView fourPartTwoRowIv;
	private TextView fourPartTwoRowTv2;
	private TextView fourPartTwoRowTv3;
	private TextView fourPartThreeRowTv1;
	private ImageView fourPartThreeRowIv;
	private TextView fourPartThreeRowTv2;
	private TextView fourPartThreeRowTv3;
	private TextView fourPartFourRowTv1;
	private ImageView fourPartFourRowIv;
	private TextView fourPartFourRowTv2;
	private TextView fourPartFourRowTv3;
	private TextView fivePartOneRowTv1;
	private TextView fivePartOneRowTv2;
	private TextView fivePartTwoRowTv1;
	private TextView fivePartTwoRowTv2;
	private TextView fivePartThreeRowTv1;
	private TextView fivePartThreeRowTv2;
	private TextView fivePartFourRowTv1;
	private TextView fivePartFourRowTv2;
	private TextView fivePartFiveRowTv1;
	private TextView fivePartFiveRowTv2;
	private TextView fivePartSixRowTv1;
	private TextView fivePartSixRowTv2;
	private TextView fivePartSevenRowTv1;
	private TextView fivePartSevenRowTv2;
	private TextView fivePartEightRowTv1;
	private TextView fivePartEightRowTv2;
	private TextView fivePartNightRowTv1;
	private TextView fivePartNightRowTv2;
	private TextView fivePartTenRowTv1;
	private TextView fivePartTenRowTv2;
	private String url = HttpResource.getCityHttpUrl("北京");
	private View view;
	String[] titlename;
	private SlidingMenu menu;
	private static int count = 0;
	private MyProgressDialog prdialog;
	private Handler hd = new MyHandle();
	protected final static int CHANGE_UI = 1;
	protected final static int ZHISHU = 2;
	protected final static int YUBAO = 3;
	protected final static int INTERROR = 4;
	protected final static int DATAPULLERROR = 5;
	protected final static int INTEPULLERROR = 6;
	protected final static int FRESHOVER = 7;
	protected final static int DATAFLASH = 8;

	Handler handler = new Handler() {
	
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0x11:
				vp.setCurrentItem(vp.getCurrentItem() + 1);
				Message mes = handler.obtainMessage(0x11);
				handler.sendMessageDelayed(mes, 7000);
				break;
			case 0x12:
				SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
				Date date = new Date(System.currentTimeMillis());
				dateOut = formatter.format(date);
				twoPartTv2.setText(dateOut);
				Message mqs = handler.obtainMessage(0x12);
				handler.sendMessageDelayed(mqs, 1000);
				break;
			default:
				break;
			}
		};
	};

	public WhtherFragment(SlidingMenu menu) {
		this.menu = menu;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.whther, null);
		viewInit(view);
		setEvent();
		viewSet();
		return view;
	}

	private void viewInit(View view) {
		// menu=getActivity().gets
		imLeft = (ImageButton) view.findViewById(R.id.whtherPartOneReLayoutib);
		vp = (MyViewPager) view.findViewById(R.id.toolViewPager);
		onePartTv1 = (TextView) view
				.findViewById(R.id.whtherPartOneReLayouttv1);
		onePartTv2 = (TextView) view
				.findViewById(R.id.whtherPartOneReLayouttv2);
		twoPartTv1 = (TextView) view
				.findViewById(R.id.whtherPartTwoLiLayouttv1);
		twoPartTv2 = (TextView) view
				.findViewById(R.id.whtherPartTwoLiLayouttv2);
		twoPartTv3 = (TextView) view
				.findViewById(R.id.whtherPartTwoLiLayouttv3);
		threePartTv1 = (TextView) view
				.findViewById(R.id.whtherPartThreeLiLayouttv1);
		threePartTv2 = (TextView) view
				.findViewById(R.id.whtherPartThreeLiLayoutLiLayouttv1);
		threePartTv3 = (TextView) view
				.findViewById(R.id.whtherPartThreeLiLayoutLiLayouttv2);
		threePartIv = (ImageView) view
				.findViewById(R.id.whtherPartThreeLiLayoutLiLayoutiv);
		threePartTv4 = (TextView) view
				.findViewById(R.id.whtherPartThreeLiLayouttv2);
		fourPartOneRowTv1 = (TextView) view
				.findViewById(R.id.whtherPartFourTabLyTabRowOnetv1);
		fourPartOneRowIv = (ImageView) view
				.findViewById(R.id.whtherPartFourTabLyTabRowOneiv);
		fourPartOneRowTv2 = (TextView) view
				.findViewById(R.id.whtherPartFourTabLyTabRowOnetv2);
		fourPartOneRowTv3 = (TextView) view
				.findViewById(R.id.whtherPartFourTabLyTabRowOnetv3);

		fourPartTwoRowTv1 = (TextView) view
				.findViewById(R.id.whtherPartFourTabLyTabRowTwotv1);
		fourPartTwoRowIv = (ImageView) view
				.findViewById(R.id.whtherPartFourTabLyTabRowTwoiv);
		fourPartTwoRowTv2 = (TextView) view
				.findViewById(R.id.whtherPartFourTabLyTabRowTwotv2);
		fourPartTwoRowTv3 = (TextView) view
				.findViewById(R.id.whtherPartFourTabLyTabRowTwotv3);

		fourPartThreeRowTv1 = (TextView) view
				.findViewById(R.id.whtherPartFourTabLyTabRowThreetv1);
		fourPartThreeRowIv = (ImageView) view
				.findViewById(R.id.whtherPartFourTabLyTabRowThreeiv);
		fourPartThreeRowTv2 = (TextView) view
				.findViewById(R.id.whtherPartFourTabLyTabRowThreetv2);
		fourPartThreeRowTv3 = (TextView) view
				.findViewById(R.id.whtherPartFourTabLyTabRowThreetv3);

		fourPartFourRowTv1 = (TextView) view
				.findViewById(R.id.whtherPartFourTabLyTabRowFourtv1);
		fourPartFourRowIv = (ImageView) view
				.findViewById(R.id.whtherPartFourTabLyTabRowFouriv);
		fourPartFourRowTv2 = (TextView) view
				.findViewById(R.id.whtherPartFourTabLyTabRowFourtv2);
		fourPartFourRowTv3 = (TextView) view
				.findViewById(R.id.whtherPartFourTabLyTabRowFourtv3);
		fivePartOneRowTv1 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowOnetv1);
		fivePartOneRowTv2 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowOnetv2);
		fivePartTwoRowTv1 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowTwotv1);
		fivePartTwoRowTv2 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowTwotv2);
		fivePartThreeRowTv1 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowThreetv1);
		fivePartThreeRowTv2 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowThreetv2);
		fivePartFourRowTv1 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowFourtv1);
		fivePartFourRowTv2 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowFourtv2);
		fivePartFiveRowTv1 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowFivetv1);
		fivePartFiveRowTv2 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowFivetv2);
		fivePartSixRowTv1 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowSixtv1);
		fivePartSixRowTv2 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowSixtv2);
		fivePartSevenRowTv1 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowSeventv1);
		fivePartSevenRowTv2 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowSeventv2);
		fivePartEightRowTv1 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowEighttv1);
		fivePartEightRowTv2 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowEighttv2);
		fivePartNightRowTv1 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowNighttv1);
		fivePartNightRowTv2 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowNighttv2);
		fivePartTenRowTv1 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowTentv1);
		fivePartTenRowTv2 = (TextView) view
				.findViewById(R.id.whtherPartFiveTabLyTabRowTentv2);
		pv = (PullToRefreshScrollView) view
				.findViewById(R.id.pullFreshScrollView);
		imagePageInit();
		titleInit(view);
		pointInint(view);
	}

	private void titleInit(View view) {
		titlename = new String[] { "年走势图", "详细气温", "日常曲线", "全国降雨", "温差图" };
		title = (TextView) view.findViewById(R.id.whtherTitle);
	}

	private void imagePageInit() {
		imageResId = new int[] { R.drawable.viewchange1,
				R.drawable.viewchange2, R.drawable.viewchange3,
				R.drawable.viewchange4, R.drawable.viewchange5 };
		imageList = new ArrayList<ImageView>();
		for (int i = 0; i < imageResId.length; i++) {
			ImageView im = new ImageView(getActivity());
			im.setImageResource(imageResId[i]);
			im.setScaleType(ScaleType.CENTER_CROP);
			imageList.add(im);
		}
	}

	private void pointInint(View view) {
		pointGroup = (LinearLayout) view.findViewById(R.id.pointGroup);
		imagePoint = new ImageView[imageList.size()];
		for (int i = 0; i < imageResId.length; i++) {
			ImageView im = new ImageView(getActivity());
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(6, 6);
			lp.setMargins(11, 0, 11, 0);
			im.setLayoutParams(lp);
			imagePoint[i] = im;
			imagePoint[i].setImageResource(R.drawable.ponint);
			pointGroup.addView(im);
		}
	}

	private void setEvent() {
		imLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				menu.showMenu();

			}
		});
		pv.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				prdialog = new MyProgressDialog(WhtherFragment.this
						.getActivity());
				prdialog.initDialog();
				new TodayInternetPullThread().start();
				new ForecastInternetPullThread().start();
				pv.onRefreshComplete();
			}
		});
		onePartTv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater inf = WhtherFragment.this.getActivity()
						.getLayoutInflater();
				View view = inf.inflate(R.layout.choosecity, null);
				final ImageButton imb1 = (ImageButton) view
						.findViewById(R.id.chooseCity);
				final ImageButton imb2 = (ImageButton) view
						.findViewById(R.id.chooseLocation);
				final String[] citys = new String[] { "北京", "上海", "天津", "南京",
						"青岛", "广州", "铜陵", "厦门", "沈阳", "深圳", "合肥" };
				final Dialog alert = new AlertDialog.Builder(
						WhtherFragment.this.getActivity()).setTitle("选择操作")
						.setView(view).create();
				alert.show();
				alert.setCanceledOnTouchOutside(true);
				imb1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						alert.cancel();
						Dialog alertDialog = new AlertDialog.Builder(
								WhtherFragment.this.getActivity())
								.setTitle("请选择城市")
								.setItems(citys,
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												prdialog = new MyProgressDialog(
														WhtherFragment.this
																.getActivity());
												prdialog.initDialog();
												String citytemp = citys[which];
												url = HttpResource
														.getCityHttpUrl(citytemp);
												onePartTv1.setText(citytemp);
												MySharedPreferences
														.loadCityInfo(
																WhtherFragment.this
																		.getActivity(),
																citytemp);
												new TodayInternetPullThread()
														.start();
												new ForecastInternetPullThread()
														.start();

											}
										})
								.setNegativeButton("取消",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
											}
										}).create();
						alertDialog.show();

					}
				});
				imb2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(getActivity(), "暂时取消定位", 1).show();
					}
				});
			}
		});
	}

	private void viewSet() {
		vp.setAdapter(new PagerAdapter() {

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				((ViewPager) container).removeView((View) object);
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			@Override
			public Object instantiateItem(ViewGroup container,
					final int position) {
				((ViewPager) container).addView(imageList.get(position
						% imageList.size()));
				String tempWeb="http://www.weather.com.cn/weather/101030100.shtml";
				final String[] urls = new String[] {tempWeb,tempWeb,tempWeb,tempWeb,tempWeb};
				title.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent in = new Intent();
						in.setAction(Intent.ACTION_VIEW);
						in.setData(Uri.parse(urls[position % imageList.size()]));
						startActivity(in);

					}
				});
				return imageList.get(position % imageList.size());
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return Integer.MAX_VALUE;
			}
		});
		vp.setOnPageChangeListener(new MyPagerChangeListener());
		String[] week = new String[] { "星期六", "星期天", "星期一", "星期二", "星期三",
				"星期四", "星期五", };
		Calendar c = Calendar.getInstance();
		int index1 = c.get(Calendar.DAY_OF_WEEK);
		twoPartTv1.setText(week[index1 % week.length]);
		String city = MySharedPreferences.readCityInfo(WhtherFragment.this
				.getActivity());
		onePartTv1.setText(city);
		SimpleDateFormat form = new SimpleDateFormat("yyyy年MM月dd日 ");
		Date date = new Date(System.currentTimeMillis());
		String str = form.format(date);
		onePartTv2.setText(str);
		File test1 = new File(getActivity().getFilesDir(), "wtoy.xml");
		File test2 = new File(getActivity().getFilesDir(), "wfast.xml");
		if (test1.exists() && test2.exists()) {
			PullLocalXml sp = new PullLocalXml();
			sp.start();

		} else {

			return;
		}
	}

	class PullLocalXml extends Thread {

		@Override
		public void run() {
			try {
				WhtherToday today = AboutXML.pullTodayXml(WhtherFragment.this
						.getActivity());
				Message msg1 = new Message();
				String[] mux = new String[] { today.getUpdateTime() + "发布",
						today.getNowTemp(), today.getNowFengLi(),
						today.getNowShiDu(), today.getNowFengXiang(),
						today.getNowIcon() };
				msg1.what = CHANGE_UI;
				msg1.obj = mux;
				hd.sendMessage(msg1);
				Map<String, String> zsmap = today.getZhiShuMap();
				Message msg2 = new Message();
				msg2.what = ZHISHU;
				msg2.obj = zsmap;
				hd.sendMessage(msg2);
				List<WhtherForecast> lf = AboutXML
						.pullForecastXml(WhtherFragment.this.getActivity());
				List<String[]> list = new ArrayList<String[]>();
				for (WhtherForecast imp : lf) {
					String[] s = new String[] { imp.getWeek(),
							imp.getMaxTemp(), imp.getMinTemp(), imp.getIcon() };
					list.add(s);
				}
				Message msg3 = new Message();
				msg3.what = YUBAO;
				msg3.obj = list;
				hd.sendMessage(msg3);

			} catch (Exception e) {
				Message msg4 = new Message();
				msg4.what = DATAPULLERROR;
				hd.sendMessage(msg4);
			}
		}
	}

	class MyHandle extends Handler {

		@Override
		public void handleMessage(Message msg) {
			if (count == 2) {
				prdialog.colseDialog();
				count = 0;
			}
			if (msg.what == CHANGE_UI) {
				String[] set = (String[]) msg.obj;
				twoPartTv3.setText(set[0]);
				threePartTv1.setText(set[1]);
				threePartTv2.setText(set[2]);
				threePartTv3.setText(set[3]);
				threePartTv4.setText(set[4]);
				threePartIv.setImageResource(WhtherToIcon(set[5]));
			} else if (msg.what == ZHISHU) {
				Map<String, String> map = (Map<String, String>) msg.obj;
				Set<String> set = map.keySet();
				String keys[] = new String[10];
				String values[] = new String[10];
				int i = 0;
				int j = 0;
				for (String key : set) {
					if (i < 10) {
						keys[i] = key;
						values[j] = map.get(key);
						i++;
						j++;
					} else {
						break;
					}
				}
				fivePartOneRowTv1.setText(keys[0]);
				fivePartOneRowTv2.setText(values[0]);
				fivePartTwoRowTv1.setText(keys[1]);
				fivePartTwoRowTv2.setText(values[1]);
				fivePartThreeRowTv1.setText(keys[2]);
				fivePartThreeRowTv2.setText(values[2]);
				fivePartFourRowTv1.setText(keys[3]);
				fivePartFourRowTv2.setText(values[3]);
				fivePartFiveRowTv1.setText(keys[4]);
				fivePartFiveRowTv2.setText(values[4]);
				fivePartSixRowTv1.setText(keys[5]);
				fivePartSixRowTv2.setText(values[5]);
				fivePartSevenRowTv1.setText(keys[6]);
				fivePartSevenRowTv2.setText(values[6]);
				fivePartEightRowTv1.setText(keys[7]);
				fivePartEightRowTv2.setText(values[7]);
				fivePartNightRowTv1.setText(keys[8]);
				fivePartNightRowTv2.setText(values[8]);
				fivePartTenRowTv1.setText(keys[9]);
				fivePartTenRowTv2.setText(values[9]);
			} else if (msg.what == YUBAO) {
				List<String[]> ls = (List<String[]>) msg.obj;
				String[] jt = ls.get(0);
				String[] mt = ls.get(1);
				String[] ht = ls.get(2);
				String[] dht = ls.get(3);
				fourPartOneRowTv1.setText(jt[0]);
				fourPartOneRowTv2.setText(jt[1]);
				fourPartOneRowTv3.setText(jt[2]);
				fourPartTwoRowTv1.setText(mt[0]);
				fourPartTwoRowTv2.setText(mt[1]);
				fourPartTwoRowTv3.setText(mt[2]);
				fourPartThreeRowTv1.setText(ht[0]);
				fourPartThreeRowTv2.setText(ht[1]);
				fourPartThreeRowTv3.setText(ht[2]);
				fourPartFourRowTv1.setText(dht[0]);
				fourPartFourRowTv2.setText(dht[1]);
				fourPartFourRowTv3.setText(dht[2]);
				try {
					fourPartOneRowIv.setImageResource(WhtherToIcon(jt[3]));
					fourPartTwoRowIv.setImageResource(WhtherToIcon(mt[3]));
					fourPartThreeRowIv.setImageResource(WhtherToIcon(ht[3]));
					fourPartFourRowIv.setImageResource(WhtherToIcon(dht[3]));
				} catch (Exception e) {
					Toast.makeText(getActivity(), "获取数据出错，设计问题", 0).show();
				}
			} else if (msg.what == INTERROR) {
				Toast.makeText(getActivity(), "网络异常", 0).show();
			} else if (msg.what == DATAPULLERROR) {
				Toast.makeText(getActivity(), "数据解析异常", 0).show();
			} else if (msg.what == INTEPULLERROR) {
				Toast.makeText(getActivity(), "网络解析异常", 0).show();
			} else if (msg.what == FRESHOVER) {
				Toast.makeText(getActivity(), "更新完成", 1).show();
			}
		}
	}

	class TodayInternetPullThread extends Thread {

		@Override
		public void run() {
			try {
				URL ur = new URL(url);
				HttpURLConnection con = (HttpURLConnection) ur.openConnection();
				con.setReadTimeout(5000);
				con.setRequestMethod("GET");
				con.setRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
				int code1 = con.getResponseCode();
				if (code1 == 200) {
					InputStream in = con.getInputStream();
					WhtherToday today = AboutXML.pullIOTodayXml(in);
					AboutXML.todayXmlMemory(today, getActivity());
					Message msg1 = new Message();
					String[] mux = new String[] { today.getUpdateTime() + "发布",
							today.getNowTemp(), today.getNowFengLi(),
							today.getNowShiDu(), today.getNowFengXiang(),
							today.getNowIcon() };
					msg1.what = CHANGE_UI;
					msg1.obj = mux;
					hd.sendMessage(msg1);
					Map<String, String> zsmap = today.getZhiShuMap();
					Message msg2 = new Message();
					msg2.what = ZHISHU;
					msg2.obj = zsmap;
					hd.sendMessage(msg2);
					hd.sendEmptyMessage(0);
				} else {
					Message msg = new Message();
					msg.what = WhtherFragment.INTERROR;
					hd.sendMessage(msg);
				}

			} catch (Exception e) {
				Message msg = new Message();
				msg.what = WhtherFragment.INTEPULLERROR;
				hd.sendMessage(msg);
			} finally {
				count++;
			}

		}

	}

	class ForecastInternetPullThread extends Thread {

		@Override
		public void run() {
			try {
				URL u = new URL(url);
				HttpURLConnection con = (HttpURLConnection) u.openConnection();
				con.setReadTimeout(5000);
				con.setRequestMethod("GET");
				con.setRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
				int code1 = con.getResponseCode();
				if (code1 == 200) {
					InputStream in = con.getInputStream();
					List<WhtherForecast> ls = AboutXML.pullIOForecastXml(in);
					AboutXML.forecastXmlMemory(ls, getActivity());
					List<String[]> list = new ArrayList<String[]>();
					for (WhtherForecast imp : ls) {
						String[] s = new String[] { imp.getWeek(),
								imp.getMaxTemp(), imp.getMinTemp(),
								imp.getIcon() };
						list.add(s);
					}
					Message msg1 = new Message();
					msg1.what = YUBAO;
					msg1.obj = list;
					hd.sendMessage(msg1);
					Message msg = new Message();
					msg.what = FRESHOVER;
					hd.sendMessage(msg);
					hd.sendEmptyMessage(0);
				} else {
					Message msg = new Message();
					msg.what = WhtherFragment.INTERROR;
					hd.sendMessage(msg);
				}

			} catch (Exception e) {
				Message msg = new Message();
				msg.what = WhtherFragment.INTEPULLERROR;
				hd.sendMessage(msg);
			} finally {
				count++;
			}

		}
	}

	private int WhtherToIcon(String whther) {
		if ("霾".equals(whther)) {
			return R.drawable.mai;
		} else if ("多云".equals(whther)) {
			return R.drawable.duoyun;
		} else if ("晴".equals(whther)) {
			return R.drawable.qing;
		} else if ("小雨".equals(whther)) {
			return R.drawable.xiaoyu;
		} else if ("阴".equals(whther)) {
			return R.drawable.yin;
		}
		return 0;
	}

	class MyPagerChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			title.setText(titlename[arg0 % imageList.size()]);
			imagePoint[arg0 % imageList.size()]
					.setBackgroundResource(R.drawable.pointselect);
			for (int i = 0; i < imagePoint.length; i++) {
				if (i != arg0 % imageList.size()) {
					imagePoint[i].setBackgroundResource(R.drawable.ponint);
				}
			}
		}
	}

	@Override
	public void onStart() {
		Message msg = handler.obtainMessage(0x11);
		handler.sendMessage(msg);
		Message msq = handler.obtainMessage(0x12);
		handler.sendMessage(msq);
		super.onStart();
	}

	@Override
	public void onStop() {
		handler.removeMessages(0x11);
		handler.removeMessages(0x12);
		super.onStop();
	}

}