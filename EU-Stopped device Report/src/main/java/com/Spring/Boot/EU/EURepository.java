package com.Spring.Boot.EU;

import java.util.List;

public interface EURepository {
	
	public List<DeviceModel> getAllDeviceDetails(String instance);

	public List<DeviceModel> getAllDeviceSummary(String instance);
	
	public List<DeviceModel> getAllDeviceDetailsEU(String instance);
	public List<DeviceModel> getAllDeviceSummaryEU(String instance);
	
}
