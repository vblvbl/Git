package com.shiliangyu.tempdc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shiliangyu.calendarbuild.CalAdapter;
import com.shiliangyu.calendarbuild.CalEvent;
import com.shiliangyu.datebase.ChooseDao;
import com.shiliangyu.datebase.DayEventDao;
import com.shiliangyu.domain.DayEvent;
import com.shiliangyu.serviceutil.MySharedPreferences;

public class ScheduleFragment extends Fragment {
	private TextView noEvent;
	private ListView calListView;
	private TextView today;
	private TextView leftTv;
	private TextView rightTv;
	private View view;
	private CalAdapter cv = null;
	private GridView gridView = null;
	private TextView topText = null;
	private static int jumpMonth = 0; // 每次滑动，增加或减去一个月,默认为0（即显示当前月）
	private static int jumpYear = 0; // 滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	private String currentDate = "";
	private Bundle bd = null;// 发送参数
	private Bundle bun = null;// 接收参数
	private String ruzhuTime;
	private String lidianTime;
	private String state = "";
	private List<DayEvent> calendarEventList;
	private int[] flagAll;

	public ScheduleFragment() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		currentDate = sdf.format(date); // 当期日期
		year_c = Integer.parseInt(currentDate.split("-")[0]);
		month_c = Integer.parseInt(currentDate.split("-")[1]);
		day_c = Integer.parseInt(currentDate.split("-")[2]);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.calendar, null);
		init(view);
		myViewClick(view);
		return view;
	}

	private void init(View view) {
		calendarEventList = new ArrayList<DayEvent>();
		noEvent = (TextView) view.findViewById(R.id.calendar_noevent);
		calListView = (ListView) view.findViewById(R.id.calendarListView);
		leftTv = (TextView) view.findViewById(R.id.left_img);
		rightTv = (TextView) view.findViewById(R.id.right_img);
		today = (TextView) view.findViewById(R.id.btn_goback_to_today);
		cv = new CalAdapter(getActivity(), getActivity().getResources(),
				jumpMonth, jumpYear, year_c, month_c, day_c);
		addGridView(jumpMonth);
		gridView.setAdapter(cv);
		topText = (TextView) view.findViewById(R.id.tv_month);
		addTextToTopTextView(topText);

	}

	private void myViewClick(View view) {
		today.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				jumpMonth = 0;
				addGridView(jumpMonth);
				addTextToTopTextView(topText);
			}
		});
		rightTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				jumpMonth++;
				addGridView(jumpMonth); // 添加一个gridView
				addTextToTopTextView(topText);

			}
		});
		leftTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				jumpMonth--; // 上一个月
				addGridView(jumpMonth); // 添加一个gridView
				addTextToTopTextView(topText);

			}
		});

	}

	public void addTextToTopTextView(TextView view) {
		StringBuffer textDate = new StringBuffer();
		textDate.append(cv.getShowYear()).append("年").append(cv.getShowMonth())
				.append("月").append("\t");
		view.setText(textDate);
		view.setTextColor(Color.WHITE);
		view.setTypeface(Typeface.DEFAULT_BOLD);
	}

	private void addGridView(final int jumpMonth) {
		flagAll = new int[] {};
		gridView = (GridView) view.findViewById(R.id.gridview);
		cv = new CalAdapter(getActivity(), getResources(), jumpMonth, jumpYear,
				year_c, month_c, day_c);
		gridView.setAdapter(cv);
		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				boolean flagtemp = false;
				ChooseDao cd = new ChooseDao(getActivity());
				if (cd.findAll(jumpMonth) != null) {
					List ls = cd.findAll(jumpMonth);
					Iterator it = ls.iterator();
					while (it.hasNext()) {
						int post = Integer.parseInt(it.next().toString());
						if (arg2 == post) {
							flagtemp = true;
						}
					}
				}
				if (flagtemp) {
					AlertDialog at = new AlertDialog.Builder(getActivity())
							.setTitle("删除当前备忘")
							.setMessage("确定删除当前的备忘？")
							.setNegativeButton("确定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											DayEventDao de = new DayEventDao(
													getActivity());
											de.delEvent(jumpMonth, arg2);
											ChooseDao ch = new ChooseDao(
													getActivity());
											ch.delPostion(jumpMonth, arg2);
											cv = new CalAdapter(getActivity(), getResources(), jumpMonth, jumpYear,
													year_c, month_c, day_c);
											gridView.setAdapter(cv);
											Toast.makeText(getActivity(), "删除成功", 1).show();
										}

									})
							.setPositiveButton("取消",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub

										}
									}).create();
					at.show();
				} else {
					return false;
				}
				return false;
			}

		});
		gridView.setOnItemClickListener(new OnItemClickListener() {
			// gridView中的每一个item的点击事件

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// 点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
				int startPosition = cv.getStartPositon();
				int endPosition = cv.getEndPosition();
				boolean tempFlag = false;
				if (startPosition <= position + 7
						&& position <= endPosition - 7) {
					String scheduleDay = cv.getDateByClickItem(position).split(
							"\\.")[0]; // 这一天的阳历
					String ruzhuYear = cv.getShowYear();
					String scheduleMonth = cv.getShowMonth();
					ruzhuTime = ruzhuYear + "年" + scheduleMonth + "月"
							+ scheduleDay + "日";
					ChooseDao cd = new ChooseDao(getActivity());
					if (cd.findAll(jumpMonth) != null) {
						List ls = cd.findAll(jumpMonth);
						Iterator it = ls.iterator();
						while (it.hasNext()) {
							int post = Integer.parseInt(it.next().toString());
							if (position == post) {
								tempFlag = true;
							}
						}
					}

					if (tempFlag) {
						DayEventDao dd = new DayEventDao(getActivity());
						calendarEventList = dd.readEvent(jumpMonth, position);
						noEvent.setVisibility(View.INVISIBLE);
						calListView.setVisibility(View.VISIBLE);
						calListView.setAdapter(new MyAdapter());
					} else {
						Intent intent = new Intent();
						intent.putExtra("month", jumpMonth);
						intent.putExtra("postion", position);
						intent.putExtra("time", ruzhuTime);
						intent.setClass(getActivity(), CalEvent.class);
						startActivityForResult(intent, 2);
					}

				}

			}

		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 2 && resultCode == 2) {
			String beginTime = data.getStringExtra("beginTime");
			String endTime = data.getStringExtra("endTime");
			String actName = data.getStringExtra("actName");
			String actAddress = data.getStringExtra("actAddress");
			String actContent = data.getStringExtra("actContent");
			String[] info = new String[] { beginTime, endTime, actName,
					actAddress, actContent };
			DayEvent day = new DayEvent();
			day.setBeginTime(beginTime);
			day.setEndTime(endTime);
			day.setActName(actName);
			day.setActAddress(actAddress);
			day.setActContent(actContent);
			calendarEventList.add(day);
			if (calendarEventList != null) {
				noEvent.setVisibility(View.INVISIBLE);
				calListView.setVisibility(View.VISIBLE);
				calListView.setAdapter(new MyAdapter());
				cv = new CalAdapter(getActivity(), getResources(), jumpMonth, jumpYear,
						year_c, month_c, day_c);
				gridView.setAdapter(cv);
			}

		}
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return calendarEventList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View lsv = View.inflate(getActivity(), R.layout.calendarlist, null);
			TextView tv1 = (TextView) lsv.findViewById(R.id.calendarlist_tv1);
			TextView tv2 = (TextView) lsv.findViewById(R.id.calendarlist_tv2);
			TextView tv3 = (TextView) lsv.findViewById(R.id.calendarlist_tv3);
			TextView tv4 = (TextView) lsv.findViewById(R.id.calendarlist_tv4);
			TextView tv5 = (TextView) lsv.findViewById(R.id.calendarlist_tv5);
			DayEvent day = calendarEventList.get(position);
			tv1.setText(day.getBeginTime());
			tv2.setText(day.getEndTime());
			tv3.setText(day.getActName());
			tv4.setText(day.getActAddress());
			tv5.setText(day.getActContent());
			return lsv;
		}

	}
}
