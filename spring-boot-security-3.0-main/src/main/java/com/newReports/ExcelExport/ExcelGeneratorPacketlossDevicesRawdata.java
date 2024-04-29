package com.newReports.ExcelExport;

import java.util.ArrayList;
import java.util.List;


import com.newReports.entity.PacketlossReport;
import com.newReports.entity.SummaryReport;
import com.newReports.repository.DeviceStatusRep;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelGeneratorPacketlossDevicesRawdata {
    private static DeviceStatusRep deviceStatusRep;

    public static Workbook generateExcel(DeviceStatusRep deviceStatusRep, List<String> clientnames, String startDate, String endDate) {
        Workbook workbook = new XSSFWorkbook();

        // Create summary sheet for all clients
        Sheet summarySheet = workbook.createSheet("Summary");

        // Apply header style to summary sheet
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontName("Calibri");
        headerFont.setFontHeightInPoints((short) 12);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFont(headerFont);

        // Apply style to packet loss sheet
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Calibri");
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        // Populate summary report data for all clients
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
       
        
        row.setRowStyle(headerStyle);

        for (String clientname : clientnames) {
            List<SummaryReport> summaryReportData = new ArrayList<>();
            try {
                summaryReportData = deviceStatusRep.getDevicestotaList(clientname);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            if (!summaryReportData.isEmpty()) {
                for (SummaryReport summaryReport : summaryReportData) {
                    row = summarySheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(summaryReport.getClientname());
                    row.createCell(1).setCellValue(summaryReport.getTotal());
                    row.createCell(2).setCellValue(summaryReport.getActive());
                    row.createCell(3).setCellValue(summaryReport.getStopped());
                    row.createCell(4).setCellValue(summaryReport.getNotYetReporting());
                    row.createCell(5).setCellValue(summaryReport.getBLEGateWay());
                    row.createCell(6).setCellValue(summaryReport.getBLEDevices());
                    row.createCell(7).setCellValue(summaryReport.getIntrafficDevices());
                    row.createCell(8).setCellValue(summaryReport.getFeedback());
                    row.createCell(9).setCellValue(summaryReport.getOccupancyDisplay());
                    row.createCell(10).setCellValue(summaryReport.getBatteryLow());

                    
                    row.setRowStyle(style);
                }
            }
        }

        // Create sheet for packet loss report for each client
        for (String clientname : clientnames) {
            Sheet packetlossSheet = workbook.createSheet(clientname);

            // Populate packet loss report data for each client
            List<PacketlossReport> packetlossReportData = new ArrayList<>();
            try {
                packetlossReportData = deviceStatusRep.getDevicesPacketlossRawdata(clientname, startDate, endDate);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!packetlossReportData.isEmpty()) {
                rowNum = 0;
                row = packetlossSheet.createRow(rowNum++);
                row.createCell(0).setCellValue("DEVICE MACID");
                row.createCell(1).setCellValue("DEVICE NAME");
                row.createCell(2).setCellValue("DEVICE SENSORID");
                row.createCell(3).setCellValue("BUILDING NAME");
                row.createCell(4).setCellValue("FLOOR NAME");
                row.createCell(5).setCellValue("AREA NAME");
                row.createCell(6).setCellValue("SENSOR NAME");
                row.createCell(7).setCellValue("DEVICE TIMESTAMP");
                row.createCell(8).setCellValue("SENSOR VALUE");
                row.createCell(9).setCellValue("BATTERY VALUE");
                row.createCell(10).setCellValue("RSSI VALUE");
                row.createCell(11).setCellValue("ERROR CODE");
                row.createCell(12).setCellValue("EXP");
                row.createCell(13).setCellValue("RECEIVED");
                row.createCell(14).setCellValue("LOSS %");

                
                
                row.setRowStyle(headerStyle);

                for (PacketlossReport packetlossReport : packetlossReportData) {
                    row = packetlossSheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(packetlossReport.getDeviceMacId());
                    row.createCell(1).setCellValue(packetlossReport.getDeviceName());
                    row.createCell(2).setCellValue(packetlossReport.getDeviceSensorId());
                    row.createCell(3).setCellValue(packetlossReport.getBuildingName());
                    row.createCell(4).setCellValue(packetlossReport.getFloorName());
                    row.createCell(5).setCellValue(packetlossReport.getAreaName());
                    row.createCell(6).setCellValue(packetlossReport.getSensorName());
                    row.createCell(7).setCellValue(packetlossReport.getDeviceTimeStamp());
                    row.createCell(8).setCellValue(packetlossReport.getSensorValue());
                    row.createCell(9).setCellValue(packetlossReport.getBatteryValue());
                    row.createCell(10).setCellValue(packetlossReport.getRssiValue	());
                    row.createCell(11).setCellValue(packetlossReport.getErrorCode());
                    row.createCell(12).setCellValue(packetlossReport.getExp());
                    row.createCell(13).setCellValue(packetlossReport.getReceived());
                    row.createCell(14).setCellValue(packetlossReport.getLossP());
                    
                    row.setRowStyle(style);
                }

                // Fit content to cells
                for (int i = 0; i < 14; i++) {
                    packetlossSheet.autoSizeColumn(i);
                }
            }
        }

        return workbook;
    }
}