package com.newReports.entity;

public class AddClient {

	public int id;
	public String clientName;
	
	
	public AddClient(int id, String clientName) {
		super();
		this.id = id;
		this.clientName = clientName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	
	
}
