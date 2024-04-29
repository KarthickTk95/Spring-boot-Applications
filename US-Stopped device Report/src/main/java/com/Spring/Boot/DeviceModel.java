package com.Spring.Boot;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="deviceStatus")
public class DeviceModel{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true)
    private  int id;
	
	private  int deviceId;
	private  String deviceName; 
	private  String deviceMacId; 
	private  int deviceSensorId;
	private   int areaId;
	private  String areaName; 
	private  int floorId;
	private  String floorName; 
	private  String buildingId;
	private  String buildingName; 
	private  String sensorName; 
	private  double sensorValue;
	private  double percentage ; 
	private  double batteryValue; 
	private  double rssiValue; 
	private  double washCountValue; 
	private  String status; 
	private  String iconPath; 
	private  String alert; 
	private  Date deviceTimestamp; 
	private  Date batteryTimeStamp; 
	private  Date rssiTimeStamp; 
	private  Date washCountTimeStamp; 
	private  int lastPeopleCount;
	private  String version; 
	private  int sNo;
	private  int refillCount;
	private int Total;
	private int active;
	private int Stopped;
	private String ClientName;
	private String TotalZanWave;
	private String StoppedZanWave;
	private String NotYetReporting;
	private String FeedBackDevices;
	private String Gateway;
	private String LastReportingTime;
	private String ErrorCode;
	private String ErrorDescription;
	private String LastErrorMsgTime;
	private String Notes;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceMacId() {
		return deviceMacId;
	}
	public void setDeviceMacId(String deviceMacId) {
		this.deviceMacId = deviceMacId;
	}
	public int getDeviceSensorId() {
		return deviceSensorId;
	}
	public void setDeviceSensorId(int deviceSensorId) {
		this.deviceSensorId = deviceSensorId;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public int getFloorId() {
		return floorId;
	}
	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}
	public String getFloorName() {
		return floorName;
	}
	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getSensorName() {
		return sensorName;
	}
	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
	public double getSensorValue() {
		return sensorValue;
	}
	public void setSensorValue(double sensorValue) {
		this.sensorValue = sensorValue;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public double getBatteryValue() {
		return batteryValue;
	}
	public void setBatteryValue(double batteryValue) {
		this.batteryValue = batteryValue;
	}
	public double getRssiValue() {
		return rssiValue;
	}
	public void setRssiValue(double rssiValue) {
		this.rssiValue = rssiValue;
	}
	public double getWashCountValue() {
		return washCountValue;
	}
	public void setWashCountValue(double washCountValue) {
		this.washCountValue = washCountValue;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	public Date getDeviceTimestamp() {
		return deviceTimestamp;
	}
	public void setDeviceTimestamp(Date deviceTimestamp) {
		this.deviceTimestamp = deviceTimestamp;
	}
	public Date getBatteryTimeStamp() {
		return batteryTimeStamp;
	}
	public void setBatteryTimeStamp(Date batteryTimeStamp) {
		this.batteryTimeStamp = batteryTimeStamp;
	}
	public Date getRssiTimeStamp() {
		return rssiTimeStamp;
	}
	public void setRssiTimeStamp(Date rssiTimeStamp) {
		this.rssiTimeStamp = rssiTimeStamp;
	}
	public Date getWashCountTimeStamp() {
		return washCountTimeStamp;
	}
	public void setWashCountTimeStamp(Date washCountTimeStamp) {
		this.washCountTimeStamp = washCountTimeStamp;
	}
	public int getLastPeopleCount() {
		return lastPeopleCount;
	}
	public void setLastPeopleCount(int lastPeopleCount) {
		this.lastPeopleCount = lastPeopleCount;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getsNo() {
		return sNo;
	}
	public void setsNo(int sNo) {
		this.sNo = sNo;
	}
	public int getRefillCount() {
		return refillCount;
	}
	public void setRefillCount(int refillCount) {
		this.refillCount = refillCount;
	}
	public int getTotal() {
		return Total;
	}
	public void setTotal(int total) {
		Total = total;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public int getStopped() {
		return Stopped;
	}
	public void setStopped(int stopped) {
		Stopped = stopped;
	}
	public String getClientName() {
		return ClientName;
	}
	public void setClientName(String clientName) {
		ClientName = clientName;
	}
	public String getTotalZanWave() {
		return TotalZanWave;
	}
	public void setTotalZanWave(String totalZanWave) {
		TotalZanWave = totalZanWave;
	}
	public String getStoppedZanWave() {
		return StoppedZanWave;
	}
	public void setStoppedZanWave(String stoppedZanWave) {
		StoppedZanWave = stoppedZanWave;
	}
	public String getNotYetReporting() {
		return NotYetReporting;
	}
	public void setNotYetReporting(String notYetReporting) {
		NotYetReporting = notYetReporting;
	}
	public String getFeedBackDevices() {
		return FeedBackDevices;
	}
	public void setFeedBackDevices(String feedBackDevices) {
		FeedBackDevices = feedBackDevices;
	}
	public String getGateway() {
		return Gateway;
	}
	public void setGateway(String gateway) {
		Gateway = gateway;
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
	public String getNotes() {
		return Notes;
	}
	public void setNotes(String notes) {
		Notes = notes;
	}
	public DeviceModel(int id, int deviceId, String deviceName, String deviceMacId, int deviceSensorId, int areaId,
			String areaName, int floorId, String floorName, String buildingId, String buildingName, String sensorName,
			double sensorValue, double percentage, double batteryValue, double rssiValue, double washCountValue,
			String status, String iconPath, String alert, Date deviceTimestamp, Date batteryTimeStamp,
			Date rssiTimeStamp, Date washCountTimeStamp, int lastPeopleCount, String version, int sNo, int refillCount,
			int total, int active, int stopped, String clientName, String totalZanWave, String stoppedZanWave,
			String notYetReporting, String feedBackDevices, String gateway, String lastReportingTime, String errorCode,
			String errorDescription, String lastErrorMsgTime, String notes) {
		super();
		this.id = id;
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.deviceMacId = deviceMacId;
		this.deviceSensorId = deviceSensorId;
		this.areaId = areaId;
		this.areaName = areaName;
		this.floorId = floorId;
		this.floorName = floorName;
		this.buildingId = buildingId;
		this.buildingName = buildingName;
		this.sensorName = sensorName;
		this.sensorValue = sensorValue;
		this.percentage = percentage;
		this.batteryValue = batteryValue;
		this.rssiValue = rssiValue;
		this.washCountValue = washCountValue;
		this.status = status;
		this.iconPath = iconPath;
		this.alert = alert;
		this.deviceTimestamp = deviceTimestamp;
		this.batteryTimeStamp = batteryTimeStamp;
		this.rssiTimeStamp = rssiTimeStamp;
		this.washCountTimeStamp = washCountTimeStamp;
		this.lastPeopleCount = lastPeopleCount;
		this.version = version;
		this.sNo = sNo;
		this.refillCount = refillCount;
		Total = total;
		this.active = active;
		Stopped = stopped;
		ClientName = clientName;
		TotalZanWave = totalZanWave;
		StoppedZanWave = stoppedZanWave;
		NotYetReporting = notYetReporting;
		FeedBackDevices = feedBackDevices;
		Gateway = gateway;
		LastReportingTime = lastReportingTime;
		ErrorCode = errorCode;
		ErrorDescription = errorDescription;
		LastErrorMsgTime = lastErrorMsgTime;
		Notes = notes;
	}
	public DeviceModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	

	
	
}
