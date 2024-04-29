/**
 * 
 */
package com.newReports.controller;

import com.newReports.ExcelExport.ExcelGeneratorDevicesRawdata;
import com.newReports.ExcelExport.SummaryReportExporter;
import com.newReports.entity.SummaryReport;
import com.newReports.repository.DeviceStatusRep;
import com.newReports.repository.SummarySheetRep;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * @author KarthickT
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/Newreports")
public class ExcelExportController {

	@Autowired
	private DeviceStatusRep deviceStatusRep;
	@Autowired
	private SummarySheetRep summarySheetRep;


	@GetMapping("/exportSummarylist")
	@PreAuthorize("hasAuthority('AC_ACTIVE')")
	public void exportSummarylist(HttpServletResponse response, @RequestParam List<String> clientNames)
			throws IOException {
		// Get summary data
		List<List<SummaryReport>> summaryData = new ArrayList<>();
		for (String clientName : clientNames) {
			summaryData.add(deviceStatusRep.getDevicestotaList(clientName.toLowerCase()));
		}

		// Create workbook
		Workbook workbook = SummaryReportExporter.exportSummary(summaryData);

		// Set response headers
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new java.util.Date());
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=summary_report_" + currentDateTime + ".xlsx");

		// Write workbook to response stream
		workbook.write(response.getOutputStream());

		// Close workbook
		workbook.close();
	}


	 @GetMapping("/getExportIntoRawDataExcelFile")
	 @PreAuthorize("hasAuthority('AC_ACTIVE')")
	 public void getExportIntoRawDataExcelFile(@RequestParam List<String> clientName,@RequestParam String startDate,@RequestParam String endDate, HttpServletResponse response) {
		 XSSFWorkbook workbook = ExcelGeneratorDevicesRawdata.generateWorkbook(deviceStatusRep,clientName, startDate, endDate);
	        response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=\"devices_report.xlsx\"");
	        try {
	            workbook.write(response.getOutputStream());
	            workbook.close();
	        } catch (IOException e) {
	            // handle exception
	        }
	    }
	
	

}
