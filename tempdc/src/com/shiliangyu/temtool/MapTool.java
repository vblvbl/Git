package com.shiliangyu.temtool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapDoubleClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapLongClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMapTouchListener;
import com.baidu.mapapi.map.BaiduMap.SnapshotReadyCallback;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.shiliangyu.tempdc.R;

public class MapTool extends Activity {
	/**
	 * MapView �ǵ�ͼ���ؼ�
	 */
	private MapView mMapView;
	private BaiduMap mBaiduMap;

	/**
	 * ��ǰ�ص����
	 */
	private LatLng currentPt;
	/**
	 * ���ư�ť
	 */
	private Button zoomButton;
	private Button rotateButton;
	private Button overlookButton;
	private Button saveScreenButton;

	private String touchType;

	/**
	 * ������ʾ��ͼ״̬�����
	 */
	private TextView mStateBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.tool_map);
		mMapView = (MapView) findViewById(R.id.bmapView);
	}

	private void initListener() {
		mBaiduMap.setOnMapTouchListener(new OnMapTouchListener() {

			@Override
			public void onTouch(MotionEvent event) {

			}
		});

		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
			public void onMapClick(LatLng point) {
				touchType = "����";
				currentPt = point;
				updateMapState();
			}

			public boolean onMapPoiClick(MapPoi poi) {
				return false;
			}
		});
		mBaiduMap.setOnMapLongClickListener(new OnMapLongClickListener() {
			public void onMapLongClick(LatLng point) {
				touchType = "����";
				currentPt = point;
				updateMapState();
			}
		});
		mBaiduMap.setOnMapDoubleClickListener(new OnMapDoubleClickListener() {
			public void onMapDoubleClick(LatLng point) {
				touchType = "˫��";
				currentPt = point;
				updateMapState();
			}
		});
		mBaiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {
			public void onMapStatusChangeStart(MapStatus status) {
				updateMapState();
			}

			public void onMapStatusChangeFinish(MapStatus status) {
				updateMapState();
			}

			public void onMapStatusChange(MapStatus status) {
				updateMapState();
			}
		});
		zoomButton = (Button) findViewById(R.id.zoombutton);
		rotateButton = (Button) findViewById(R.id.rotatebutton);
		overlookButton = (Button) findViewById(R.id.overlookbutton);
		saveScreenButton = (Button) findViewById(R.id.savescreen);
		OnClickListener onClickListener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (view.equals(zoomButton)) {
					perfomZoom();
				} else if (view.equals(rotateButton)) {
					perfomRotate();
				} else if (view.equals(overlookButton)) {
					perfomOverlook();
				} else if (view.equals(saveScreenButton)) {
					// ��ͼ����SnapshotReadyCallback�б���ͼƬ�� sd ��
					mBaiduMap.snapshot(new SnapshotReadyCallback() {
						public void onSnapshotReady(Bitmap snapshot) {
							File file = new File("/mnt/sdcard/test.png");
							FileOutputStream out;
							try {
								out = new FileOutputStream(file);
								if (snapshot.compress(
										Bitmap.CompressFormat.PNG, 100, out)) {
									out.flush();
									out.close();
								}
								Toast.makeText(MapTool.this,
										"��Ļ��ͼ�ɹ���ͼƬ����: " + file.toString(),
										Toast.LENGTH_SHORT).show();
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
					Toast.makeText(MapTool.this, "���ڽ�ȡ��ĻͼƬ...",
							Toast.LENGTH_SHORT).show();

				}
				updateMapState();
			}

		};
		zoomButton.setOnClickListener(onClickListener);
		rotateButton.setOnClickListener(onClickListener);
		overlookButton.setOnClickListener(onClickListener);
		saveScreenButton.setOnClickListener(onClickListener);
	}

	/**
	 * �������� sdk ���ż���Χ�� [3.0,19.0]
	 */
	private void perfomZoom() {
		EditText t = (EditText) findViewById(R.id.zoomlevel);
		try {
			float zoomLevel = Float.parseFloat(t.getText().toString());
			MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(zoomLevel);
			mBaiduMap.animateMapStatus(u);
		} catch (NumberFormatException e) {
			Toast.makeText(this, "��������ȷ�����ż���", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * ������ת ��ת�Ƿ�Χ�� -180 ~ 180 , ��λ���� ��ʱ����ת
	 */
	private void perfomRotate() {
		EditText t = (EditText) findViewById(R.id.rotateangle);
		try {
			int rotateAngle = Integer.parseInt(t.getText().toString());
			MapStatus ms = new MapStatus.Builder(mBaiduMap.getMapStatus())
					.rotate(rotateAngle).build();
			MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
			mBaiduMap.animateMapStatus(u);
		} catch (NumberFormatException e) {
			Toast.makeText(this, "��������ȷ����ת�Ƕ�", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * ������ ���Ƿ�Χ�� -45 ~ 0 , ��λ�� ��
	 */
	private void perfomOverlook() {
		EditText t = (EditText) findViewById(R.id.overlookangle);
		try {
			int overlookAngle = Integer.parseInt(t.getText().toString());
			MapStatus ms = new MapStatus.Builder(mBaiduMap.getMapStatus())
					.overlook(overlookAngle).build();
			MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
			mBaiduMap.animateMapStatus(u);
		} catch (NumberFormatException e) {
			Toast.makeText(this, "��������ȷ�ĸ���", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * ���µ�ͼ״̬��ʾ���
	 */
	private void updateMapState() {
		if (mStateBar == null) {
			return;
		}
		String state = "";
		if (currentPt == null) {
			state = "�����������˫����ͼ�Ի�ȡ��γ�Ⱥ͵�ͼ״̬";
		} else {
			state = String.format(touchType + ",��ǰ���ȣ� %f ��ǰγ�ȣ�%f",
					currentPt.longitude, currentPt.latitude);
		}
		state += "\n";
		MapStatus ms = mBaiduMap.getMapStatus();
		state += String.format("zoom=%.1f rotate=%d overlook=%d", ms.zoom,
				(int) ms.rotate, (int) ms.overlook);
		mStateBar.setText(state);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mMapView.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mMapView.onDestroy();
	}

}
