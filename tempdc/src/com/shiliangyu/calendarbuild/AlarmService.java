package com.shiliangyu.calendarbuild;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;

public class AlarmService extends Service {
	private MyReceive myre;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(myre);
		myre=null;
	}

	@Override
	public void onCreate() {
		myre = new MyReceive();
		IntentFilter inf = new IntentFilter();
		inf.addAction("BC_ACTION");
		registerReceiver(myre, inf);
		super.onCreate();
	}

	class MyReceive extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String content = intent.getStringExtra("note");
			String title = intent.getStringExtra("title");
			Intent it = new Intent(context, AlarmDialog.class);
			it.putExtra("note", content);
			it.putExtra("title", title);
			it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(it);
		}

	}
}
