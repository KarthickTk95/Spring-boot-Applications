package com.newReports.service;

import java.util.ArrayList;
import java.util.List;


import com.newReports.entity.CarparkingModel;
import com.newReports.entity.ClientModel;
import com.newReports.repository.SynopsysRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
public class SynopsysJdbc implements SynopsysRep {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/*
	 * public List<SynopsysModel> getSynopsysOldSummary(String Date) {
	 * 
	 * 
	 * try { return template.query(
	 * "select phd.areaId,phd.entrenceNumber,phd.entrenceType,date(convert_tz(phd.deviceTimeStamp,'+00:00',d.TimeZone)) as Date,(select time(convert_tz(deviceTimeStamp,'+00:00',d.TimeZone)) from synopsys.parkingHistory ph where ph.entrenceNumber=phd.entrenceNumber and date(convert_tz(deviceTimeStamp,'+00:00',d.TimeZone))= '"
	 * +Date+"'  order by time(convert_tz(deviceTimeStamp,'+00:00',d.TimeZone)) desc limit 1) as Time ,count(*) as count from synopsys.parkingHistory phd join synopsys.device d on phd.deviceMacId=d.deviceId where  date(convert_tz(phd.deviceTimeStamp,'+00:00',d.TimeZone))= '"
	 * +Date+"' and areaId not in ('1167')   group by deviceMacId order by  phd.entrenceType,deviceMacId desc;"
	 * , new BeanPropertyRowMapper<>(SynopsysModel.class)); } catch (Exception e) {
	 * e.printStackTrace();
	 * 
	 * return null; }
	 * 
	 * 
	 * }
	 */

	public List<ClientModel> getParkingClients() {

		try {
			List<String> parkingClients = jdbcTemplate.queryForList(
					"SELECT client FROM MasterDB.device_client WHERE sensorName = 'ParkingSensor' GROUP BY client;",
					String.class);

			// Assuming ClientModel class has a constructor that accepts a client parameter
			List<ClientModel> clientModels = new ArrayList<>();
			for (String client : parkingClients) {
				clientModels.add(new ClientModel(client));
			}

			return clientModels;

		} catch (Exception e) {
			return null;
		}
	}

	public List<String> getDistinctBuildingNames(String parkingClient) {
		String sqlQuery = "SELECT DISTINCT buildingName FROM " + parkingClient
				+ ".deviceStatus WHERE sensorName = 'ParkingSensor'";
		return jdbcTemplate.queryForList(sqlQuery, String.class);
	}

	public List<CarparkingModel> getSynopsysOldSummary(String Date, String parkingClient,
													   List<String> parkingBuildings) {

		try {

			String buildingNames = String.join("','", parkingBuildings); // Convert list to comma-separated string
			return jdbcTemplate.query(
					"select ds.buildingName,CASE WHEN ph.areaId IS NOT NULL THEN convert_tz(ds.deviceTimeStamp, '+00:00', d.TimeZone) ELSE ds.deviceMacId END AS LastReportingTime,COALESCE(ph.areaId, 'Not yet') AS areaId,COALESCE(ph.entrenceNumber, 'started this') AS entrenceNumber,COALESCE(ph.entrenceType, 'Device') AS entrenceType,COALESCE(max((convert_tz(ph.deviceTimeStamp,'+00:00',d.TimeZone))),'today') as LastDeductionTime,count(*) as count from "
							+ parkingClient + ".deviceStatus ds  join " + parkingClient
							+ ".device d on ds.deviceMacId = d.deviceId left join " + parkingClient
							+ ".parkingHistory ph on ds.deviceMacId = ph.deviceMacId and  date(convert_tz(ph.deviceTimeStamp,'+00:00',d.TimeZone)) = '"
							+ Date + "'  where sensorName = 'ParkingSensor'  and ds.buildingName in  ('" + buildingNames
							+ "')  group by ds.deviceMacId order by  ph.entrenceType asc,entrenceNumber;",
					new BeanPropertyRowMapper<>(CarparkingModel.class));

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	public List<CarparkingModel> getbuildingCurrentTime(String parkingClient, List<String> parkingBuildings) {

		try {

			String buildingNames = String.join("','", parkingBuildings); // Convert list to comma-separated string
			return jdbcTemplate.query(
					"select buildingName,CONVERT_TZ(now(), '+00:00',timeZone) clientTime from " + parkingClient
							+ ".building where buildingName in ('" + buildingNames + "');",
					new BeanPropertyRowMapper<>(CarparkingModel.class));

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

	}

	public List<CarparkingModel> getCarparkingRawData(List<String> dates, String parkingClient,
			List<String> parkingBuildings) {

		try {
			String buildingNames = String.join("','", parkingBuildings); // Convert list to comma-separated string
			String DatesList = String.join("','", dates);
			String query = "select ds.buildingName,ds.areaId,date(convert_tz(p.deviceTimeStamp,'+00:00',d.TimeZone)) as dDate,time(convert_tz(p.deviceTimeStamp,'+00:00',d.TimeZone)) as dTime,entrenceType,1 as CarCount from "
					+ parkingClient + ".parkingHistory p join " + parkingClient
					+ ".device d on d.deviceId=p.deviceMacId join " + parkingClient
					+ ".deviceStatus ds on ds.deviceMacId = p.deviceMacId where date(convert_tz(p.deviceTimeStamp,'+00:00',d.TimeZone)) in ('"
					+ DatesList + "') and ds.buildingName in ('" + buildingNames
					+ "')    order by time(convert_tz(p.deviceTimeStamp,'+00:00',d.TimeZone)) asc;";
			System.out.println("query -> " + query);
			return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(CarparkingModel.class));

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

}
