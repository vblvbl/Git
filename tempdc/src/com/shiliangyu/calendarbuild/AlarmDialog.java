package com.shiliangyu.calendarbuild;

import java.io.IOException;
import com.shiliangyu.tempdc.R;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmDialog extends Activity {
	private TextView tv;
	private TextView noneTitle;
	private Button bt1;
	private Button bt2;
	private String content;
	private String title;
	private PendingIntent piitent;
	private AlarmManager alar;
	private MediaPlayer mpMediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nonealarm);
		init();
		setClick();
		playMusic();

	}

	private void playMusic() {
		mpMediaPlayer = new MediaPlayer();
		AssetManager am = getAssets();
		try {
			mpMediaPlayer.setDataSource(am.openFd("music.mp3")
					.getFileDescriptor());
			mpMediaPlayer.prepare();
			mpMediaPlayer.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setClick() {
		bt1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mpMediaPlayer.stop();
				AlarmDialog.this.finish();
				Intent it = new Intent(AlarmDialog.this, AlarmService.class);
				AlarmDialog.this.stopService(it);
			}
		});
		bt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("note", content);
				intent.putExtra("title", title);
				intent.setAction("BC_ACTION");
				// Intent inte = new
				// Intent(AlarmDialog.this,AlarmService.class);
				// AlarmDialog.this.startService(inte);
				piitent = PendingIntent.getBroadcast(AlarmDialog.this, 0,
						intent, PendingIntent.FLAG_UPDATE_CURRENT);
				alar.setRepeating(AlarmManager.RTC_WAKEUP,
						System.currentTimeMillis() + 5 * 60 * 1000, 0, piitent); // 提醒时间noteTime
				Toast.makeText(AlarmDialog.this, "五分钟后提醒", 0).show();
				mpMediaPlayer.stop();
				AlarmDialog.this.finish();
			}
		});
	}

	private void init() {
		tv = (TextView) findViewById(R.id.nonealarm_content);
		noneTitle = (TextView) findViewById(R.id.nonetitle);
		alar = (AlarmManager) this.getSystemService(ALARM_SERVICE);
		bt1 = (Button) findViewById(R.id.wozhidaole);
		bt2 = (Button) findViewById(R.id.guihuitongzhi);
		Intent it = getIntent();
		content = it.getStringExtra("note");
		title = it.getStringExtra("title");
		tv.setText(content);
		noneTitle.setText("N:" + title);
	}

}
