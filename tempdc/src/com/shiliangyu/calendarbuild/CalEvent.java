package com.shiliangyu.calendarbuild;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;
import com.shiliangyu.datebase.ChooseDao;
import com.shiliangyu.datebase.DayEventDao;
import com.shiliangyu.tempdc.MainActivity;
import com.shiliangyu.tempdc.R;

public class CalEvent extends Activity {
	private PendingIntent piitent;
	private AlarmManager alar;
	private long noteTime;
	private int tmpbYear;
	private int tmpbMonth;
	private int tmpbDay;
	private int tmpbHour;
	private int tmpbMinute;
	private int tmpeYear;
	private int tmpeMonth;
	private int tmpeDay;
	private int tmpeHour;
	private int tmpeMinute;
	private String time;
	private String day;
	private Button save;
	private Button quxiao;
	private EditText edName;
	private EditText edAddress;
	private EditText edContent;
	private Button bt1;
	private Button bt2;
	private Button bt3;
	private Button bt4;
	private CheckBox cb;
	private CheckBox note;
	private RelativeLayout lls;
	private LinearLayout llus;
	private int postion;
	private int jumpMonth;
	private String actName;
	private String actAddress;
	private String beginTime;
	private String endTime;
	private String actContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cal_event);
		init();
		viewClick();
	}

	private void init() {
		alar = (AlarmManager) getApplicationContext().getSystemService(
				ALARM_SERVICE);
		Intent in = getIntent();
		SimpleDateFormat form = new SimpleDateFormat("HH:MM");
		Date date = new Date(System.currentTimeMillis());
		time = form.format(date);
		day = in.getStringExtra("time");
		jumpMonth = in.getIntExtra("month", 0);
		postion = in.getIntExtra("postion", 0);
		save = (Button) findViewById(R.id.cal_event_save);
		quxiao = (Button) findViewById(R.id.cal_event_quxiao);
		bt1 = (Button) findViewById(R.id.timeFrom);
		bt1.setText(day);
		bt2 = (Button) findViewById(R.id.timeTo);
		bt2.setText(day);
		bt3 = (Button) findViewById(R.id.timesFrom);
		bt3.setText(time);
		bt4 = (Button) findViewById(R.id.timesTo);
		bt4.setText(time);
		note = (CheckBox) findViewById(R.id.addCalEvent);
		note.setChecked(false);
		lls = (RelativeLayout) findViewById(R.id.event_day);
		llus = (LinearLayout) findViewById(R.id.event_time);
		cb = (CheckBox) findViewById(R.id.calEventCheck);
		cb.setChecked(false);
		edName = (EditText) findViewById(R.id.activityName);
		edAddress = (EditText) findViewById(R.id.activityAddress);
		edContent = (EditText) findViewById(R.id.activityContent);
	}

	private void viewClick() {
		quxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					CalEvent.this.finish();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				actName = edName.getText().toString().trim();
				actAddress = edAddress.getText().toString().trim();
				beginTime = bt1.getText().toString() + bt3.getText();
				endTime = bt2.getText().toString() + bt4.getText();
				actContent = "备注：" + edContent.getText().toString().trim();
				if (actName.equals("") || actAddress.equals("")
						|| actContent.equals("")) {
					Toast.makeText(CalEvent.this, "事件关键部分不能为空", 1).show();
				} else {
					// 设置闹铃
					if (note.isChecked() || noteTime != 0) {
						Intent in = new Intent(CalEvent.this,
								AlarmService.class);
						CalEvent.this.startService(in);
						Intent intent = new Intent();
						intent.putExtra("note", edContent.getText().toString());
						intent.putExtra("title", edName.getText().toString());
						intent.setAction("BC_ACTION");
						// Intent inte = new
						// Intent(AlarmDialog.this,AlarmService.class);
						// AlarmDialog.this.startService(inte);
						piitent = PendingIntent.getBroadcast(CalEvent.this, 0,
								intent, PendingIntent.FLAG_UPDATE_CURRENT);
						alar.setRepeating(AlarmManager.RTC_WAKEUP, noteTime, 0,
								piitent);
					} else {
						Toast.makeText(CalEvent.this, "请正确选择时间", 0).show();
					}
					// 回显结果：
					DayEventDao dd = new DayEventDao(CalEvent.this);
					dd.addEvent(jumpMonth, postion, beginTime, endTime,
							actName, actAddress, actContent);
					Intent in = new Intent();
					in.putExtra("actName", actName);
					in.putExtra("actAddress", actAddress);
					in.putExtra("beginTime", beginTime);
					in.putExtra("endTime", endTime);
					in.putExtra("actContent", actContent);
					CalEvent.this.setResult(2, in);
					ChooseDao cd = new ChooseDao(CalEvent.this);
					cd.addPostion(jumpMonth, postion);

					try {
						CalEvent.this.finish();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (cb.isChecked() == true) {
					llus.setVisibility(View.INVISIBLE);
					lls.setVisibility(View.VISIBLE);
				} else if (cb.isChecked() == false) {
					lls.setVisibility(View.INVISIBLE);
					llus.setVisibility(View.VISIBLE);
				}

			}
		});

		bt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog ad = new AlertDialog.Builder(CalEvent.this)
						.create();
				ad.setTitle("设置日期");
				LayoutInflater inf = CalEvent.this.getLayoutInflater();
				View view = inf.inflate(R.layout.cal_event_dialog, null);
				ad.setView(view);
				final DatePicker dp = (DatePicker) view
						.findViewById(R.id.datePicker);
				ad.setButton2("设置", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						tmpbYear = dp.getYear();
						tmpbMonth = dp.getMonth() + 1;
						tmpbDay = dp.getDayOfMonth();
						String dateBegin = tmpbYear + "年" + tmpbMonth + "月"
								+ tmpbDay + "日";
						bt1.setText(dateBegin);
						Toast.makeText(CalEvent.this, "开始日期设置成功", 0).show();

					}
				});
				ad.setButton3("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(CalEvent.this, "取消成功", 1).show();
					}
				});
				ad.show();
			}
		});

		bt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog ad = new AlertDialog.Builder(CalEvent.this)
						.create();
				ad.setTitle("设置日期");
				LayoutInflater inf = CalEvent.this.getLayoutInflater();
				View view = inf.inflate(R.layout.cal_event_dialog, null);
				final DatePicker dp = (DatePicker) view
						.findViewById(R.id.datePicker);
				ad.setView(view);
				ad.setButton2("设置", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						tmpeYear = dp.getYear();
						tmpeMonth = dp.getMonth() + 1;
						tmpeDay = dp.getDayOfMonth();
						String dateEnd = tmpeYear + "年" + tmpeMonth + "月"
								+ tmpeDay + "日";
						Toast.makeText(CalEvent.this, "结束日期设置成功", 0).show();
						bt2.setText(dateEnd);
					}
				});
				ad.setButton3("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(CalEvent.this, "取消成功", 1).show();
					}
				});
				ad.show();
			}
		});

		bt3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog ad = new AlertDialog.Builder(CalEvent.this)
						.create();
				ad.setTitle("设置时间");
				LayoutInflater inf = CalEvent.this.getLayoutInflater();
				View view = inf.inflate(R.layout.cal_event_dialog_fuben, null);
				ad.setView(view);
				final TimePicker tmp = (TimePicker) view
						.findViewById(R.id.timePicker);
				ad.setButton2("设置", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						tmpbHour = tmp.getCurrentHour();
						tmpbMinute = tmp.getCurrentMinute();
						String tmpbMinutes = null;
						if (tmpeMinute < 10 && tmpeMinute != 0) {
							tmpbMinutes = "0" + tmpbMinute;
						} else {
							tmpbMinutes = "" + tmpbMinute;
						}
						String selectTime = tmpbHour + ":" + tmpbMinutes;
						Toast.makeText(CalEvent.this, "开始时间设置成功设置成功", 0).show();
						bt3.setText(selectTime);
					}
				});
				ad.setButton3("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(CalEvent.this, "到达取消成功", 1).show();
					}
				});
				ad.show();
			}
		});

		bt4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog ad = new AlertDialog.Builder(CalEvent.this)
						.create();
				ad.setTitle("设置时间");
				LayoutInflater inf = CalEvent.this.getLayoutInflater();
				View view = inf.inflate(R.layout.cal_event_dialog_fuben, null);
				ad.setView(view);
				final TimePicker tmp = (TimePicker) view
						.findViewById(R.id.timePicker);
				ad.setButton2("设置", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						tmpeHour = tmp.getCurrentHour();
						tmpeMinute = tmp.getCurrentMinute();
						String tmpeMinutes = null;
						if (tmpeMinute < 10 && tmpeMinute != 0) {
							tmpeMinutes = "0" + tmpeMinute;
						} else {
							tmpeMinutes = "" + tmpeMinute;
						}
						String selectTime = tmpeHour + ":" + tmpeMinutes;
						Toast.makeText(CalEvent.this, "结束时间设置成功设置成功", 0).show();
						bt4.setText(selectTime);
					}
				});
				ad.setButton3("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(CalEvent.this, "到达取消成功", 1).show();
					}
				});
				ad.show();
			}
		});
		note.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked == true) {
					noteTime = setNote();
					if (noteTime != 0) {
						Toast.makeText(CalEvent.this, "提醒设置成功", 0).show();

					} else {
						note.setChecked(false);
					}
				} else if (isChecked == false) {
					Toast.makeText(CalEvent.this, "取消提醒", 0).show();
					return;
				}

			}
		});
	}

	private long setNote() {
		if (tmpbYear == 0 || tmpbMinute == 0) {
			Toast.makeText(CalEvent.this, "请先选择时间", 0).show();
		} else {
			String tmphs = null;
			String tmpms = null;
			String tmpMs = null;
			String tmpds = null;
			if (tmpbHour < 10) {
				tmphs = "0" + tmpbHour;
			} else {
				tmphs = "" + tmpbHour;
			}
			if (tmpbMinute < 10) {
				tmpms = "0" + tmpbMinute;
			} else {
				tmpms = "" + tmpbMinute;
			}
			if (tmpbMonth < 10) {
				tmpMs = "0" + tmpbMonth;
			} else {
				tmpMs = "" + tmpbMonth;
			}
			if (tmpbDay < 10) {
				tmpds = "0" + tmpbDay + 1;
			} else {
				tmpds = "" + tmpbDay;
			}
			String art = tmpbYear + "" + tmpMs + tmpds + tmphs + tmpms;
			SimpleDateFormat sm = new SimpleDateFormat("yyyyMMddHHmm");
			// long dd = 10000;
			Date armt = null;
			System.out.println(art);
			try {
				armt = sm.parse(art);
				return armt.getTime();
			} catch (Exception e) {
				Toast.makeText(CalEvent.this, "时间设置有误", 0).show();
			}
		}
		return 0;
	}

}
