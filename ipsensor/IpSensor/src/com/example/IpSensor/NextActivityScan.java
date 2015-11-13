package com.example.IpSensor;

import java.io.IOException;
import java.net.UnknownHostException;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.ipsensor.R;

public class NextActivityScan extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getActionBar().setTitle("Output Scan");
		setContentView(R.layout.activity_next_scan);
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT > 9) {
        	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
		}
		new Thread(new Runnable() {
			public void run(){
		        Intent intent = getIntent();
		        String ip = intent.getStringExtra("ip");
		        ScanDAO daoScan = new ScanDAO();
		        
		        try {
					PortScan scan = new PortScan(ip);
					String outp = scan.scanMainPort();
		            Log.d("Output", outp);
		            Scan s = new Scan(ip,outp);    
		            Intent nextActivityScan = new Intent(NextActivityScan.this, OutputScan.class);
	                nextActivityScan.putExtra("dados", outp ); 
	                startActivity(nextActivityScan);
	                boolean resultado = daoScan.inserirScan(s);
		            Log.d("ExemploSCAN", resultado + "");  
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {

					e.printStackTrace();
				}  
			}
		}).start();	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.next_activity_scan, menu);
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
