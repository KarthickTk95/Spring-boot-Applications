package com.Spring.Boot.EU;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;



public class EURepositoryDao implements EURepository{
	
	// Defining JdbcTemplate as member variable in order
	// to use the query() method of the JdbcTemplate's class
	private JdbcTemplate jdbcTemplate;
	//private JdbcTemplate jdbcTemplateA;
	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	  
	
	
	
	
	public List<DeviceModel> getAllDeviceDetails(String instance) {
		
		//for(int i=0;i<instance.length;i++) {
			// Implementation of RowMapper interface
			return jdbcTemplate.query("select * from(select ds.deviceMacId,ds.deviceName,ds.deviceSensorId ,ds.buildingName,ds.floorName,ds.areaName,ds.deviceTimestamp as lastReportingTime,er.errorCode,er.errorDescription,er.deviceTimeStamp as lastErrorMsgTime,ds.sensorValue,ds.batteryValue,ds.rssiValue,\r\n"
					+ "CASE\r\n"
					+ "    WHEN sensorValue=-3 and rssiValue=-3 and batteryValue=-3 THEN \"Not yet reporting\"\r\n"
					+ "    WHEN errorCode=2 THEN \"Sensor is faild\"\r\n"
					+ "    WHEN batteryValue<=70 THEN \"Battery Issue\"\r\n"
					+ "    WHEN rssiValue<-80 THEN \"Range issue\"\r\n"
					+ "    WHEN errorCode=4 and sensorName='peoplecount' THEN \"Door Not Clossed properly\"\r\n"
					+ "    WHEN sensorValue=255 THEN \"Reporting 255 reading\"\r\n"
					+ "    WHEN bootReasonCode=6  THEN \"Not yet reporting\"\r\n"
					+ "    ELSE \"-\"\r\n"
					+ "END as Comments\r\n"
					+ "from "+instance+".deviceStatus ds left join "+instance+".errorMessageInfo er on er.deviceId=ds.deviceMacId where ds.sensorName not in('QR-Janitor','QR-Feedback') and (ds.deviceTimestamp <= now()-interval 24 hour ) order by er.deviceTimeStamp desc)a group by deviceMacId\r\n"
					+ "order by buildingName,floorName,areaName", new RowMapper<DeviceModel>() {

				public DeviceModel mapRow(ResultSet rs, int rowNum) throws SQLException {
					DeviceModel device = new DeviceModel();
					device.setDeviceMacId(rs.getString(1));;
					
					device.setDeviceName(rs.getString(2));;
					device.setDeviceSensorId(rs.getInt(3));
					device.setBuildingName(rs.getString(4));
					device.setFloorName(rs.getString(5));
					device.setAreaName(rs.getString(6));
					device.setLastReportingTime(rs.getString(7));
					device.setErrorCode(rs.getString(8));
					device.setErrorDescription(rs.getString(9));
					device.setLastErrorMsgTime(rs.getString(10));
					device.setSensorValue(rs.getDouble(11));
					device.setBatteryValue(rs.getDouble(12));
					device.setRssiValue(rs.getDouble(13));
					device.setNotes(rs.getString(14));
					//System.out.print(rs.getString(3));
					return device;
				}
			});
		}
		
	//}

	

	public void writeHeaderLine(XSSFSheet sheet) {

		Row headerRow = sheet.createRow(0);
		CellStyle style = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Calibri");
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        //style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(equals(font));
        sheet.autoSizeColumn(0);
        style.setFont(font);
		

		Cell headerCell = headerRow.createCell(0);
		headerCell.setCellValue("DeviceMacId");
		headerRow.getCell(0).setCellStyle(style);
		sheet.autoSizeColumn(0);
		System.out.println("HAi");
		headerCell = headerRow.createCell(1);
		headerCell.setCellValue("DeviceName");
		headerRow.getCell(1).setCellStyle(style);
		sheet.autoSizeColumn(1);

		headerCell = headerRow.createCell(2);
		headerCell.setCellValue("DeviceSensorId");
		headerRow.getCell(2).setCellStyle(style);
		sheet.autoSizeColumn(2);
		
		headerCell = headerRow.createCell(3);
		headerCell.setCellValue("BuildingName");
		headerRow.getCell(3).setCellStyle(style);
		sheet.autoSizeColumn(3);
		
		headerCell = headerRow.createCell(4);
		headerCell.setCellValue("FloorName");
		headerRow.getCell(4).setCellStyle(style);
		sheet.autoSizeColumn(4);

		headerCell = headerRow.createCell(5);
		headerCell.setCellValue("AreaName");
		headerRow.getCell(5).setCellStyle(style);
		sheet.autoSizeColumn(5);
		
		headerCell = headerRow.createCell(6);
		
		headerCell.setCellValue("LastReportingTime");
		headerRow.getCell(6).setCellStyle(style);
		sheet.autoSizeColumn(6);
		
	
		
		headerCell = headerRow.createCell(7);
		headerCell.setCellValue("ErrorCode");
		headerRow.getCell(7).setCellStyle(style);
		sheet.autoSizeColumn(7);

		headerCell = headerRow.createCell(8);
		headerCell.setCellValue("ErrorDescription");
		headerRow.getCell(8).setCellStyle(style);
		sheet.autoSizeColumn(8);
		
		headerCell = headerRow.createCell(9);
		headerCell.setCellValue("LastErrorMsgTime");
		headerRow.getCell(9).setCellStyle(style);
		sheet.autoSizeColumn(9);
		
		headerCell = headerRow.createCell(10);
		headerCell.setCellValue("SensorValue");
		headerRow.getCell(10).setCellStyle(style);
		sheet.autoSizeColumn(10);

		headerCell = headerRow.createCell(11);
		headerCell.setCellValue("BatteryValue");
		headerRow.getCell(11).setCellStyle(style);
		sheet.autoSizeColumn(11);
		
		headerCell = headerRow.createCell(12);
		headerCell.setCellValue("RssiValue");
		headerRow.getCell(12).setCellStyle(style);
		sheet.autoSizeColumn(12);

		headerCell = headerRow.createCell(13);
		headerCell.setCellValue("Notes");
		headerRow.getCell(13).setCellStyle(style);
		sheet.autoSizeColumn(13);
	
		
		
		
	}

	
public void writeDataLines(List<DeviceModel> result, XSSFWorkbook workbook, XSSFSheet sheet) throws SQLException {
	int rowCount = 0;
		//System.out.println( "in "+instance);
//		rowCount=2;
	System.out.println(sheet.getSheetName());
	for(DeviceModel index : result) {
	   // System.out.println(index.getDeviceName());
	    rowCount++;
		//int id = index.getId();
		String deviceMacId = index.getDeviceMacId();
		// float rating = result.getFloat("rating");
		String deviceName = index.getDeviceName();
		// String Last = result.getString("last");
		int deviceSensorId = index.getDeviceSensorId();
		String buildingName = index.getBuildingName();
		String floorName = index.getFloorName();
		String areaName = index.getAreaName();
		String LastReportingTime =index.getLastReportingTime();
		String ErrorCode = index.getErrorCode();
		String ErrorDescription = index.getErrorDescription();
		String LastErrorMsgTime = index.getLastErrorMsgTime();
		double sensorValue = index.getSensorValue();
		double batteryValue = index.getBatteryValue();
		double rssiValue = index.getRssiValue();
		String Notes = index.getNotes();
		
		Row row = sheet.createRow(rowCount);

		int columnCount = 0;
		Cell cell = row.createCell(columnCount++);
		cell.setCellValue(deviceMacId);
		

		cell = row.createCell(columnCount++);
		cell.setCellValue(deviceName);
		

		cell = row.createCell(columnCount++);
		cell.setCellValue(deviceSensorId);
		

		cell = row.createCell(columnCount++);
		cell.setCellValue(buildingName);
		
		cell = row.createCell(columnCount++);
		cell.setCellValue(floorName);

		cell = row.createCell(columnCount++);
		cell.setCellValue(areaName);
		
		cell = row.createCell(columnCount++);
		cell.setCellValue(LastReportingTime);

	
		
		cell = row.createCell(columnCount++);
		cell.setCellValue(ErrorCode);
		
		cell = row.createCell(columnCount++);
		cell.setCellValue(ErrorDescription);

		cell = row.createCell(columnCount++);
		cell.setCellValue(LastErrorMsgTime);
		
		cell = row.createCell(columnCount++);
		cell.setCellValue(sensorValue);

		cell = row.createCell(columnCount++);
		cell.setCellValue(batteryValue);
		
		cell = row.createCell(columnCount++);
		cell.setCellValue(rssiValue);

		cell = row.createCell(columnCount++);
		cell.setCellValue(Notes);

		 
	}
	
	
		
}

@Override
public List<DeviceModel> getAllDeviceSummary(String instance) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<DeviceModel> getAllDeviceDetailsEU(String instance) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<DeviceModel> getAllDeviceSummaryEU(String instance) {
	// TODO Auto-generated method stub
	return null;
}





}
