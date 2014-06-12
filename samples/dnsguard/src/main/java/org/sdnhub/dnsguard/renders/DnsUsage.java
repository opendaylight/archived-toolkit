package org.sdnhub.dnsguard.renders;

public class DnsUsage {
	
	protected String label, color;
	protected int value;
	
	private static final String[] color_table = { "#4e7530","#f30000","#0600f3", "#00b109", "#ff7e00" };
	
	public DnsUsage(String label, int value, int order){
		
		this.label = label;
		this.value = value;
		this.color = color_table[order]; 
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
}
