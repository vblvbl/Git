package com.shiliangyu.tempdc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.slidingmenu.lib.SlidingMenu;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private boolean flag;
	private ViewPager viewPager;
	private ImageButton imb1;
	private TextView tvuser;
	private List<Fragment> listv;
	private ImageButton iButtonOne;
	private ImageButton iButtonTwo;
	private ImageButton iButtonThree;
	private ImageView imageView;
	private LinearLayout ll;
	private SlidingMenu menu;
	private ImageButton leftBack;
	private ListView leftListView;
	private File file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewInit();
		setEvent();
	}

	private List<Map<String, Object>> setLeftList(List<Map<String, Object>> ls) {
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("icon", R.drawable.p2);
		map1.put("title", "分享");
		map1.put("summy", "软件推荐给更多人使用");
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("icon", R.drawable.p1);
		map2.put("title", "设置");
		map2.put("summy", "设置本APP的相关配置");
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("icon", R.drawable.p3);
		map3.put("title", "退出");
		map3.put("summy", "点击退出Temptc软件");
		ls.add(map1);
		ls.add(map2);
		ls.add(map3);
		return ls;

	}

	private void setEvent() {
		leftBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				menu.toggle(false);

			}
		});

		imb1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				file = new File(getFilesDir(), "icon.png");
				if (file.exists()) {
					Toast.makeText(MainActivity.this, "正在修建用户专属activity", 1)
							.show();
					;
				} else {
					Intent in = new Intent();
					in.setClass(MainActivity.this, LoginActivity.class);
					startActivityForResult(in, 2);
				}
			}
		});
		leftListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					LayoutInflater in = getLayoutInflater();
					View v = in.inflate(R.layout.chooseshare, null);
					final ImageButton im1 = (ImageButton) v
							.findViewById(R.id.chooseShareInter);
					final ImageButton im2 = (ImageButton) v
							.findViewById(R.id.chooseShareSMS);
					Dialog dia = new AlertDialog.Builder(MainActivity.this)
							.setTitle("分享软件给朋友").setView(v).create();
					dia.show();
					dia.setCanceledOnTouchOutside(true);
					im2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent in = new Intent();
							in.setAction("android.intent.action.VIEW");
							in.addCategory("android.intent.category.DEFAULT");
							startActivity(in);
						}
					});
					im1.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							/*
							 * 模版代码 用于分享
							 */
							Intent intent = new Intent(Intent.ACTION_SEND);
							intent.setType("image/*");
							intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
							intent.putExtra(Intent.EXTRA_TEXT,
									"I have successfully share my message through my app");
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(Intent.createChooser(intent,
									getTitle()));
						}
					});
					break;

				case 2:
					MainActivity.this.finish();
					break;

				default:
					break;
				}

			}
		});
	}

	private void viewInit() {
		initSlid();
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		iButtonOne = (ImageButton) findViewById(R.id.ibuttonOne);
		iButtonTwo = (ImageButton) findViewById(R.id.ibuttonTwo);
		iButtonThree = (ImageButton) findViewById(R.id.ibuttonTree);
		ll = (LinearLayout) findViewById(R.id.linearLayout);
		iButtonOne.setOnClickListener(this);
		iButtonTwo.setOnClickListener(this);
		iButtonThree.setOnClickListener(this);
		WhtherFragment wf = new WhtherFragment(menu);
		ScheduleFragment sf = new ScheduleFragment();
		ToolFragment uf = new ToolFragment();
		listv = new ArrayList<Fragment>();
		listv.add(wf);
		listv.add(sf);
		listv.add(uf);
		FragmentManager fm = getSupportFragmentManager();
		viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {

			@Override
			public void finishUpdate(ViewGroup container) {
				super.finishUpdate(container);
				int currentItem = viewPager.getCurrentItem();
				if (currentItem == 0) {
					iButtonOne.setImageDrawable((MainActivity.this
							.getResources().getDrawable(R.drawable.whthers)));
					iButtonTwo.setImageDrawable((MainActivity.this
							.getResources().getDrawable(R.drawable.schedule)));
					iButtonThree.setImageDrawable((MainActivity.this
							.getResources().getDrawable(R.drawable.tool)));
				}
				if (currentItem == 1) {
					iButtonOne.setImageDrawable((MainActivity.this
							.getResources().getDrawable(R.drawable.whther)));
					iButtonTwo.setImageDrawable((MainActivity.this
							.getResources().getDrawable(R.drawable.schedules)));
					iButtonThree.setImageDrawable((MainActivity.this
							.getResources().getDrawable(R.drawable.tool)));
				}
				if (currentItem == 2) {
					iButtonOne.setImageDrawable((MainActivity.this
							.getResources().getDrawable(R.drawable.whther)));
					iButtonTwo.setImageDrawable((MainActivity.this
							.getResources().getDrawable(R.drawable.schedule)));
					iButtonThree.setImageDrawable((MainActivity.this
							.getResources().getDrawable(R.drawable.tools)));
				}

			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return listv.size();
			}

			@Override
			public Fragment getItem(int postion) {
				// TODO Auto-generated method stub
				return listv.get(postion);
			}

		});
	}

	private void initSlid() {
		int Displayside = getWindowManager().getDefaultDisplay().getWidth();
		int side = (Displayside / 5);
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setBehindOffset(side);
		// 设置渐入渐出效果的值
		menu.setFadeDegree(0.55f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.leftmenu);
		// LayoutInflater lf = WhtherFragment.this.getActivity()
		// .getLayoutInflater();
		// View leftmenu = lf.inflate(R.layout.leftmenu, null);
		leftListView = (ListView) this.findViewById(R.id.leftListView);
		List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
		ls = setLeftList(ls);
		leftListView.setAdapter(new SimpleAdapter(this, ls,
				R.layout.tool_listview,
				new String[] { "icon", "title", "summy" }, new int[] {
						R.id.user_listview_iv, R.id.user_listview_tv1,
						R.id.user_listview_tv2 }));
		leftBack = (ImageButton) findViewById(R.id.leftMenuBack);
		//读取本地是否有头像，没有使用静态
		imb1 = (ImageButton) findViewById(R.id.imageButtonOne);
		file = new File(getFilesDir(), "icon.png");
		if (file.exists()) {
			try {
				InputStream fl = new FileInputStream(file);
				Bitmap bp = BitmapFactory.decodeStream(fl);
				if (bp != null) {
					imb1.setImageBitmap(bp);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//读取本地用户名，没有使用静态
		tvuser = (TextView) findViewById(R.id.toolTextView);
		String name = getSharedPreferences("loginusername",
				Context.MODE_PRIVATE).getString("name", null);
		if (name != null) {
			tvuser.setText(name);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ibuttonOne:

			viewPager.setCurrentItem(0, true);

			break;
		case R.id.ibuttonTwo:

			viewPager.setCurrentItem(1, true);

			break;
		case R.id.ibuttonTree:

			viewPager.setCurrentItem(2, true);

			break;
		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null) {
			return;
		}
		if (requestCode == 2 && resultCode == 2) {
			Bitmap bt = data.getParcelableExtra("icon");
			String name = data.getStringExtra("name");
			tvuser.setText(name);
			imb1.setImageBitmap(bt);
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(!flag){
		ConnectivityManager cme = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo inf = cme.getActiveNetworkInfo();
		if (inf == null || !inf.isConnected()) {
			AlertDialog.Builder bl = new Builder(this);
			bl.setTitle("网络不可用");
			bl.setMessage("点击确定进入网络设置，否则离线进入");
			bl.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent in = new Intent();
					in.setAction("android.settings.WIRELESS_SETTINGS");
					in.addCategory("android.intent.category.DEFAULT");
					startActivity(in);
				}

			});
			bl.setNegativeButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					flag=true;

				}
			});
			bl.create().show();
		}
	}
	}
}
