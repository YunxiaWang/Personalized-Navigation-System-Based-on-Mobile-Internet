package com.navigation_seu;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends Activity {

	private EditText edittext1;
	private EditText edittext2;
	private ImageButton searchbtn1;
	private ImageButton searchbtn2;
	private ImageButton searchbtn;

	MKSearch mMKSearch = null;
	BMapManager mBMapMan = null;
	ListView mSuggestionList = null;

	double Lat1 = 0;
	double Lon1 = 0;

	int check = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mMKSearch = new MKSearch();
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("WElb1bTFpSTGf7nrcuSdWdQz", null);
		setContentView(R.layout.activity_search);
		mMKSearch.init(mBMapMan, new MySearchListener());

		mSuggestionList = (ListView) findViewById(R.id.listview1);
		mSuggestionList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				edittext1.setText(mSuggestionList.getItemAtPosition(arg2)
						.toString().toCharArray(), 0, mSuggestionList
						.getItemAtPosition(arg2).toString().length());
			}
		});

		edittext1 = (EditText) findViewById(R.id.edittext1_view);

		edittext2 = (EditText) findViewById(R.id.edittext2_view);

		searchbtn1 = (ImageButton) findViewById(R.id.searchbtn2_view);
		searchbtn1.setOnClickListener(new searchbtn1Listener());

		searchbtn2 = (ImageButton) findViewById(R.id.searchbtn1_view);
		searchbtn2.setOnClickListener(new searchbtn2Listener());

		searchbtn = (ImageButton) findViewById(R.id.searchbtn_view);
		searchbtn.setOnClickListener(new searchbtnListener());

	}

	public class searchbtn1Listener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			String str1 = edittext1.getText().toString();
			mMKSearch.suggestionSearch(str1, "南京");
		}
	}

	public class searchbtn2Listener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			String str2 = edittext2.getText().toString();
			mMKSearch.suggestionSearch(str2, "南京");
		}
	}

	public class searchbtnListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			String str1 = edittext1.getText().toString();
			String str2 = edittext2.getText().toString();
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("start", str1);
			bundle.putString("end", str2);
			intent.putExtras(bundle);
			intent.setClass(SearchActivity.this, MapActivity.class);
			startActivity(intent);
			SearchActivity.this.finish();
		}
	}

	public class MySearchListener implements MKSearchListener {
		@Override
		public void onGetAddrResult(MKAddrInfo res, int error) {
			if (error != 0) {
				String str = String.format("错误号：%d", error);
				Toast.makeText(SearchActivity.this, str, Toast.LENGTH_LONG)
						.show();
				return;
			}
			if (res.type == MKAddrInfo.MK_GEOCODE) {
				Lat1 = res.geoPt.getLatitudeE6() / 1e6;
				Lon1 = res.geoPt.getLongitudeE6() / 1e6;
			}
			if (res.type == MKAddrInfo.MK_REVERSEGEOCODE) {
				// 反地理编码：通过坐标点检索详细地址及周边poi
				String strInfo = res.strAddr;
				Toast.makeText(SearchActivity.this, strInfo, Toast.LENGTH_LONG)
						.show();
			}
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
		}

		@Override
		public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
				int arg2) {
		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult res, int iError) {
			if (iError != 0 || res == null) {
				Toast.makeText(SearchActivity.this, "抱歉，未找到结果",
						Toast.LENGTH_LONG).show();
				return;
			}
			int nSize = res.getSuggestionNum();
			String[] mStrSuggestions = new String[nSize];
			for (int i = 0; i < nSize; i++) {
				mStrSuggestions[i] = res.getSuggestion(i).city
						+ res.getSuggestion(i).key;
			}
			ArrayAdapter<String> suggestionString = new ArrayAdapter<String>(
					SearchActivity.this, android.R.layout.simple_list_item_1,
					mStrSuggestions);
			mSuggestionList.setAdapter(suggestionString);
		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
		}
	}

}