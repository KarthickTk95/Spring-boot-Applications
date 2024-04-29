package com.newReports.ExcelExport;

import java.util.List;

import com.newReports.entity.SummaryReport;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class SummaryReportExporter {

	 public static Workbook exportSummary(List<List<SummaryReport>> summaryData) {
	        // Create workbook
	        Workbook workbook = new XSSFWorkbook();

	        // Create sheet
	        Sheet sheet = workbook.createSheet("Summary Report");

	        // Create header row
	        Row headerRow = sheet.createRow(0);
	        headerRow.createCell(0).setCellValue("CLIENT NAME");
	        headerRow.createCell(1).setCellValue("TOTAL");
	        headerRow.createCell(2).setCellValue("ACTIVE");
	        headerRow.createCell(3).setCellValue("STOPPED");
	        headerRow.createCell(4).setCellValue("NOT YET REPORTING");
	        headerRow.createCell(5).setCellValue("BLE GATEWAY");
	        headerRow.createCell(6).setCellValue("BLE DEVICES");
	        headerRow.createCell(7).setCellValue("INTRAFFIC DEVICES");
	        headerRow.createCell(8).setCellValue("FEEDBACK DEVICES");
	        headerRow.createCell(9).setCellValue("OCCUPANCY DISPLAY");
	        headerRow.createCell(10).setCellValue("BATTERY LOW ");

	        // Add data to sheet
	        int rowIndex = 1;
	        for (List<SummaryReport> clientSummary : summaryData) {
	            for (SummaryReport summary : clientSummary) {
	                Row row = sheet.createRow(rowIndex++);
	                row.createCell(0).setCellValue(summary.getClientname());
	                row.createCell(1).setCellValue(summary.getTotal());
	                row.createCell(2).setCellValue(summary.getActive());
	                row.createCell(3).setCellValue(summary.getStopped());
	                row.createCell(4).setCellValue(summary.getNotYetReporting());
	                row.createCell(5).setCellValue(summary.getBLEGateWay());
	                row.createCell(6).setCellValue(summary.getBLEDevices());
	                row.createCell(7).setCellValue(summary.getIntrafficDevices());
	                row.createCell(8).setCellValue(summary.getFeedback());
	                row.createCell(9).setCellValue(summary.getOccupancyDisplay());
	                row.createCell(10).setCellValue(summary.getBatteryLow());
	            }
	        }

	        return workbook;
	    }
	
	
}
