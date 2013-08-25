package scut.houzhi.viewboard;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import scut.houzhi.view.RotateViewGroup;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final ViewGroup group = (ViewGroup)findViewById(R.id.group);
		
		final RotateViewGroup viewGroup = new RotateViewGroup(group);
		group.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(viewGroup.isOpened())
					viewGroup.close();
				else
					viewGroup.open();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
