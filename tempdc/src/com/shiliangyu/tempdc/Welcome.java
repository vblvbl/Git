package com.shiliangyu.tempdc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class Welcome extends Activity {
	ProgressBar pb;
	private ImageView imv;
	private int i = 0;
	Handler myhd = new MyHandler();
	private final int ProgamPut = 1;
	private final int ChangeActivity = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		viewInit();

	}

	private void viewInit() {
		Animation am = AnimationUtils.loadAnimation(this, R.anim.welcomelogo);
		imv = (ImageView) findViewById(R.id.welcomeIn);
		imv.startAnimation(am);
		pb = (ProgressBar) findViewById(R.id.welcomeBar);
		new ProgramBarDo().start();
	}

	class ProgramBarDo extends Thread {

		@Override
		public void run() {
			while (i < 50) {
				try {
					Thread.sleep(60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				i++;
				Message msg = new Message();
				msg.what = ProgamPut;
				myhd.sendMessage(msg);
			}
			if (i == 50) {
				Message ms = new Message();
				ms.what = ChangeActivity;
				myhd.sendMessage(ms);
			}
		}
	}

	private class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == ProgamPut) {
				pb.setProgress(2 * i);
			} else if (msg.what == ChangeActivity) {
				Intent in = new Intent(Welcome.this, MainActivity.class);
				startActivity(in);
				Welcome.this.overridePendingTransition(R.anim.main,
						R.anim.welcome);
				Welcome.this.finish();
			}
		}

	}
}
