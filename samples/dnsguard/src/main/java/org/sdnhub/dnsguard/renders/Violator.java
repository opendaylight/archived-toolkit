package org.sdnhub.dnsguard.renders;

public class Violator {
	
	private String ip;
	private String timestamp; 
	
	public Violator(String ip, String timestamp){
		this.ip = ip;
		this.timestamp = timestamp;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
