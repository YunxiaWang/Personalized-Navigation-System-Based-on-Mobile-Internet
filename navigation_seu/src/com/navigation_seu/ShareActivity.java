package com.navigation_seu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.navigation_seu.UrlToNet.DataCallback;
import com.navigation_seu.UrlToNet.DataCallback1;

public class ShareActivity extends Activity {

	private ImageButton sharebtn1;
	private ImageButton sharebtn2;
	private ImageButton sharebtn3;
	private EditText edittextshareid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);

		sharebtn1 = (ImageButton) findViewById(R.id.sharebtn1_view);
		sharebtn1.setOnClickListener(new sharebtn1Listener());
		sharebtn2 = (ImageButton) findViewById(R.id.sharebtn2_view);
		sharebtn2.setOnClickListener(new sharebtn2Listener());
		sharebtn3 = (ImageButton) findViewById(R.id.sharebtn3_view);
		sharebtn3.setOnClickListener(new sharebtn3Listener());
		edittextshareid = (EditText) findViewById(R.id.edittextshareid_view);
	}

	public class sharebtn1Listener implements OnClickListener {
		@Override
		public void onClick(View view) {
			Time t = new Time();
			t.setToNow();
			String id = edittextshareid.getText().toString();
			double lat = 31.891715;
			double lon = 118.821915;
			String latstr = Double.toString(lat);
			String latlonr = Double.toString(lon);
			postresponsedata(id, latstr, latlonr);
		}
	}

	public class sharebtn2Listener implements OnClickListener {
		@Override
		public void onClick(View view) {
			String id = edittextshareid.getText().toString();
			getresponsedata(id);
		}
	}

	public class sharebtn3Listener implements OnClickListener {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(ShareActivity.this, MainActivity.class);
			startActivity(intent);
			ShareActivity.this.finish();
		}
	}

	public void getresponsedata(String id) {
		UrlToNet.doGet(id, new DataCallback() {
			public void process(getdata getresponse) {
				if (getresponse == null) {
					System.out.println(0);
					Toast.makeText(getApplicationContext(), "no",
							Toast.LENGTH_SHORT).show();
				} else {
					System.out.println(getresponse.getnum());
					Toast.makeText(getApplicationContext(), "yes",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(ShareActivity.this,
							MapActivity.class);
					startActivity(intent);
				}
			}
		});
	}

	public void postresponsedata(String id, String lat, String lon) {
		UrlToNet.doPost(id, lat, lon, new DataCallback1() {
			public void process(postdata postresponse) {
				// TODO Auto-generated method stub
				if (postresponse == null) {
					System.out.println(0);
					Toast.makeText(getApplicationContext(), "no",
							Toast.LENGTH_SHORT).show();
				} else {
					System.out.println(postresponse.getid());
					Toast.makeText(getApplicationContext(), "yes",
							Toast.LENGTH_SHORT).show();

				}
			}
		});
	}

}
