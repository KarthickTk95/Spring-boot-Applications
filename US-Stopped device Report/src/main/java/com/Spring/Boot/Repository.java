package com.Spring.Boot;

import java.util.List;

public interface Repository {
	
	public List<DeviceModel> getAllDeviceDetails(String instance);

	public List<DeviceModel> getAllDeviceSummary(String instance);
	
	public List<DeviceModel> getAllDeviceDetailsEU(String instance);
	public List<DeviceModel> getAllDeviceSummaryEU(String instance);
	
}
