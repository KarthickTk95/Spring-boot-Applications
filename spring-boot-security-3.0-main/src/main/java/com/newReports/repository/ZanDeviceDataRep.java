package com.newReports.repository;

import com.newReports.entity.ZanDeviceData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZanDeviceDataRep {


    List<ZanDeviceData> getZanDeviceData(String deviceMacId, String yearmonth, String topic);

    List<ZanDeviceData> getZanDeviceDataTopic(String deviceMacId, String yearmonth);
}
