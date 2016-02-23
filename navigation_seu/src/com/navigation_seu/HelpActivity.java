package com.navigation_seu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class HelpActivity extends Activity {

	private ImageButton helpbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);

		helpbtn = (ImageButton) findViewById(R.id.helpbtn_view);
		helpbtn.setOnClickListener(new helpbtnListener());
	}

	public class helpbtnListener implements OnClickListener {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent(HelpActivity.this, MainActivity.class);
			startActivity(intent);
			HelpActivity.this.finish();
		}
	}

}
