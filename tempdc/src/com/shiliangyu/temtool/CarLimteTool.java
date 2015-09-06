package com.shiliangyu.temtool;

import com.shiliangyu.tempdc.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CarLimteTool extends Activity {
	private WebView wv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_carlimite);
		wv = (WebView) findViewById(R.id.carlimit);
		wv.setWebViewClient(new WebViewClient());
		wv.loadUrl("http://xianxing.911cha.com");
	}

}
