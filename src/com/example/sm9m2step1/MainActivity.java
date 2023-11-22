package com.example.sm9m2step1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jnidriver.*;

public class MainActivity extends Activity {
	
	ReceiveThread mSegThread;
	boolean mThreadRun = true;
	
	JNIDriver mDriver = new JNIDriver();
	
	int direction = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btn1 = (Button)findViewById(R.id.button1);
		Button btn2 = (Button)findViewById(R.id.button2);
		Button btn3 = (Button)findViewById(R.id.button3);
		
		btn1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				direction = 1;
				if (mSegThread == null) {
					mSegThread = new ReceiveThread();
					mSegThread.start();
				}
			}
		});
		
		btn2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				direction = 2;
				if (mSegThread == null) {
					mSegThread = new ReceiveThread();
					mSegThread.start();
				}
			}
		});
		
		btn3.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				direction = 0;
				if (mSegThread != null) {
					mSegThread = null;
				}
				mDriver.setMotor(0);
			}
		});
	}
	
	private class ReceiveThread extends Thread {
		@Override
		public void run() {
			super.run();
			while(mThreadRun) {
				mDriver.setMotor(direction);
			}
		}
	}
	
	@Override
	protected void onPause() {
		mDriver.close();
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		if(mDriver.open("/dev/sm9s5422_step") < 0) {
			Toast.makeText(MainActivity.this, "Driver Open Failed", Toast.LENGTH_SHORT).show();
		}
		super.onResume();
	}
}
