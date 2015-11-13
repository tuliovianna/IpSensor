package com.example.IpSensor;

public class Scan {
	
	private String url;
	
	private String ports;
	
	public Scan() {
		
	}
	
	public Scan(String url, String ports) {
		this.url = url;
		this.ports = ports;
	}
	
	public String getPorts() {
		return ports;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setPorts(String ports) {
		this.ports = ports;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

}
