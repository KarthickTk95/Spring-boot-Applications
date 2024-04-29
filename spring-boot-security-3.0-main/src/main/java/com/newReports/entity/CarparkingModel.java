package com.newReports.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarparkingModel {
	
	private String buildingName;
	private String LastReportingTime;
	private String LastDeductionTime;
	private String areaId;
	private String entrenceNumber;
	private String entrenceType;
	private String count;
	private String clientTime;
	
	private String dDate;
	private String dTime;
	private String carCount;
	
	public String getdDate() {
		return dDate;
	}
	public void setdDate(String dDate) {
		this.dDate = dDate;
	}
	public String getdTime() {
		return dTime;
	}
	public void setdTime(String dTime) {
		this.dTime = dTime;
	}
	public String getCarCount() {
		return carCount;
	}
	public void setCarCount(String carCount) {
		this.carCount = carCount;
	}
	
	
	
	
	
	
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getLastReportingTime() {
		return LastReportingTime;
	}
	public void setLastReportingTime(String lastReportingTime) {
		LastReportingTime = lastReportingTime;
	}
	public String getLastDeductionTime() {
		return LastDeductionTime;
	}
	public void setLastDeductionTime(String lastDeductionTime) {
		LastDeductionTime = lastDeductionTime;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getEntrenceNumber() {
		return entrenceNumber;
	}
	public void setEntrenceNumber(String entrenceNumber) {
		this.entrenceNumber = entrenceNumber;
	}
	public String getEntrenceType() {
		return entrenceType;
	}
	public void setEntrenceType(String entrenceType) {
		this.entrenceType = entrenceType;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getClientTime() {
		return clientTime;
	}
	public void setClientTime(String clientTime) {
		this.clientTime = clientTime;
	}

	
	
	
}
