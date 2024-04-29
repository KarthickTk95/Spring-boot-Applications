package com.Spring.Boot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class SummaryRepositoryDao implements Repository {

	int rowCount = 1;
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
		

		
		
		public List<DeviceModel> getAllDeviceSummary(String instance) {
			
			//for(int i=0;i<instance.length;i++) {
				// Implementation of RowMapper interface
				return jdbcTemplate.query("select  count(*) as Total,\r\n"
						+ "sum(if (deviceTimeStamp >= now() - interval 24 hour,1,0)) active ,\r\n"
						+ "sum(if (deviceTimeStamp <= now() - interval 24 hour,1,0)) Stopped,\r\n"
						+ "(select count(*) from "+instance+".deviceStatus where deviceTimeStamp >= now() - interval 24 hour and  sensorName in ('ZanOpenAreaTraffic','ZanInTraffic') ) as TotalZanWave,\r\n"
						+ "(select count(*) from "+instance+".deviceStatus where sensorName in ('ZanOpenAreaTraffic','ZanInTraffic')and deviceTimeStamp <= now() - interval 24 hour) as StoppedZanWave,\r\n"
						+ "(select count(*) from "+instance+".deviceStatus where  deviceTimeStamp is null) as NotYetReporting,\r\n"
						+ "(select count(*) from "+instance+".deviceStatus where sensorName='Gateway'and deviceTimeStamp <= now() - interval 24 hour) as FeedBackDevices,\r\n"
						+ "(select count(*) from "+instance+".deviceStatus where sensorName='BLEGateWay'and deviceTimeStamp <= now() - interval 24 hour) as Gateway\r\n"
						+ " from "+instance+".deviceStatus where sensorName not in('QR-Janitor','QR-Feedback')", new RowMapper<DeviceModel>() {
							
					public DeviceModel mapRow(ResultSet rs, int rowNum) throws SQLException {
						DeviceModel device = new DeviceModel();
						System.out.println(rs.getString(1));
						device.setClientName(instance);
						device.setTotal(rs.getInt(1));
						device.setActive(rs.getInt(2));
						device.setStopped(rs.getInt(3));
						device.setTotalZanWave(rs.getString(4));
						device.setStoppedZanWave(rs.getString(5));
						device.setNotYetReporting(rs.getString(6));
						device.setFeedBackDevices(rs.getString(7));
						device.setGateway(rs.getString(8));
						//System.out.print(rs.getString(3));
						return device;
					}
				});
			}
			
		//}

		

		public void writeHeaderLine(XSSFSheet sheet) {

			CellStyle style = sheet.getWorkbook().createCellStyle();
	        Font font = sheet.getWorkbook().createFont();
	        font.setFontName("Calibri");
	        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
	        //style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        font.setBold(equals(font));
	        sheet.autoSizeColumn(0);
	        style.setFont(font);
			
			Row headerRow = sheet.createRow(0);

			Cell headerCell = headerRow.createCell(0);
			headerCell.setCellValue("ClientName");
			headerRow.getCell(0).setCellStyle(style);
			sheet.autoSizeColumn(0);
			headerCell = headerRow.createCell(1);
			headerCell.setCellValue("Total");
			headerRow.getCell(1).setCellStyle(style);
			sheet.autoSizeColumn(1);
			System.out.println("HAi");
			headerCell = headerRow.createCell(2);
			headerCell.setCellValue("active");
			headerRow.getCell(2).setCellStyle(style);
			sheet.autoSizeColumn(2);
			headerCell = headerRow.createCell(3);
			headerCell.setCellValue("Stopped");
			headerRow.getCell(3).setCellStyle(style);
			sheet.autoSizeColumn(3);
			
			headerCell = headerRow.createCell(4);
			headerCell.setCellValue("TotalZanWave");
			headerRow.getCell(4).setCellStyle(style);
			sheet.autoSizeColumn(4);
			headerCell = headerRow.createCell(5);
			headerCell.setCellValue("StoppedZanWave");
			headerRow.getCell(5).setCellStyle(style);
			sheet.autoSizeColumn(5);
			headerCell = headerRow.createCell(6);
			headerCell.setCellValue("NotYetReporting");
			headerRow.getCell(6).setCellStyle(style);
			sheet.autoSizeColumn(6);
			headerCell = headerRow.createCell(7);
			headerCell.setCellValue("FeedBackDevices");
			headerRow.getCell(7).setCellStyle(style);
			sheet.autoSizeColumn(7);
			headerCell = headerRow.createCell(8);
			headerCell.setCellValue("Gateway");
			headerRow.getCell(8).setCellStyle(style);
			sheet.autoSizeColumn(8);
			
			
			
		}

		
	public void writeDataLines(List<DeviceModel> result, XSSFWorkbook workbook, XSSFSheet sheet) throws SQLException {

			//System.out.println( "in "+instance);
//			rowCount=2;
		
		for(DeviceModel index : result) {
		   // System.out.println(index.getDeviceName());
			System.out.println(index.getTotal());
		    int Total = index.getTotal();
			int active = index.getActive();
			// float rating = result.getFloat("rating");
			int Stopped = index.getStopped();
			// String Last = result.getString("last");
			String TotalZanWave = index.getTotalZanWave();
			String StoppedZanWave = index.getStoppedZanWave();
			String NotYetReporting = index.getNotYetReporting();
			String FeedBackDevices = index.getFeedBackDevices();
			String Gateway = index.getGateway();
			
			
			Row row = sheet.createRow(rowCount);

			int columnCount = 0;
			Cell cell = row.createCell(columnCount++);
			
			cell.setCellValue(index.getClientName());
			cell = row.createCell(columnCount++);
			cell.setCellValue(Total);

			cell = row.createCell(columnCount++);
			cell.setCellValue(active);

			
			cell = row.createCell(columnCount++);
			cell.setCellValue(Stopped);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(TotalZanWave);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(StoppedZanWave);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(NotYetReporting);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(FeedBackDevices);
			
			cell = row.createCell(columnCount++);
			cell.setCellValue(Gateway);
			
			
			
			rowCount++;
			 
		}
		
		
			
	}

	@Override
	public List<DeviceModel> getAllDeviceDetails(String instance) {
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
