package com.navigation_seu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	private ImageButton navigationbutton;
	private ImageButton sharebutton;
	private ImageButton surroundbutton;
	private ImageButton helpbutton;
	private ImageButton quitbutton;
	private ImageButton sightbutton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		navigationbutton = (ImageButton) findViewById(R.id.startbtn1);
		navigationbutton.setOnClickListener(new navigationbuttonListener());
		sharebutton = (ImageButton) findViewById(R.id.startbtn2);
		sharebutton.setOnClickListener(new sharebuttonListener());
		surroundbutton = (ImageButton) findViewById(R.id.startbtn3);
		surroundbutton.setOnClickListener(new surroundbuttonListener());
		helpbutton = (ImageButton) findViewById(R.id.startbtn4);
		helpbutton.setOnClickListener(new helpbuttonListener());
		sightbutton = (ImageButton) findViewById(R.id.startbtn5);
		sightbutton.setOnClickListener(new sightbuttonListener());
		quitbutton = (ImageButton) findViewById(R.id.startbtn6);
		quitbutton.setOnClickListener(new quitbuttonListener());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class navigationbuttonListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			Intent intent = new Intent(MainActivity.this, SearchActivity.class);
			startActivity(intent);

		}

	}

	public class sharebuttonListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			Intent intent = new Intent(MainActivity.this, ShareActivity.class);
			startActivity(intent);

		}

	}

	public class surroundbuttonListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			Intent intent = new Intent(MainActivity.this, MapActivity.class);
			startActivity(intent);

		}

	}

	public class helpbuttonListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			Intent intent = new Intent(MainActivity.this, HelpActivity.class);
			startActivity(intent);

		}

	}

	public class quitbuttonListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			MainActivity.this.finish();
		}

	}

	public class sightbuttonListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			Intent intent = new Intent(MainActivity.this,
					SightListActivity.class);
			startActivity(intent);

		}

	}
}
