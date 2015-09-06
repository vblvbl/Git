package com.shiliangyu.temtool;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.shiliangyu.resource.HttpResource;
import com.shiliangyu.tempdc.MainActivity;
import com.shiliangyu.tempdc.R;

public class DownloadToolActivity extends Activity {
	View downing, downed;
	ImageButton back;
	ImageButton add;
	android.support.v4.view.ViewPager vp;
	List<View> viewPagerList;
	List<Map<String, Object>> dowingListAdapter;
	ListView dowingList;
	TextView tv1;
	TextView tv2;
	private static int ThreadCount = 3;
	File file;
	String getFileList;
	SimpleAdapter sp;
	Handler hd = new MyHandler();
	private final int ChangeList = 2;
	ProgressDialog prdialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downloadtool);
		viewInit();
		viewSet();
		setEvent();
	}

	private void viewInit() {
		LayoutInflater in = getLayoutInflater();
		vp = (ViewPager) findViewById(R.id.downloadViewPager);
		downing = in.inflate(R.layout.tool_download_downloading, null);
		downed = in.inflate(R.layout.tool_download_downloaded, null);
		viewPagerList = new ArrayList<View>();
		viewPagerList.add(downing);
		viewPagerList.add(downed);
		tv1 = (TextView) findViewById(R.id.downloadTag1);
		tv2 = (TextView) findViewById(R.id.downloadTag2);
		back = (ImageButton) findViewById(R.id.downloadBack);
		add = (ImageButton) findViewById(R.id.downloadAdd);
		dowingListAdapter = new ArrayList<Map<String, Object>>();
		setListAdapter();
	}

	private void viewSet() {
		vp.setAdapter(new PagerAdapter() {

			@Override
			public void finishUpdate(ViewGroup container) {
				super.finishUpdate(container);
				int current = vp.getCurrentItem();
				if (current == 0) {
					tv1.setTextColor(Color.BLUE);
					tv2.setTextColor(Color.BLACK);
				}
				if (current == 1) {
					tv2.setTextColor(Color.BLUE);
					tv1.setTextColor(Color.BLACK);
				}
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(viewPagerList.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(viewPagerList.get(position));
				return viewPagerList.get(position);
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return viewPagerList.size();
			}
		});

		tv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				vp.setCurrentItem(0, true);
			}
		});
		tv2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				vp.setCurrentItem(1, true);
			}
		});
	}

	private void setEvent() {
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(DownloadToolActivity.this,
						MainActivity.class);
				DownloadToolActivity.this.startActivity(in);
				DownloadToolActivity.this.overridePendingTransition(R.anim.toolselecttotool, R.anim.tooltotoolselect);
				DownloadToolActivity.this.finish();
			}
		});
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inf = DownloadToolActivity.this
						.getLayoutInflater();
				View view = inf.inflate(R.layout.downdialog, null);
				final TextView tv = (TextView) view
						.findViewById(R.id.ddialogviewextview);
				final AlertDialog ad = new AlertDialog.Builder(
						DownloadToolActivity.this)
						.setTitle("输入合法下载地址")
						.setView(view)
						.setPositiveButton("下载",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										String downpath = tv.getText()
												.toString().trim();
										if ("".equals(downpath)) {
											Toast.makeText(
													DownloadToolActivity.this,
													"输入不能为空", 0).show();
											return;
										}
										new PareDownLoad(downpath).start();
										prdialog = new ProgressDialog(
												DownloadToolActivity.this);
										prdialog.show();
										prdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
									}

								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}
								}).create();
				ad.show();
				ad.setCanceledOnTouchOutside(true);
				tv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						prdialog = new ProgressDialog(DownloadToolActivity.this);
						prdialog.show();
						prdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
						prdialog.closeOptionsMenu();
						prdialog.setCancelable(false);
						new PareDownLoad(HttpResource.p2p).start();
						ad.cancel();
						
					}
				});
			}
		});
	}

	private Map<String, Object> downloadBackMap(String temp) {
		Map<String, Object> backmap = new HashMap<String, Object>();
		String[] FileAbout = temp.split(" ");
		String name = FileAbout[0];
		String length = FileAbout[1];
		if (name.endsWith(".mp3") || name.endsWith(".WAV")) {
			backmap.put("media", R.drawable.music);
		} else if (name.endsWith(".mp4") || name.endsWith(".rmvb")) {
			backmap.put("media", R.drawable.video);
		} else if (name.endsWith(".apk")) {
			backmap.put("media", R.drawable.apkit);
		} else if (name.endsWith(".jpg")) {
			backmap.put("media", R.drawable.picture);
		} else {
			backmap.put("media", R.drawable.notexcute);
		}
		backmap.put("name", name);
		backmap.put("size", length);
		return backmap;
	}

	private void setListAdapter() {
		dowingList = (ListView) downing.findViewById(R.id.downloadIng);
		sp = new SimpleAdapter(this, dowingListAdapter,
				R.layout.downloading_list, new String[] { "media", "name",
						"size" }, new int[] { R.id.downloadingListImage,
						R.id.downloadingListtv1, R.id.downloadingListtv2, });
		dowingList.setAdapter(sp);
	}

	class PareDownLoad extends Thread {
		private String pat;

		public PareDownLoad(String path) {
			this.pat = path;
		}

		@Override
		public void run() {
			try {
				URL url = new URL(pat);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.setConnectTimeout(5000);
				con.setRequestMethod("GET");
				int code = con.getResponseCode();
				if (code == 200) {
					int length = con.getContentLength();
					int block = length / ThreadCount;
					String getfile = con.getURL().getFile();
					String fileName = getfile.substring(getfile
							.lastIndexOf('/') + 1);
					getFileList = fileName + " " + length;
					Map<String, Object> mapneed = downloadBackMap(getFileList);
					Message msg = new Message();
					msg.what = ChangeList;
					msg.obj = mapneed;
					hd.sendMessage(msg);
					file = new File(DownloadToolActivity.this.getFilesDir(),
							fileName);
					RandomAccessFile rad = new RandomAccessFile(file, "rwd");
					rad.setLength(length);
					rad.close();
					for (int threadId = 1; threadId <= ThreadCount; threadId++) {
						int startIndex = (threadId - 1) * block;
						int endIndex = threadId * block - 1;
						if (threadId == ThreadCount) {
							endIndex = length;
						}
						new DownLoadThread(threadId, startIndex, endIndex, pat)
								.start();
						hd.sendEmptyMessage(0);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	class DownLoadThread extends Thread {
		int id;
		int start;
		int end;
		String path;

		public DownLoadThread(int id, int start, int end, String path) {
			super();
			this.id = id;
			this.start = start;
			this.end = end;
			this.path = path;
		}

		@Override
		public void run() {
			try {
				URL url = new URL(path);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.setRequestMethod("GET");
				con.setReadTimeout(5000);
				con.setRequestProperty("Range", "bytes=" + start + "-" + end);
				InputStream in = con.getInputStream();
				RandomAccessFile ra = new RandomAccessFile(file, "rwd");
				ra.seek(start);
				int len = 0;
				byte[] buf = new byte[1024];
				while ((len = in.read(buf)) != -1) {
					ra.write(buf, 0, len);
				}
				in.close();
				ra.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			prdialog.dismiss();
			if (msg.what == ChangeList) {
				Map<String, Object> map = (Map<String, Object>) msg.obj;
				dowingListAdapter.add(map);
				sp.notifyDataSetChanged();
			}
		}
	}
}
