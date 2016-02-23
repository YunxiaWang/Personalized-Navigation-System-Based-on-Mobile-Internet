package com.navigation_seu;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class MapActivity extends Activity {

	private ImageButton mapbtn1;
	private ImageButton mapbtn2;
	private ImageButton mapbtn3;
	private ImageButton mapbtn4;
	private ImageButton mapsearchbtn1;
	private ImageButton mapsearchbtn2;
	private ImageButton mapsearchbtn3;

	String startstr;
	String endstr;

	BMapManager mBMapMan = null;
	MKSearch mMKSearch = null;
	MapView mMapView = null;
	private MapController mMapController = null;

	// 定位
	LocationClient mLocClient;
	LocationData locData = null;
	public MyLocationListenner myListener = new MyLocationListenner();
	locationOverlay myLocationOverlay = null;
	boolean isRequest = false;
	boolean isFirstLoc = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// map初始化
		mMKSearch = new MKSearch();
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("WElb1bTFpSTGf7nrcuSdWdQz", null);

		setContentView(R.layout.activity_map);

		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapController = mMapView.getController();
		mMapController.setZoom(14);
		mMapView.getController().enableClick(true);
		mMapView.setBuiltInZoomControls(true);

		mMKSearch.init(mBMapMan, new MySearchListener());

		GeoPoint point = new GeoPoint((int) (31.891715 * 1E6),
				(int) (118.821915 * 1E6));
		mMapController.setCenter(point);

		// 定位初始化
		mLocClient = new LocationClient(this);
		locData = new LocationData();
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setCoorType("bd09ll");
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		mLocClient.requestLocation();

		// 定位图层初始化
		myLocationOverlay = new locationOverlay(mMapView);
		myLocationOverlay.setData(locData);
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		mMapView.refresh();

		// 得到内容
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			startstr = bundle.getString("start");
			endstr = bundle.getString("end");
			double lat = 0;
			double lng = 0;
			lat = bundle.getDouble("lat");
			lng = bundle.getDouble("lng");

			if (startstr != null) {
				MKPlanNode stNode = new MKPlanNode();
				stNode.name = startstr;
				MKPlanNode enNode = new MKPlanNode();
				enNode.name = endstr;
				mMKSearch.drivingSearch("南京", stNode, "南京", enNode);
			}
			if (lat != 0) {
				MKPlanNode stNode = new MKPlanNode();
				stNode.pt = point;
				MKPlanNode enNode = new MKPlanNode();
				enNode.pt = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
				mMKSearch.drivingSearch("南京", stNode, "南京", enNode);
			}
		}

		mapbtn1 = (ImageButton) findViewById(R.id.mapbtn1_view);
		mapbtn1.setOnClickListener(new mapbtn1Listener());
		mapbtn2 = (ImageButton) findViewById(R.id.mapbtn2_view);
		mapbtn2.setOnClickListener(new mapbtn2Listener());
		mapbtn3 = (ImageButton) findViewById(R.id.mapbtn3_view);
		mapbtn3.setOnClickListener(new mapbtn3Listener());
		mapbtn4 = (ImageButton) findViewById(R.id.mapbtn4_view);
		mapbtn4.setOnClickListener(new mapbtn4Listener());
		mapsearchbtn1 = (ImageButton) findViewById(R.id.mapsearchbtn1_view);
		mapsearchbtn1.setOnClickListener(new mapsearchbtn1Listener());
		mapsearchbtn2 = (ImageButton) findViewById(R.id.mapsearchbtn2_view);
		mapsearchbtn2.setOnClickListener(new mapsearchbtn2Listener());
		mapsearchbtn3 = (ImageButton) findViewById(R.id.mapsearchbtn3_view);
		mapsearchbtn3.setOnClickListener(new mapsearchbtn3Listener());
	}

	public class mapsearchbtn1Listener implements OnClickListener {
		@Override
		public void onClick(View view) {
			MKPlanNode stNode = new MKPlanNode();
			stNode.name = startstr;
			MKPlanNode enNode = new MKPlanNode();
			enNode.name = endstr;
			if (startstr != null) {
				mMKSearch.drivingSearch("南京", stNode, "南京", enNode);
			}
		}
	}

	public class mapsearchbtn2Listener implements OnClickListener {
		@Override
		public void onClick(View view) {
			MKPlanNode stNode = new MKPlanNode();
			stNode.name = startstr;
			MKPlanNode enNode = new MKPlanNode();
			enNode.name = endstr;
			if (startstr != null) {
				mMKSearch.walkingSearch("南京", stNode, "南京", enNode);
			}
		}
	}

	public class mapsearchbtn3Listener implements OnClickListener {
		@Override
		public void onClick(View view) {
			MKPlanNode stNode = new MKPlanNode();
			stNode.name = startstr;
			MKPlanNode enNode = new MKPlanNode();
			enNode.name = endstr;
			if (startstr != null) {
				mMKSearch.transitSearch("北京", stNode, enNode);
			}
		}
	}

	public class mapbtn1Listener implements OnClickListener {
		@Override
		public void onClick(View view) {
			mLocClient.requestLocation();
			mMKSearch.poiSearchNearBy("加油站", new GeoPoint(
					(int) (31.891715 * 1E6), (int) (118.821915 * 1E6)), 5000);

		}
	}

	public class mapbtn2Listener implements OnClickListener {
		@Override
		public void onClick(View view) {
			mMKSearch.poiSearchNearBy("停车场", new GeoPoint(
					(int) (31.891715 * 1E6), (int) (118.821915 * 1E6)), 5000);
		}
	}

	public class mapbtn3Listener implements OnClickListener {
		@Override
		public void onClick(View view) {
			mMKSearch.poiSearchNearBy("美食", new GeoPoint(
					(int) (31.891715 * 1E6), (int) (118.821915 * 1E6)), 5000);
		}
	}

	public class mapbtn4Listener implements OnClickListener {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(MapActivity.this, MainActivity.class);
			startActivity(intent);
			MapActivity.this.finish();
		}
	}

	@Override
	protected void onDestroy() {
		mMapView.destroy();
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		if (mBMapMan != null) {
			mBMapMan.start();
		}
		super.onResume();
	}

	public class MySearchListener implements MKSearchListener {
		@Override
		public void onGetAddrResult(MKAddrInfo res, int error) {
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult result,
				int iError) {
			if (iError != 0 || result == null) {
				Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			RouteOverlay routeOverlay = new RouteOverlay(MapActivity.this,
					mMapView);
			routeOverlay.setData(result.getPlan(0).getRoute(0));
			mMapView.getOverlays().add(routeOverlay);
			mMapView.refresh();
			mMapView.getController().animateTo(result.getStart().pt);
		}

		@Override
		public void onGetPoiDetailSearchResult(int res, int arg1) {
		}

		@Override
		public void onGetPoiResult(MKPoiResult res, int type, int error) {
			if (error == MKEvent.ERROR_RESULT_NOT_FOUND) {
				Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG)
						.show();
				return;
			} else if (error != 0 || res == null) {
				Toast.makeText(MapActivity.this, "搜索出错啦..", Toast.LENGTH_LONG)
						.show();
				return;
			}
			PoiOverlay poiOverlay = new PoiOverlay(MapActivity.this, mMapView);
			poiOverlay.setData(res.getAllPoi());
			mMapView.getOverlays().clear();
			mMapView.getOverlays().add(poiOverlay);
			mMapView.refresh();
			for (MKPoiInfo info : res.getAllPoi()) {
				if (info.pt != null) {
					mMapView.getController().animateTo(info.pt);
					break;
				}
			}
		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
				int arg2) {
		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult res, int iError) {
		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult result,
				int iError) {
			if (iError != 0 || result == null) {
				Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			RouteOverlay routeOverlay = new RouteOverlay(MapActivity.this,
					mMapView);
			routeOverlay.setData(result.getPlan(0).getRoute(0));
			mMapView.getOverlays().add(routeOverlay);
			mMapView.refresh();
			mMapView.getController().animateTo(result.getStart().pt);
		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult result,
				int iError) {
			if (iError != 0 || result == null) {
				Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			RouteOverlay routeOverlay = new RouteOverlay(MapActivity.this,
					mMapView);
			routeOverlay.setData(result.getPlan(0).getRoute(0));
			mMapView.getOverlays().add(routeOverlay);
			mMapView.refresh();
			mMapView.getController().animateTo(result.getStart().pt);
		}
	}

	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceivePoi(BDLocation location) {
			if (location == null)
				return;

			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			locData.accuracy = location.getRadius();
			locData.direction = location.getDerect();
			myLocationOverlay.setData(locData);

			mMapView.refresh();
			if (isRequest || isFirstLoc) {
				mMapController.animateTo(new GeoPoint(
						(int) (locData.latitude * 1e6),
						(int) (locData.longitude * 1e6)));
				isRequest = false;
				myLocationOverlay.setLocationMode(LocationMode.FOLLOWING);
			}
			String str1 = Double.toString(locData.latitude);
			String str2 = Double.toString(locData.longitude);
			String str = str1 + "," + str2;
			Toast.makeText(MapActivity.this, str, Toast.LENGTH_LONG).show();
			isFirstLoc = false;
		}

		@Override
		public void onReceiveLocation(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	public class locationOverlay extends MyLocationOverlay {
		public locationOverlay(MapView mapView) {
			super(mapView);
		}

		@Override
		protected boolean dispatchTap() {
			return true;
		}
	}

}