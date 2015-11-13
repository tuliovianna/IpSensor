package com.example.IpSensor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.ipsensor.R;

public class ScanActivity extends Activity {
	
	private static final String PATTERN = 
	        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	EditText input;
    Button btn;
    TextView out;
    String command;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);
		input = (EditText)findViewById(R.id.txtScan);
        btn = (Button)findViewById(R.id.btnScan);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (input.getText().toString().equals("")) {
					AlertDialog.Builder dialog = new AlertDialog.Builder(ScanActivity.this);
					dialog.setTitle("Alarm");
					dialog.setMessage("Empty field.");
					dialog.setNeutralButton("Ok", null);
					dialog.show();
				} else {
					if (!validateIp(input.getText().toString())) {
						AlertDialog.Builder dialog = new AlertDialog.Builder(ScanActivity.this);
						dialog.setTitle("Alarm");
						dialog.setMessage("Incorrect IP format.");
						dialog.setNeutralButton("Ok", null);
						dialog.show();
					} else {
						if (!isConnected()) {
							AlertDialog.Builder dialog = new AlertDialog.Builder(ScanActivity.this);
							dialog.setTitle("Alarm");
							dialog.setMessage("Without internet connection.");
							dialog.setNeutralButton("Ok", null);
							dialog.show();
						} else {
							Intent nextActivityScan = new Intent(ScanActivity.this, NextActivityScan.class);
							command = input.getText().toString(); 
			                nextActivityScan.putExtra("ip", command); 
			                input.setText("");
			                startActivity(nextActivityScan);
						}
					
					}
				}
				 
			}
		});
	}
	
	public boolean validateIp(String ip) {
		String regexIp = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
						 "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
						 "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
						 "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";				
		
		Pattern patternIp = Pattern.compile(regexIp);
		Matcher matcher = patternIp.matcher(ip);
		
		if(matcher.find()) {
			return true;
		}
		
		return false;
	}
	
	public boolean isConnected() {
		boolean conect;
		ConnectivityManager conectivtyManager = (ConnectivityManager) 
				getSystemService(Context.CONNECTIVITY_SERVICE); 
		if (conectivtyManager.getActiveNetworkInfo() != null  
	            && conectivtyManager.getActiveNetworkInfo().isAvailable()  
	            && conectivtyManager.getActiveNetworkInfo().isConnected()) {  
	        conect = true;  
	    } else {  
	        conect = false;  
	    }  
	    return conect;  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scan, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
