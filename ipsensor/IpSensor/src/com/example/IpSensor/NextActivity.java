package com.example.IpSensor;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.ksoap2.transport.HttpResponseException;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import com.example.ipsensor.R;

public class NextActivity extends Activity {
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
        	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
        }
        TextView out = new TextView(this); 
        ScrollView scroller = new ScrollView(this);
        Intent intent = getIntent();
        String ip = intent.getStringExtra("ip");  
        PingWrapperDAO dao = new PingWrapperDAO();
        try {
            PingWrapper ping = new PingWrapper(ip); 
            ping.traceroute();
            String outp = ping.outputFormat();
            out.setText(outp);
            scroller.addView(out);
            Log.d("Output", outp);
            String hops = setHops(ping.getHops());
            Ping p = new Ping(ping.getUrl(),outp); 
			boolean resultado = dao.inserirPing(p);
			Log.d("ExemploWS", resultado + "");      
        } catch (UnknownHostException e) { 
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (HttpResponseException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        setContentView(scroller);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.next, menu);
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
    
    public String setHops(ArrayList<String> hops) {
    	String result = "";
    	for(String h: hops) {
    		result += (h);
    	}
    	return result;
    }
}
