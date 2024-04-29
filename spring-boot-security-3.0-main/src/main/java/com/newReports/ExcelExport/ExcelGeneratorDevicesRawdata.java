package com.newReports.ExcelExport;

import java.util.List;


import com.newReports.entity.DeviceStatus;
import com.newReports.entity.SummaryReport;
import com.newReports.repository.DeviceStatusRep;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelGeneratorDevicesRawdata {
    private static DeviceStatusRep deviceStatusRep;

    public static XSSFWorkbook generateWorkbook(DeviceStatusRep deviceStatusRep, List<String> clientName, String startDate, String endDate) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet summarySheet = workbook.createSheet("Summary");
        int rowNum = 0;
        Row row = summarySheet.createRow(rowNum++);
        row.createCell(0).setCellValue("CLIENT NAME");
        row.createCell(1).setCellValue("TOTAL");
        row.createCell(2).setCellValue("ACTIVE");
        row.createCell(3).setCellValue("STOPPED");
        row.createCell(4).setCellValue("NOT YET REPORTING");
        row.createCell(5).setCellValue("BLE GATEWAY");
        row.createCell(6).setCellValue("BLE DEVICES");
        row.createCell(7).setCellValue("INTRAFFIC DEVICES");
        row.createCell(8).setCellValue("FEEDBACK DEVICES");
        row.createCell(9).setCellValue("OCCUPANCY DISPLAY");
        row.createCell(10).setCellValue("BATTERY LOW");

        // Create cell style for header
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // Create cell style for content
        XSSFCellStyle contentStyle = workbook.createCellStyle();

        for (String clientname : clientName) {
            List<DeviceStatus> devices = deviceStatusRep.getDevicesRawdata(clientname, startDate, endDate);
            List<SummaryReport> summary = deviceStatusRep.getDevicestotaList(clientname);
            XSSFSheet clientSheet = workbook.createSheet(clientname);
            rowNum = 0;
            row = clientSheet.createRow(rowNum++);
            row.createCell(0).setCellValue("DEVICE MACID");
            row.createCell(1).setCellValue("DEVICE NAME");
            row.createCell(2).setCellValue("DEVICE SENSORID");
            row.createCell(3).setCellValue("BUILDING NAME");
            row.createCell(4).setCellValue("FLOOR NAME");
            row.createCell(5).setCellValue("AREA NAME");
            row.createCell(6).setCellValue("LAST REPORTING TIME");
            row.createCell(7).setCellValue("ERROR CODE");
            row.createCell(8).setCellValue("ERROR DESCRIPTION");
            row.createCell(9).setCellValue("LAST ERROR MSG TIME");
            row.createCell(10).setCellValue("SENSOR VALUE");
            row.createCell(11).setCellValue("BATTERY VALUE");
            row.createCell(12).setCellValue("RSSI VALUE");

            // Apply header style to header row
            for (int i = 0; i <= 12; i++) {
                Cell cell = row.getCell(i);
                cell.setCellStyle(headerStyle);
                clientSheet.autoSizeColumn(i);
            }

            // Apply content style to data rows
            for (DeviceStatus device : devices) {
                row = clientSheet.createRow(rowNum++);
                row.createCell(0).setCellValue(device.getDeviceMacId());
                row.createCell(1).setCellValue(device.getDeviceName());
                row.createCell(2).setCellValue(device.getDeviceSensorId());
                row.createCell(3).setCellValue(device.getBuildingName());
                row.createCell(4).setCellValue(device.getFloorName());
                row.createCell(5).setCellValue(device.getAreaName());
                row.createCell(6).setCellValue(device.getLastReportingTime());
                row.createCell(7).setCellValue(device.getErrorCode());
                row.createCell(8).setCellValue(device.getErrorDescription());
                row.createCell(9).setCellValue(device.getLastErrorMsgTime());
                row.createCell(10).setCellValue(device.getSensorValue());
                row.createCell(11).setCellValue(device.getBatteryValue());
                row.createCell(12).setCellValue(device.getRssiValue());
                
                for (int i = 0; i <= 12; i++) {
                    Cell cell = row.getCell(i);
                    cell.setCellStyle(contentStyle);
                    clientSheet.autoSizeColumn(i);
                }
            }

            rowNum = summarySheet.getLastRowNum() + 1;
            row = summarySheet.createRow(rowNum);
            row.createCell(0).setCellValue(clientname);

            // Apply content style to summary data cells
            for (SummaryReport report : summary) {
            	row.createCell(1).setCellValue(report.getTotal());
            	row.createCell(2).setCellValue(report.getActive());
            	row.createCell(3).setCellValue(report.getStopped());
            	row.createCell(4).setCellValue(report.getNotYetReporting());
            	row.createCell(5).setCellValue(report.getBLEGateWay());
            	row.createCell(6).setCellValue(report.getBLEDevices());
            	row.createCell(7).setCellValue(report.getIntrafficDevices());
            	row.createCell(8).setCellValue(report.getFeedback());
            	row.createCell(9).setCellValue(report.getOccupancyDisplay());
            	row.createCell(10).setCellValue(report.getBatteryLow());

                for (int i = 1; i <= 10; i++) {
                    Cell cell = row.getCell(i);
                    cell.setCellStyle(contentStyle);
                    summarySheet.autoSizeColumn(i);
                }
            }
        }
        return workbook;
    }
}



