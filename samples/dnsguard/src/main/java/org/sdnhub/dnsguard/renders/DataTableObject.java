package org.sdnhub.dnsguard.renders;

import java.util.List;

public class DataTableObject {

	int iTotalRecords;

	int iTotalDisplayRecords;

	String sEcho;

	String sColumns;
	
	int iDraw;

	public int getiDraw() {
		return iDraw;
	}

	public void setiDraw(int iDraw) {
		this.iDraw = iDraw;
	}

	@SuppressWarnings("rawtypes")
	List aaData;

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public String getsColumns() {
		return sColumns;
	}

	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}

	@SuppressWarnings("rawtypes")
	public List getAaData() {
		return aaData;
	}

	@SuppressWarnings("rawtypes")
	public void setAaData(List aaData) {
		this.aaData = aaData;
	}

}