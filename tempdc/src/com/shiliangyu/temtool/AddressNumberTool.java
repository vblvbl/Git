package com.shiliangyu.temtool;

import com.shiliangyu.tempdc.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AddressNumberTool extends Activity {
	private WebView wv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool_addressnum);
		wv = (WebView) findViewById(R.id.addressNum);
		wv.setWebViewClient(new WebViewClient());
		wv.loadUrl("http://youbian.911cha.com");
	}

}
