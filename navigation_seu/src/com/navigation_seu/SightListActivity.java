package com.navigation_seu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SightListActivity extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.sightlist, new String[] { "title", "info", "img" },
				new int[] { R.id.title, R.id.info, R.id.img });
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (position == 0) {
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putDouble("lat", 31.900483);
			bundle.putDouble("lng", 118.884089);
			intent.putExtras(bundle);
			intent.setClass(SightListActivity.this, MapActivity.class);
			startActivity(intent);
			SightListActivity.this.finish();
		}
		if (position == 1) {
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putDouble("lat", 31.927463);
			bundle.putDouble("lng", 118.78973);
			intent.putExtras(bundle);
			intent.setClass(SightListActivity.this, MapActivity.class);
			startActivity(intent);
			SightListActivity.this.finish();
		}
		if (position == 2) {
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putDouble("lat", 31.948997);
			bundle.putDouble("lng", 118.828628);
			intent.putExtras(bundle);
			intent.setClass(SightListActivity.this, MapActivity.class);
			startActivity(intent);
			SightListActivity.this.finish();
		}
		super.onListItemClick(l, v, position, id);
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "方山风景区");
		map.put("info", "距今1000 万年前新近纪中新世时期一座火山");
		map.put("img", R.drawable.thumb);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "将军山旅游风景区");
		map.put("info", "这里曾是宋代牛首山大捷的古战场");
		map.put("img", R.drawable.jjs);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "恐龙园");
		map.put("info", "江宁区双龙大道1351号");
		map.put("img", R.drawable.kky);
		list.add(map);

		return list;
	}
}
