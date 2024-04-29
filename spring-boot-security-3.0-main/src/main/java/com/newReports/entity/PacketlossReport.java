package com.newReports.entity;

public class PacketlossReport {
	
	private String deviceMacId; 
	private String deviceName; 
	private String deviceSensorId;
	private String buildingName; 
	private String floorName; 
	private String areaName;
	private String sensorName;
	private String deviceTimeStamp;
	private String sensorValue;
	private String batteryValue; 
	private String rssiValue;
	private String ErrorCode;
	private String exp;
	private int received;
	private String lossP;
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
	public String getSensorName() {
		return sensorName;
	}
	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
	public String getDeviceTimeStamp() {
		return deviceTimeStamp;
	}
	public void setDeviceTimeStamp(String deviceTimeStamp) {
		this.deviceTimeStamp = deviceTimeStamp;
	}
	public String getSensorValue() {
		return sensorValue;
	}
	public void setSensorValue(String sensorValue) {
		this.sensorValue = sensorValue;
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
	public String getErrorCode() {
		return ErrorCode;
	}
	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public int getReceived() {
		return received;
	}
	public void setReceived(int received) {
		this.received = received;
	}
	public String getLossP() {
		return lossP;
	}
	public void setLossP(String lossP) {
		this.lossP = lossP;
	}

	

}
