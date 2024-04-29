package com.newReports.entity;

public class SummaryReport {

	private String clientname;

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	private String Total;
	private String Active;
	private String Stopped;

	private String NotYetReporting;
	private String BLEGateWay;
	private String BLEDevices;
	private String IntrafficDevices;
	private String BatteryLow;

	private String Feedback;
	private String OccupancyDisplay;
	private String PreStopped;

	public String getPreStopped() {
		return PreStopped;
	}

	public void setPreStopped(String preStopped) {
		PreStopped = preStopped;
	}

	public String getOccupancyDisplay() {
		return OccupancyDisplay;
	}

	public void setOccupancyDisplay(String occupancyDisplay) {
		OccupancyDisplay = occupancyDisplay;
	}

	public String getTotal() {
		return Total;
	}

	public void setTotal(String total) {
		Total = total;
	}

	public String getActive() {
		return Active;
	}

	public void setActive(String active) {
		Active = active;
	}

	public String getStopped() {
		return Stopped;
	}

	public void setStopped(String stopped) {
		Stopped = stopped;
	}

	public String getNotYetReporting() {
		return NotYetReporting;
	}

	public void setNotYetReporting(String notYetReporting) {
		NotYetReporting = notYetReporting;
	}

	public String getBLEGateWay() {
		return BLEGateWay;
	}

	public void setBLEGateWay(String bLEGateWay) {
		BLEGateWay = bLEGateWay;
	}

	public String getBLEDevices() {
		return BLEDevices;
	}

	public void setBLEDevices(String bLEDevices) {
		BLEDevices = bLEDevices;
	}

	public String getIntrafficDevices() {
		return IntrafficDevices;
	}

	public void setIntrafficDevices(String intrafficDevices) {
		IntrafficDevices = intrafficDevices;
	}

	public String getBatteryLow() {
		return BatteryLow;
	}

	public void setBatteryLow(String batteryLow) {
		BatteryLow = batteryLow;
	}

	public String getFeedback() {
		return Feedback;
	}

	public void setFeedback(String feedback) {
		Feedback = feedback;
	}

}
