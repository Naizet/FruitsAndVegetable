package com.bishe.FV.function;

import com.bishe.FV.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

public class Search extends Activity {

	private MultiAutoCompleteTextView mactv;
	private ArrayAdapter<String> mactvAdapter;
	private String[] strs = new String[]{"你好！","你好！晓痕","你好！衡三","你好！敏旭","嗨！敏旭","嗨！徐帆"};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sou_itme);

		mactv = (MultiAutoCompleteTextView) findViewById(R.id.sou_search_edit);
		mactvAdapter = new ArrayAdapter<String>(Search.this, android.R.layout.simple_dropdown_item_1line,strs);
		mactv.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
		mactv.setAdapter(mactvAdapter);
	}
}
