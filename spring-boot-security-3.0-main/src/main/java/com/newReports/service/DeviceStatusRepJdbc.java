package com.newReports.service;

import com.newReports.entity.*;
import com.newReports.repository.DeviceStatusRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

@Repository
public class DeviceStatusRepJdbc implements DeviceStatusRep {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<DeviceStatus> findAll() {
        try {
            System.out.println("Before executing query");

            return jdbcTemplate.query(
                    "select * from apple.deviceStatus",
                    new BeanPropertyRowMapper<>(DeviceStatus.class)
            );


        } catch (Exception e) {
            // Handle or log the exception
            e.printStackTrace();
            return Collections.emptyList(); // or throw a custom exception
        }
    }

    @Override
    public List<DeviceStatus> findAllDeviceName() {
        try {
            System.out.println("Before executing query");

            return jdbcTemplate.query(
                    "select * from apple.deviceStatus",
                    new BeanPropertyRowMapper<>(DeviceStatus.class)
            );


        } catch (Exception e) {
            // Handle or log the exception
            e.printStackTrace();
            return Collections.emptyList(); // or throw a custom exception
        }
    }


    @Override
    public List<DeviceStatus> getDevices(String clientname, String startDate, String endDate) {

        try {
            return jdbcTemplate.query(
                    "select * from(select ds.deviceMacId,ds.deviceName,ds.deviceSensorId ,ds.buildingName,ds.floorName,ds.areaName,convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone)  as lastReportingTime,er.errorCode,er.errorDescription,convert_tz(er.deviceTimeStamp,'+00:00',d.TimeZone) as lastErrorMsgTime,ds.sensorValue,ds.batteryValue,ds.rssiValue\r\n"
                            + "from " + clientname + ".deviceStatus ds left join " + clientname
                            + ".errorMessageInfo er on er.deviceId=ds.deviceMacId join " + clientname
                            + " .device d on d.deviceId = ds.deviceMacId and d.deviceId = er.deviceId where ds.sensorName not in('QR-Janitor','QR-Feedback') and ds.deviceTimestamp not between '"
                            + startDate + "' and '" + endDate
                            + "'  order by er.deviceTimeStamp desc)a group by deviceMacId\r\n"
                            + "order by buildingName,floorName,areaName ;",
                    new BeanPropertyRowMapper<>(DeviceStatus.class));
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    @Override
    public List<AddClient> getClientsList() {
        List<String> clientlist1 = null;
        try {
            clientlist1 = jdbcTemplate.queryForList("SELECT clientName FROM MasterDB.appMonitoringConfig order by clientName",
                    // "SELECT clientName FROM add_client WHERE clientName IN ( 'google',
                    // 'applebgm')",
                    String.class);
        } catch (DataAccessException e) {
            // handle the exception
            e.printStackTrace();
        }
        final List<AddClient> addClients = new ArrayList<>();
        // clientlist1.stream().filter(c -> c != null).peek(c -> addClients.add(new
        // AddClient(0, c)));

        if (clientlist1 != null) {
            clientlist1.forEach(new Consumer<String>() {
                @Override
                public void accept(String b) {
                    addClients.add(new AddClient(0, b));
                }
            });
        }
        return addClients;
    }


    @Override
    public List<PacketlossReport> getPacketloss(String clientname, String startDate, String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
        YearMonth startYearMonth = YearMonth.from(startDateTime);

        String dynamicTableName = "zanDeviceData.sensorData_" + startYearMonth.getYear() + "_"
                + String.format("%02d", startYearMonth.getMonthValue());

        System.out.println(dynamicTableName);

        String query = "SELECT *, " + "ROUND(100 - (received/exp) * 100) AS lossP " + "FROM ( "
                + "SELECT ds.deviceMacId, ds.deviceSensorId, ds.deviceName, ds.buildingName, ds.floorName, ds.areaName, "
                + "ds.sensorName, CONVERT_TZ(ds.deviceTimeStamp, '+00:00', d.TimeZone) AS deviceTimestamp, ds.sensorValue, "
                + "ds.batteryValue, ds.rssiValue, " + "(SELECT errorCode FROM " + clientname
                + ".errorMessageInfo em WHERE em.deviceId = ds.deviceMacId " + "AND em.id = (SELECT MAX(id) FROM "
                + clientname + ".errorMessageInfo WHERE deviceId = ds.deviceMacId)) AS ErrorCode, " + "CASE "
                + "WHEN sensorName IN ('BLEGateWay', 'OccupancyDisplay') THEN TIMESTAMPDIFF(hour, '" + startDate
                + "', '" + endDate + "') * 1 "
                + "WHEN sensorName IN ('WetnessDetector', 'AirQuality') THEN TIMESTAMPDIFF(hour, '" + startDate + "', '"
                + endDate + "') * 12 " + "ELSE TIMESTAMPDIFF(hour, '" + startDate + "', '" + endDate + "') * 2 "
                + "END AS exp, " + "CASE " + "WHEN sensorName = 'BLEGateWay' THEN (SELECT COUNT(*) FROM " + clientname
                + ".gateway_status WHERE gatewayId = ds.deviceMacId "
                + "AND CONVERT_TZ(updatedTime, '+00:00', d.TimeZone) BETWEEN '" + startDate + "' AND '" + endDate
                + "') " + "WHEN sensorName = 'OccupancyDisplay' THEN (SELECT COUNT(*) FROM " + clientname
                + ".aliveStatus WHERE deviceMacId = ds.deviceMacId "
                + "AND CONVERT_TZ(serverTimeStamp, '+00:00', d.TimeZone) BETWEEN '" + startDate + "' AND '" + endDate
                + "') " + "WHEN sensorName = 'WetnessDetector' THEN (SELECT COUNT(*) FROM " + clientname
                + ".wetnessData WHERE deviceMacId = ds.deviceMacId "
                + "AND CONVERT_TZ(updatedTime, '+00:00', d.TimeZone) BETWEEN '" + startDate + "' AND '" + endDate
                + "') " + "WHEN sensorName = 'AirQuality' THEN (SELECT COUNT(*) FROM " + clientname
                + ".sensor_value WHERE deviceSensorId = ds.deviceSensorId "
                + "AND CONVERT_TZ(deviceTimeStamp, '+00:00', d.TimeZone) BETWEEN '" + startDate + "' AND '" + endDate
                + "') " + "WHEN ds.deviceName LIKE '%CassiaGateway%' THEN (SELECT COUNT(*) FROM " + dynamicTableName
                + " AS sd "
                + "WHERE deviceMacId = ds.deviceMacId AND CONVERT_TZ(publishedTime, '+00:00', d.TimeZone) BETWEEN '"
                + startDate + "' AND '" + endDate + "') "
                + "WHEN ds.sensorName = 'BeaconScanner' THEN (SELECT COUNT(*) FROM " + clientname
                + ".aliveStatus WHERE deviceMacId = ds.deviceMacId "
                + "AND CONVERT_TZ(serverTimeStamp, '+00:00', d.TimeZone) BETWEEN '" + startDate + "' AND '" + endDate
                + "') " + "ELSE (SELECT COUNT(sensorData) FROM " + clientname
                + ".sensor_value WHERE deviceSensorId = ds.deviceSensorId "
                + "AND CONVERT_TZ(deviceTimeStamp, '+00:00', d.TimeZone) BETWEEN '" + startDate + "' AND '" + endDate
                + "') " + "END AS received " + "FROM " + clientname + ".deviceStatus ds " + "JOIN " + clientname
                + ".device d ON d.deviceId = ds.deviceMacId "
                + "WHERE ds.sensorName NOT IN ('QR-Janitor', 'QR-Feedback') "
                + "ORDER BY sensorName, buildingName, floorName, areaName " + ") a " + "GROUP BY deviceMacId";
        System.out.println(query);

        try {
            return jdbcTemplate.query(query, new Object[] {}, new BeanPropertyRowMapper<>(PacketlossReport.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
        public List<SummaryReport> getDevicestotaList(String clientname) {
            try {
                return jdbcTemplate.query("select '" + clientname + "' AS clientname, " + "COUNT(*) AS Total,"
                                + "sum(if(convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone)>now()-interval 24 hour,1,0)) Active,sum(if(convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone)<=now()-interval 24 hour,1,0)) Stopped,sum(if(ds.deviceTimestamp is null,1,0)) NotYetReporting,sum(if(convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone)<=now()-interval 24 hour and sensorName='BLEGateWay',1,0)) BLEGateWay,sum(if(convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone)<=now()-interval 24 hour and sensorName not in ('PeopleCount','ZanInTraffic','ZanOpenAreaTraffic','TOF','BLEGateWay'),1,0)) BleDevices,sum(if(convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone)<=now()-interval 24 hour and sensorName  in ('PeopleCount','ZanInTraffic','ZanOpenAreaTraffic','TOF'),1,0)) as IntrafficDevices,sum(if(convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone)<=now()-interval 24 hour and sensorName in ('GateWay'),1,0)) Feedback,sum(if(convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone)<=now()-interval 24 hour and sensorName like '%Occupancy%',1,0)) OccupancyDisplay,sum(if(batteryValue<=70 and batteryValue != -3,1,0)) BatteryLow ,sum(if(convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone)<now()-interval 48 hour,1,0)) PreStopped from  "
                                + clientname + ". deviceStatus ds join " + clientname
                                + " .device d on d.deviceId = ds.deviceMacId  where  sensorName not in ('QR-Janitor','QR-Feedback','BeaconScanner');",
                        new BeanPropertyRowMapper<>(SummaryReport.class));
            } catch (Exception e) {
                e.printStackTrace();
                return Collections.emptyList();
            }
        }


        @Override
    public List<DeviceStatus> getDevicesRawdata(String clientname, String startDate, String endDate) {

        try {
            String query = "select * from (SELECT ds.deviceMacId, ds.deviceName, ds.deviceSensorId, ds.buildingName, ds.floorName, ds.areaName, ds.deviceTimestamp AS lastReportingTime, er.errorCode, er.errorDescription, er.deviceTimeStamp AS lastErrorMsgTime, ds.sensorValue, ds.batteryValue, ds.rssiValue, "
                    + "CASE "
                    + "WHEN sensorValue = -3 AND rssiValue = -3 AND batteryValue = -3 THEN 'Not yet reporting' "
                    + "WHEN errorCode = 2 THEN 'Sensor is faild' " + "WHEN batteryValue <= 70 THEN 'Battery Issue' "
                    + "WHEN rssiValue < -80 THEN 'Range issue' "
                    + "WHEN errorCode = 4 AND sensorName = 'peoplecount' THEN 'Door Not Clossed properly' "
                    + "WHEN sensorValue = 255 THEN 'Reporting 255 reading' "
                    + "WHEN bootReasonCode = 6 THEN 'Not yet reporting' " + "ELSE '-' " + "END AS Notes " + "FROM "
                    + clientname + ".deviceStatus ds " + "LEFT JOIN " + clientname
                    + ".errorMessageInfo er ON er.deviceId = ds.deviceMacId "
                    + "WHERE ds.sensorName NOT IN ('QR-Janitor', 'QR-Feedback') AND ds.deviceTimestamp between '"
                    + startDate + "' and '" + endDate + "' " + "ORDER BY ds.deviceTimeStamp DESC)a "
                    + "GROUP BY deviceMacId " + "ORDER BY buildingName, floorName, areaName";

            return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(DeviceStatus.class));
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }




    public List<PacketlossReport> getDevicesPacketlossRawdata(String clientname, String startDate, String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
        YearMonth startYearMonth = YearMonth.from(startDateTime);

        String dynamicTableName = "zanDeviceData.sensorData_" + startYearMonth.getYear() + "_"
                + String.format("%02d", startYearMonth.getMonthValue());

        System.out.println(dynamicTableName);

        String query = "SELECT *, " + "ROUND(100 - (received/exp) * 100) AS lossP " + "FROM ( "
                + "SELECT ds.deviceMacId, ds.deviceSensorId, ds.deviceName, ds.buildingName, ds.floorName, ds.areaName, "
                + "ds.sensorName, CONVERT_TZ(ds.deviceTimeStamp, '+00:00', d.TimeZone) AS deviceTimestamp, ds.sensorValue, "
                + "ds.batteryValue, ds.rssiValue, " + "(SELECT errorCode FROM " + clientname
                + ".errorMessageInfo em WHERE em.deviceId = ds.deviceMacId " + "AND em.id = (SELECT MAX(id) FROM "
                + clientname + ".errorMessageInfo WHERE deviceId = ds.deviceMacId)) AS ErrorCode, " + "CASE "
                + "WHEN sensorName IN ('BLEGateWay', 'OccupancyDisplay') THEN TIMESTAMPDIFF(hour, '" + startDate
                + "', '" + endDate + "') * 1 "
                + "WHEN sensorName IN ('WetnessDetector', 'AirQuality') THEN TIMESTAMPDIFF(hour, '" + startDate + "', '"
                + endDate + "') * 12 " + "ELSE TIMESTAMPDIFF(hour, '" + startDate + "', '" + endDate + "') * 2 "
                + "END AS exp, " + "CASE " + "WHEN sensorName = 'BLEGateWay' THEN (SELECT COUNT(*) FROM " + clientname
                + ".gateway_status WHERE gatewayId = ds.deviceMacId "
                + "AND CONVERT_TZ(updatedTime, '+00:00', d.TimeZone) BETWEEN '" + startDate + "' AND '" + endDate
                + "') " + "WHEN sensorName = 'OccupancyDisplay' THEN (SELECT COUNT(*) FROM " + clientname
                + ".aliveStatus WHERE deviceMacId = ds.deviceMacId "
                + "AND CONVERT_TZ(serverTimeStamp, '+00:00', d.TimeZone) BETWEEN '" + startDate + "' AND '" + endDate
                + "') " + "WHEN sensorName = 'WetnessDetector' THEN (SELECT COUNT(*) FROM " + clientname
                + ".wetnessData WHERE deviceMacId = ds.deviceMacId "
                + "AND CONVERT_TZ(updatedTime, '+00:00', d.TimeZone) BETWEEN '" + startDate + "' AND '" + endDate
                + "') " + "WHEN sensorName = 'AirQuality' THEN (SELECT COUNT(*) FROM " + clientname
                + ".sensor_value WHERE deviceSensorId = ds.deviceSensorId "
                + "AND CONVERT_TZ(deviceTimeStamp, '+00:00', d.TimeZone) BETWEEN '" + startDate + "' AND '" + endDate
                + "') " + "WHEN ds.deviceName LIKE '%CassiaGateway%' THEN (SELECT COUNT(*) FROM " + dynamicTableName
                + " AS sd "
                + "WHERE deviceMacId = ds.deviceMacId AND CONVERT_TZ(publishedTime, '+00:00', d.TimeZone) BETWEEN '"
                + startDate + "' AND '" + endDate + "') "
                + "WHEN ds.sensorName = 'BeaconScanner' THEN (SELECT COUNT(*) FROM " + clientname
                + ".aliveStatus WHERE deviceMacId = ds.deviceMacId "
                + "AND CONVERT_TZ(serverTimeStamp, '+00:00', d.TimeZone) BETWEEN '" + startDate + "' AND '" + endDate
                + "') " + "ELSE (SELECT COUNT(sensorData) FROM " + clientname
                + ".sensor_value WHERE deviceSensorId = ds.deviceSensorId "
                + "AND CONVERT_TZ(deviceTimeStamp, '+00:00', d.TimeZone) BETWEEN '" + startDate + "' AND '" + endDate
                + "') " + "END AS received " + "FROM " + clientname + ".deviceStatus ds " + "JOIN " + clientname
                + ".device d ON d.deviceId = ds.deviceMacId "
                + "WHERE ds.sensorName NOT IN ('QR-Janitor', 'QR-Feedback') "
                + "ORDER BY sensorName, buildingName, floorName, areaName " + ") a " + "GROUP BY deviceMacId";

        try {
            return jdbcTemplate.query(query, new Object[] {}, new BeanPropertyRowMapper<>(PacketlossReport.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ClientModel> getClients() {
        List<String> clientList = jdbcTemplate.queryForList(
                "SELECT clientName FROM MasterDB.appMonitoringConfig  order by clientName",
                String.class
        );
        List<ClientModel> clientModels = new ArrayList<>();
        for (String a : clientList) {
            clientModels.add(new ClientModel(a));
            System.out.println(clientList);
        }
        System.out.println(clientList);
        return clientModels;
    }


    public List<DeviceStatus> getAllClientDevicesList(String clientname) {
        try {
            return jdbcTemplate.query("SELECT '" + clientname
                            + "' AS clientname, ds.deviceMacId, ds.deviceName, ds.deviceSensorId, ds.buildingName, ds.floorName, ds.areaName,convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone)  AS lastReportingTime, er.errorCode, er.errorDescription, convert_tz(er.deviceTimeStamp,'+00:00',d.TimeZone) as lastErrorMsgTime, ds.sensorValue, ds.batteryValue, ds.rssiValue FROM "
                            + clientname + ".deviceStatus ds LEFT JOIN " + clientname
                            + ".errorMessageInfo er ON ds.deviceMacId = er.deviceId and er.id=(select max(id) from "
                            + clientname + ".errorMessageInfo where deviceId=ds.deviceMacId) join " + clientname
                            + " .device d on d.deviceId = ds.deviceMacId and d.deviceId = er.deviceId WHERE ds.sensorName not in ('QR-Janitor','QR-Feedback','BeaconScanner','GateWay')and ds.sensorValue='255' ORDER BY buildingName, floorName, areaName;",
                    new BeanPropertyRowMapper<>(DeviceStatus.class));
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}


