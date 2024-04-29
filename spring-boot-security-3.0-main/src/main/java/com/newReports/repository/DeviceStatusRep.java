package com.newReports.repository;


import com.newReports.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceStatusRep {



    List<DeviceStatus> findAll();


    List<DeviceStatus> findAllDeviceName();

    List<DeviceStatus> getDevices(String lowerCase, String startDate, String endDate);

    List<AddClient> getClientsList();

    List<PacketlossReport> getPacketloss(String lowerCase, String startDate, String endDate);

    List<SummaryReport> getDevicestotaList(String lowerCase);

    List<DeviceStatus> getDevicesRawdata(String lowerCase, String startDate, String endDate);

    List<ClientModel> getClients();

    List<DeviceStatus> getAllClientDevicesList(String lowerCase);

    List<PacketlossReport> getDevicesPacketlossRawdata(String clientname, String startDate, String endDate);
}
