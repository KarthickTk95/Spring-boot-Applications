package com.newReports.entity;

public class DeviceStatus {

	
	private String clientname;
	private String deviceMacId; 
	private String deviceName; 
	private String deviceSensorId;
	private String buildingName; 
	private String floorName; 
	private String areaName;
	private String LastReportingTime;
	private String ErrorCode;
	private String ErrorDescription;
	private String LastErrorMsgTime; 
	private String sensorValue;
	private String Notes;
	private String batteryValue; 
	private String rssiValue;


	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public String getDeviceMacId() {
		return deviceMacId;
	}

	public void setDeviceMacId(String deviceMacId) {
		this.deviceMacId = deviceMacId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceSensorId() {
		return deviceSensorId;
	}

	public void setDeviceSensorId(String deviceSensorId) {
		this.deviceSensorId = deviceSensorId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getLastReportingTime() {
		return LastReportingTime;
	}

	public void setLastReportingTime(String lastReportingTime) {
		LastReportingTime = lastReportingTime;
	}

	public String getErrorCode() {
		return ErrorCode;
	}

	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}

	public String getErrorDescription() {
		return ErrorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		ErrorDescription = errorDescription;
	}

	public String getLastErrorMsgTime() {
		return LastErrorMsgTime;
	}

	public void setLastErrorMsgTime(String lastErrorMsgTime) {
		LastErrorMsgTime = lastErrorMsgTime;
	}

	public String getSensorValue() {
		return sensorValue;
	}

	public void setSensorValue(String sensorValue) {
		this.sensorValue = sensorValue;
	}

	public String getNotes() {
		return Notes;
	}

	public void setNotes(String notes) {
		Notes = notes;
	}

	public String getBatteryValue() {
		return batteryValue;
	}

	public void setBatteryValue(String batteryValue) {
		this.batteryValue = batteryValue;
	}

	public String getRssiValue() {
		return rssiValue;
	}

	public void setRssiValue(String rssiValue) {
		this.rssiValue = rssiValue;
	}

	public DeviceStatus() {
	}

	public DeviceStatus(String clientname, String deviceMacId, String deviceName, String deviceSensorId, String buildingName, String floorName, String areaName, String lastReportingTime, String errorCode, String errorDescription, String lastErrorMsgTime, String sensorValue, String notes, String batteryValue, String rssiValue) {
		this.clientname = clientname;
		this.deviceMacId = deviceMacId;
		this.deviceName = deviceName;
		this.deviceSensorId = deviceSensorId;
		this.buildingName = buildingName;
		this.floorName = floorName;
		this.areaName = areaName;
		LastReportingTime = lastReportingTime;
		ErrorCode = errorCode;
		ErrorDescription = errorDescription;
		LastErrorMsgTime = lastErrorMsgTime;
		this.sensorValue = sensorValue;
		Notes = notes;
		this.batteryValue = batteryValue;
		this.rssiValue = rssiValue;
	}
}
