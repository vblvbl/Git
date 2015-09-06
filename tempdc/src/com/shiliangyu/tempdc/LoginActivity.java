package com.shiliangyu.tempdc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.shiliangyu.tempdc.view.MyProgressDialog;

public class LoginActivity extends Activity {
	private ImageButton imb1;
	private EditText et1;
	private EditText et2;
	private MyProgressDialog mp;
	private String name;
	private String passwd;
	private Handler hd = new MyHandler();
	private final int ERROR = 0x12;
	private final int YES = 0x16;
	private final int WRONG = 0x11;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		initView();
		setEvent();
	}

	private void setEvent() {
		imb1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				name = et1.getText().toString().trim();
				passwd = et2.getText().toString().trim();
				if (!name.equals("") && !passwd.equals("")) {
					mp = new MyProgressDialog(LoginActivity.this);
					mp.initDialog();
					new LoginThread().start();
				} else {
					Toast.makeText(LoginActivity.this, "账号或密码不能为空", 1).show();
				}
			}
		});

	}

	private void initView() {
		imb1 = (ImageButton) findViewById(R.id.loginIb);
		et1 = (EditText) findViewById(R.id.et1);
		et2 = (EditText) findViewById(R.id.et2);
	}

	class LoginThread extends Thread {
		// public byte[] getByteByStream(InputStream ips) {
		// try {
		// byte buf[] = new byte[1024];
		// ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// int len = 0;
		// while ((len = ips.read(buf)) != -1) {
		// bos.write(buf, 0, len);
		// }
		// bos.close();
		// // ips.close();
		//
		// return bos.toByteArray();
		// } catch (Exception e) {
		// // 写日志
		// }
		// return null;
		// }

		@Override
		public void run() {
			try {
				String tempurl = "http://172.28.37.4:8080/test06/login?name="
						+ name + "&passwd=" + passwd;
				URL url = new URL(tempurl);
				HttpURLConnection hc = (HttpURLConnection) url.openConnection();
				hc.setReadTimeout(5000);
				hc.setDoInput(true);
				hc.setRequestMethod("GET");
				hc.setRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
				hc.connect();
				InputStream in = hc.getInputStream();
				// byte[] buf = getByteByStream(in);
				// in.close();
				Bitmap bt = BitmapFactory.decodeStream(in);
				in.close();
			//	使用完关流
				if (bt != null) {
					File bit = new File(LoginActivity.this.getFilesDir(),
							"icon.png");
					bit.createNewFile();
					OutputStream fo = new FileOutputStream(bit);
					bt.compress(Bitmap.CompressFormat.PNG, 100, fo);// 从网络获取头像写到本地
					LoginActivity.this
							.getSharedPreferences("loginusername",
									Context.MODE_PRIVATE).edit()
							.putString("name", name).commit();// 将用户名保存在本地
					Intent res = new Intent();
					res.putExtra("icon", bt);
					res.putExtra("name", name);
					Message msg = new Message();
					msg.what = YES;
					hd.sendMessage(msg);
					LoginActivity.this.setResult(2, res);
					LoginActivity.this.finish();
				} else {
					Message msg = new Message();
					msg.what = WRONG;
					hd.sendMessage(msg);
				}
				hd.sendEmptyMessage(0);

			} catch (Exception e) {
				Message msg = new Message();
				msg.what = ERROR;
				hd.sendMessage(msg);
			}
		}
	}

	class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			mp.colseDialog();
			if (msg.what == WRONG) {
				Toast.makeText(LoginActivity.this, "账号或密码错误，请重新登陆",
						Toast.LENGTH_LONG).show();
			} else if (msg.what == YES) {
				Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT)
						.show();
			} else if (msg.what == ERROR) {
				Toast.makeText(LoginActivity.this, "服务器未开启或者ip需要更改", Toast.LENGTH_LONG)
						.show();
			}
		}
	}
}
