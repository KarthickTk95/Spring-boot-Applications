package com.newReports.controller;

import java.io.IOException;
import java.util.List;


import com.newReports.ExcelExport.ExcelGeneratorPacketlossDevicesRawdata;
import com.newReports.repository.DeviceStatusRep;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/Newreports")
public class ExcelPacketlossController {
	
	
	@Autowired
	private DeviceStatusRep deviceStatusRep;
	

		
	@GetMapping("/getExportIntoPacketlossRawDataExcelFile")
	@PreAuthorize("hasAuthority('AC_ACTIVE')")
	public void getExportIntoPacketlossRawDataExcelFile(@RequestParam(name = "clientname") List<String> clientnames,
	                                @RequestParam(name = "startDate") String startDate,
	                                @RequestParam(name = "endDate") String endDate,
	                                HttpServletResponse response) throws IOException {

	    // Set response headers
	    response.setContentType("application/vnd.ms-excel");
	    response.setHeader("Content-Disposition", "attachment; filename=PacketLossReport.xlsx");

	    // Generate Excel workbook
	    Workbook workbook = ExcelGeneratorPacketlossDevicesRawdata.generateExcel(deviceStatusRep,clientnames, startDate, endDate);

	    // Write workbook to response output stream
	    workbook.write(response.getOutputStream());
	    workbook.close();
	}

	
	}



