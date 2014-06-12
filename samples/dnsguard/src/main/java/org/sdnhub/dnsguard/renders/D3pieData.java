package org.sdnhub.dnsguard.renders;

import java.util.List;

public class D3pieData {
 
	public String sortOrder = "value-desc";
	
	public D3pieData(){
		
	}
	
	@SuppressWarnings("rawtypes")
	public List content;
	
	@SuppressWarnings("rawtypes")
	D3pieData(List content){
		this.content = content;
	}
	
	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	@SuppressWarnings("rawtypes")
	public List getContent() {
		return content;
	}

	@SuppressWarnings("rawtypes")
	public void setContent(List content) {
		this.content = content;
	}
	
}
