package com.example.IpSensor;

import java.util.ArrayList;

public class Ping {

	//
	private String url;
	
	// 
	private String hops;
	
	public Ping() {
		
	}
	
	public Ping(String url, String hops ) {
		this.url = url;
		this.hops = hops;
	}
	
	public String getHops() {
		return hops;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setHops(String hops) {
		this.hops = hops;
	}
}
