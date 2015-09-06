package com.shiliangyu.tempdc;

import android.R.drawable;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shiliangyu.temtool.AddressNumberTool;
import com.shiliangyu.temtool.CarLimteTool;
import com.shiliangyu.temtool.DownloadToolActivity;
import com.shiliangyu.temtool.MapTool;
import com.shiliangyu.temtool.PersonNumberTool;

public class ToolFragment extends Fragment {
	private int[] icon;
	private String[] name;
	private GridView gridview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tool, null);
		viewInit(view);
		setEvent();
		return view;
	}

	private void viewInit(View view) {
		icon = new int[] { R.drawable.toolmap, R.drawable.toolxianhao,
				R.drawable.tooldownload, R.drawable.toolyoubian,
				R.drawable.toolshenfenzhen, R.drawable.toolchangyongdianhua,
				R.drawable.toolpacong, R.drawable.tooldaojishi,
				R.drawable.tooljishiben };
		name = new String[] { "地图", "行车限号", "下载工具", "邮编", "身份证", "常用电话", "爬虫",
				"倒计时", "路线" };
		gridview = (GridView) view.findViewById(R.id.selectGridView);
		gridview.setAdapter(new myAdapter());
	}

	private void setEvent() {

		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					Intent in = new Intent();
					in.setClass(ToolFragment.this.getActivity(), MapTool.class);
					startActivity(in);
					break;
				case 1:
					Intent in1 = new Intent();
					in1.setClass(ToolFragment.this.getActivity(),
							CarLimteTool.class);
					startActivity(in1);
					break;
				case 2:
					Intent in2 = new Intent();
					in2.setClass(ToolFragment.this.getActivity(),
							DownloadToolActivity.class);
					startActivity(in2);
					getActivity().overridePendingTransition(
							R.anim.toolselecttotool, R.anim.tooltotoolselect);

					break;
				case 3:
					Intent in3 = new Intent();
					in3.setClass(ToolFragment.this.getActivity(),
							AddressNumberTool.class);
					startActivity(in3);
					break;
				case 4:
					Intent in4 = new Intent();
					in4.setClass(ToolFragment.this.getActivity(),
							PersonNumberTool.class);
					startActivity(in4);
					break;
				default:
					break;
				}

			}
		});
	}

	class myAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return name.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = ToolFragment.this.getActivity().getLayoutInflater()
					.inflate(R.layout.toolselect_gridview, null);
			ImageView imv = (ImageView) view.findViewById(R.id.tsGVImage);
			TextView tv = (TextView) view.findViewById(R.id.tsGVText);
			LinearLayout ln = (LinearLayout) view.findViewById(R.id.tsGVbg);
			switch (position) {
			case 0:
				imv.setBackgroundResource(icon[0]);
				tv.setText(name[0]);
				ln.setBackgroundResource(R.color.grid1);
				break;
			case 1:
				imv.setBackgroundResource(icon[1]);
				tv.setText(name[1]);
				ln.setBackgroundResource(R.color.grid2);
				break;
			case 2:
				imv.setBackgroundResource(icon[2]);
				tv.setText(name[2]);
				ln.setBackgroundResource(R.color.grid3);
				break;
			case 3:
				imv.setBackgroundResource(icon[3]);
				tv.setText(name[3]);
				ln.setBackgroundResource(R.color.grid4);
				break;
			case 4:
				imv.setBackgroundResource(icon[4]);
				tv.setText(name[4]);
				ln.setBackgroundResource(R.color.grid5);
				break;
			case 5:
				imv.setBackgroundResource(icon[5]);
				tv.setText(name[5]);
				ln.setBackgroundResource(R.color.grid6);
				break;
			case 6:
				imv.setBackgroundResource(icon[6]);
				tv.setText(name[6]);
				ln.setBackgroundResource(R.color.grid7);
				break;
			case 7:
				imv.setBackgroundResource(icon[7]);
				tv.setText(name[7]);
				ln.setBackgroundResource(R.color.grid8);
				break;
			case 8:
				imv.setBackgroundResource(icon[8]);
				tv.setText(name[8]);
				ln.setBackgroundResource(R.color.grid9);
				break;
			}

			return view;
		}

	}

}
