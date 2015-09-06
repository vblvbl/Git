package com.shiliangyu.tempdc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class MyListView extends ListView {
	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
	// TODO Auto-generated method stub
	if(ev.getAction() == MotionEvent.ACTION_MOVE){
	           return true;//½ûÖ¹Gridview½øÐÐ»¬¶¯
	       }
	return super.dispatchTouchEvent(ev);
	}
}
