package com.newReports.repository;

import com.newReports.entity.DeviceStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummarySheetRep {


    List<DeviceStatus> getTotalDevices(String clientname);

    List<DeviceStatus> getActiveDevices(String clientname);

    List<DeviceStatus> getStoppedDevices(String clientname);

    List<DeviceStatus> getPreStopped(String clientname);

    List<DeviceStatus> getNotyetDevices(String clientname);

    List<DeviceStatus> getBlegatewayDevices(String clientname);

    List<DeviceStatus> getBleDevices(String clientname);

    List<DeviceStatus> getIntrafficDevices(String clientname);

    List<DeviceStatus> getfeedbackDevices(String clientname);

    List<DeviceStatus> getoccupancyDisplayDevices(String clientname);

    List<DeviceStatus> getBatterylowDevices(String clientname);
}
