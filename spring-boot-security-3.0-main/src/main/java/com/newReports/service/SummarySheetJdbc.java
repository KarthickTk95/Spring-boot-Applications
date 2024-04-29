package com.newReports.service;


import java.util.ArrayList;
import java.util.List;


import com.newReports.entity.ClientModel;
import com.newReports.entity.DeviceStatus;
import com.newReports.repository.SummarySheetRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SummarySheetJdbc implements SummarySheetRep {


	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	/**
	 * @return
	 */
	public List<ClientModel> getClients() {
	    List<String> clientList = jdbcTemplate.queryForList(
	        "SELECT clientName FROM appMonitoringConfig  order by clientName",
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

	/**
	 * @param clientname
	 * @return
	 */
	public List<DeviceStatus> getTotalDevices(String clientname) {
		
		System.out.println(clientname);
	    try {
	        return jdbcTemplate.query(
	            " SELECT ds.deviceMacId,ds.deviceName,ds.deviceSensorId,ds.buildingName,ds.floorName,ds.areaName,convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone) AS lastReportingTime,er.errorCode,er.errorDescription,convert_tz(er.deviceTimeStamp,'+00:00',d.TimeZone) as lastErrorMsgTime,ds.sensorValue, ds.batteryValue,ds.rssiValue FROM "+ clientname + ".deviceStatus ds LEFT JOIN  "+ clientname + ".errorMessageInfo er ON ds.deviceMacId = er.deviceId and er.id=(select max(id) from "+ clientname + ".errorMessageInfo where deviceId=ds.deviceMacId) join " + clientname +" .device d on d.deviceId = ds.deviceMacId  WHERE ds.sensorName NOT IN ('QR-Janitor','QR-Feedback') ORDER BY buildingName, floorName, areaName;",
	            new BeanPropertyRowMapper<>(DeviceStatus.class));
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}


	/**
	 * @param clientname
	 * @return
	 */
	public List<DeviceStatus> getActiveDevices(String clientname) {
	    try {
	        String query = "SELECT ds.deviceMacId,ds.deviceName,ds.deviceSensorId,ds.buildingName,ds.floorName,ds.areaName,convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone) AS lastReportingTime,er.errorCode,er.errorDescription,convert_tz(er.deviceTimeStamp,'+00:00',d.TimeZone) as lastErrorMsgTime,ds.sensorValue, ds.batteryValue,ds.rssiValue FROM "+ clientname + ".deviceStatus ds LEFT JOIN  "+ clientname + ".errorMessageInfo er ON ds.deviceMacId = er.deviceId and er.id=(select max(id) from "+ clientname + ".errorMessageInfo where deviceId=ds.deviceMacId) join " + clientname +" .device d on d.deviceId = ds.deviceMacId  WHERE ds.sensorName NOT IN ('QR-Janitor','QR-Feedback') AND (ds.deviceTimestamp > now()-interval 24 hour ) ORDER BY buildingName, floorName, areaName;";
	        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(DeviceStatus.class));
	    } catch (DataAccessException e) {
	        // handle exception here
	    }
	    return null;
	}

	
	
	/**
	 * @param clientname
	 * @return
	 */
	public List<DeviceStatus> getStoppedDevices(String clientname) {
		try {
			return jdbcTemplate.query(
					"SELECT ds.deviceMacId,ds.deviceName,ds.deviceSensorId,ds.buildingName,ds.floorName,ds.areaName,convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone) AS lastReportingTime,er.errorCode,er.errorDescription,convert_tz(er.deviceTimeStamp,'+00:00',d.TimeZone) as lastErrorMsgTime,ds.sensorValue, ds.batteryValue,ds.rssiValue FROM "+ clientname + ".deviceStatus ds LEFT JOIN  "+ clientname + ".errorMessageInfo er ON ds.deviceMacId = er.deviceId and er.id=(select max(id) from "+ clientname + ".errorMessageInfo where deviceId=ds.deviceMacId) join " + clientname +" .device d on d.deviceId = ds.deviceMacId  WHERE ds. sensorName not in ('QR-Janitor','QR-Feedback') AND (ds.deviceTimestamp <= now()-interval 24 hour ) ORDER BY buildingName, floorName, areaName;",
					new BeanPropertyRowMapper<>(DeviceStatus.class));
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * @param clientname
	 * @return
	 */
	public List<DeviceStatus> getNotyetDevices(String clientname) {
	    try {
	        return jdbcTemplate.query(
	            "SELECT ds.deviceMacId,ds.deviceName,ds.deviceSensorId,ds.buildingName,ds.floorName,ds.areaName,convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone) AS lastReportingTime,er.errorCode,er.errorDescription,convert_tz(er.deviceTimeStamp,'+00:00',d.TimeZone) as lastErrorMsgTime,ds.sensorValue, ds.batteryValue,ds.rssiValue FROM "+ clientname + ".deviceStatus ds LEFT JOIN  "+ clientname + ".errorMessageInfo er ON ds.deviceMacId = er.deviceId and er.id=(select max(id) from "+ clientname + ".errorMessageInfo where deviceId=ds.deviceMacId) join " + clientname +" .device d on d.deviceId = ds.deviceMacId  WHERE  ds. sensorName not in ('QR-Janitor','QR-Feedback')  AND (ds.deviceTimestamp is null ) ORDER BY buildingName, floorName, areaName;",
	            new BeanPropertyRowMapper<>(DeviceStatus.class)
	        );
	    } catch (DataAccessException e) {
	        // handle exception here
	        return null;
	    }
	}


	/**
	 * @param clientname
	 * @return
	 */
	public List<DeviceStatus> getBlegatewayDevices(String clientname)  {
		try {
	    return jdbcTemplate.query(
		    "SELECT ds.deviceMacId,ds.deviceName,ds.deviceSensorId,ds.buildingName,ds.floorName,ds.areaName,convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone) AS lastReportingTime,er.errorCode,er.errorDescription,convert_tz(er.deviceTimeStamp,'+00:00',d.TimeZone) as lastErrorMsgTime,ds.sensorValue, ds.batteryValue,ds.rssiValue FROM "+ clientname + ".deviceStatus ds LEFT JOIN  "+ clientname + ".errorMessageInfo er ON ds.deviceMacId = er.deviceId and er.id=(select max(id) from "+ clientname + ".errorMessageInfo where deviceId=ds.deviceMacId) join " + clientname +" .device d on d.deviceId = ds.deviceMacId  WHERE  (ds.deviceTimestamp <= now()-interval 24 hour ) and ds.sensorName='BLEGateWay' ORDER BY buildingName, floorName, areaName;",
		            new BeanPropertyRowMapper<>(DeviceStatus.class)
		            );
		}
	    catch (DataAccessException e) {
	        // handle exception here
	        return null;
	    }
		       
	}


	/**
	 * @param clientname
	 * @return
	 */
	public List<DeviceStatus> getBleDevices(String clientname) {

		try {
			return jdbcTemplate.query(
					"SELECT ds.deviceMacId,ds.deviceName,ds.deviceSensorId,ds.buildingName,ds.floorName,ds.areaName,convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone) AS lastReportingTime,er.errorCode,er.errorDescription,convert_tz(er.deviceTimeStamp,'+00:00',d.TimeZone) as lastErrorMsgTime,ds.sensorValue, ds.batteryValue,ds.rssiValue FROM "+ clientname + ".deviceStatus ds LEFT JOIN  "+ clientname + ".errorMessageInfo er ON ds.deviceMacId = er.deviceId and er.id=(select max(id) from "+ clientname + ".errorMessageInfo where deviceId=ds.deviceMacId) join " + clientname +" .device d on d.deviceId = ds.deviceMacId  WHERE   ds. sensorName not in ('QR-Janitor','QR-Feedback','BeaconScanner','GateWay','PeopleCount','ZanInTraffic','ZanOpenAreaTraffic','TOF','BLEGateWay') AND (ds.deviceTimestamp <= now()-interval 24 hour ) ORDER BY buildingName, floorName, areaName;",
					new BeanPropertyRowMapper<>(DeviceStatus.class) 

					);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * @param clientname
	 * @return
	 */
	public List<DeviceStatus> getIntrafficDevices(String clientname) {

		try {
			return jdbcTemplate.query(
					"SELECT ds.deviceMacId,ds.deviceName,ds.deviceSensorId,ds.buildingName,ds.floorName,ds.areaName,convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone) AS lastReportingTime,er.errorCode,er.errorDescription,convert_tz(er.deviceTimeStamp,'+00:00',d.TimeZone) as lastErrorMsgTime,ds.sensorValue, ds.batteryValue,ds.rssiValue FROM "+ clientname + ".deviceStatus ds LEFT JOIN  "+ clientname + ".errorMessageInfo er ON ds.deviceMacId = er.deviceId and er.id=(select max(id) from "+ clientname + ".errorMessageInfo where deviceId=ds.deviceMacId) join " + clientname +" .device d on d.deviceId = ds.deviceMacId  WHERE   ds. sensorName  in ('PeopleCount','ZanInTraffic','ZanOpenAreaTraffic','TOF') AND (ds.deviceTimestamp <= now()-interval 24 hour ) ORDER BY buildingName, floorName, areaName;",
					new BeanPropertyRowMapper<>(DeviceStatus.class) );
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * @param clientname
	 * @return
	 */
	public List<DeviceStatus> getBatterylowDevices(String clientname) {

		try {
			return jdbcTemplate.query(
					"SELECT ds.deviceMacId,ds.deviceName,ds.deviceSensorId,ds.buildingName,ds.floorName,ds.areaName,convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone) AS lastReportingTime,er.errorCode,er.errorDescription,convert_tz(er.deviceTimeStamp,'+00:00',d.TimeZone) as lastErrorMsgTime,ds.sensorValue, ds.batteryValue,ds.rssiValue FROM "+ clientname + ".deviceStatus ds LEFT JOIN  "+ clientname + ".errorMessageInfo er ON ds.deviceMacId = er.deviceId and er.id=(select max(id) from "+ clientname + ".errorMessageInfo where deviceId=ds.deviceMacId) join " + clientname +" .device d on d.deviceId = ds.deviceMacId  WHERE   ds.batteryValue <=70 and batteryValue != -3 AND (ds.deviceTimestamp <= now()-interval 24 hour ) ORDER BY buildingName, floorName, areaName;",
					new BeanPropertyRowMapper<>(DeviceStatus.class)
					);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	/**
	 * @param clientname
	 * @return
	 */
	public List<DeviceStatus> getfeedbackDevices(String clientname) {

		try {
			return jdbcTemplate.query(
					"SELECT ds.deviceMacId,ds.deviceName,ds.deviceSensorId,ds.buildingName,ds.floorName,ds.areaName,convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone) AS lastReportingTime,er.errorCode,er.errorDescription,convert_tz(er.deviceTimeStamp,'+00:00',d.TimeZone) as lastErrorMsgTime,ds.sensorValue, ds.batteryValue,ds.rssiValue FROM "+ clientname + ".deviceStatus ds LEFT JOIN  "+ clientname + ".errorMessageInfo er ON ds.deviceMacId = er.deviceId and er.id=(select max(id) from "+ clientname + ".errorMessageInfo where deviceId=ds.deviceMacId) join " + clientname +" .device d on d.deviceId = ds.deviceMacId  WHERE   ds. sensorName='GateWay' AND (ds.deviceTimestamp <= now()-interval 24 hour ) ORDER BY buildingName, floorName, areaName;",
					new BeanPropertyRowMapper<>(DeviceStatus.class)
					);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public List<DeviceStatus> getoccupancyDisplayDevices(String clientname) {

		try {
			return jdbcTemplate.query(
					"SELECT ds.deviceMacId,ds.deviceName,ds.deviceSensorId,ds.buildingName,ds.floorName,ds.areaName,convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone) AS lastReportingTime,er.errorCode,er.errorDescription,convert_tz(er.deviceTimeStamp,'+00:00',d.TimeZone) as lastErrorMsgTime,ds.sensorValue, ds.batteryValue,ds.rssiValue FROM "+ clientname + ".deviceStatus ds LEFT JOIN  "+ clientname + ".errorMessageInfo er ON ds.deviceMacId = er.deviceId and er.id=(select max(id) from "+ clientname + ".errorMessageInfo where deviceId=ds.deviceMacId) join " + clientname +" .device d on d.deviceId = ds.deviceMacId  WHERE  ds. sensorName like '%Occupancy%' AND (ds.deviceTimestamp <= now()-interval 24 hour )  ORDER BY buildingName, floorName, areaName;",
					new BeanPropertyRowMapper<>(DeviceStatus.class)
					);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	/**
	 * @param clientname
	 * @return
	 */
	public List<DeviceStatus> getPreStopped(String clientname) {
		try {
			return jdbcTemplate.query(
					"SELECT ds.deviceMacId,ds.deviceName,ds.deviceSensorId,ds.buildingName,ds.floorName,ds.areaName,convert_tz(ds.deviceTimestamp,'+00:00',d.TimeZone) AS lastReportingTime,er.errorCode,er.errorDescription,convert_tz(er.deviceTimeStamp,'+00:00',d.TimeZone) as lastErrorMsgTime,ds.sensorValue, ds.batteryValue,ds.rssiValue FROM "+ clientname + ".deviceStatus ds LEFT JOIN  "+ clientname + ".errorMessageInfo er ON ds.deviceMacId = er.deviceId and er.id=(select max(id) from "+ clientname + ".errorMessageInfo where deviceId=ds.deviceMacId) join " + clientname +" .device d on d.deviceId = ds.deviceMacId  WHERE ds. sensorName not in ('QR-Janitor','QR-Feedback') AND (ds.deviceTimestamp <= now()-interval 48 hour ) ORDER BY buildingName, floorName, areaName;",
					new BeanPropertyRowMapper<>(DeviceStatus.class));
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	

}
