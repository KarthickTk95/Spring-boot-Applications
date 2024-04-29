package com.newReports.controller;


import com.newReports.entity.CarparkingModel;
import com.newReports.repository.SynopsysRep;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/Newreports")
public class ExcelParkingController {
	@Autowired
private SynopsysRep synopsysRep;

	@GetMapping("/exportToExcelSeprateBuilding")
	@PreAuthorize("hasAuthority('AC_ACTIVE')")
	public void exportToExcelSeprateBuilding(@RequestParam List<String> dates, @RequestParam String parkingClient,
			@RequestParam List<String> parkingBuildings, HttpServletResponse response) {

		try (Workbook workbook = new XSSFWorkbook()) {

			CellStyle boldCellStyle = workbook.createCellStyle();
			Font boldFont = workbook.createFont();
			boldFont.setBold(true);
			boldCellStyle.setFont(boldFont);

			Sheet sheet = workbook.createSheet("Summary");
			int rowNum = 0;

			for (String date : dates) {
				List<CarparkingModel> synopsysData = synopsysRep.getSynopsysOldSummary(date, parkingClient,
						parkingBuildings);

				for (String building : parkingBuildings) {
					// Create a header row for each building
					Row headerRow = sheet.createRow(rowNum++);
					headerRow.createCell(0).setCellValue("Date: " + date + " | Building: " + building);
					Cell headerCell = headerRow.createCell(0);
					headerCell.setCellValue("Date: " + date + " | Building: " + building);
					headerCell.setCellStyle(boldCellStyle);

					Row dataHeaderRow = sheet.createRow(rowNum++);
					dataHeaderRow.createCell(0).setCellValue("Building Name");
					dataHeaderRow.createCell(1).setCellValue("Last Reporting Time");
					dataHeaderRow.createCell(2).setCellValue("Area ID");
					dataHeaderRow.createCell(3).setCellValue("Entrance Number");
					dataHeaderRow.createCell(4).setCellValue("Entrance Type");
					dataHeaderRow.createCell(5).setCellValue("Last Deduction Time");
					dataHeaderRow.createCell(6).setCellValue("Count");
					dataHeaderRow.setRowStyle(boldCellStyle);

					// After populating the data, apply autoSizeColumn to fit content
					for (int i = 0; i < dataHeaderRow.getLastCellNum(); i++) {
						sheet.autoSizeColumn(i);
					}

					int totalEntries = 0;
					int inCount = 0;
					int outCount = 0;
					for (CarparkingModel synopsys : synopsysData) {
						if (building.equals(synopsys.getBuildingName())) {
							Row row = sheet.createRow(rowNum++);
							row.createCell(0).setCellValue(synopsys.getBuildingName());
							row.createCell(1).setCellValue(synopsys.getLastReportingTime());
							row.createCell(2).setCellValue(synopsys.getAreaId());
							row.createCell(3).setCellValue(synopsys.getEntrenceNumber());
							row.createCell(4).setCellValue(synopsys.getEntrenceType());
							row.createCell(5).setCellValue(synopsys.getLastDeductionTime());
							row.createCell(6).setCellValue(synopsys.getCount());

							totalEntries++;

							// Check EntranceType and increment the respective counters
							if ("IN".equals(synopsys.getEntrenceType())) {
								inCount += Integer.parseInt(synopsys.getCount());

							} else if ("OUT".equals(synopsys.getEntrenceType())) {
								outCount += Integer.parseInt(synopsys.getCount());

							}

						}
					}

					headerRow = sheet.createRow(rowNum++); // Empty row between buildings
					// Create a new row for counts after processing all entries
					Row dataHeaderRow1 = sheet.createRow(rowNum++);
					dataHeaderRow1.createCell(0).setCellValue("IN COUNT");
					dataHeaderRow1.createCell(1).setCellValue("OUT COUNT");
					dataHeaderRow1.createCell(2).setCellValue("TOTAL");
					dataHeaderRow1.createCell(3).setCellValue("PERCENTAGE");
					dataHeaderRow1.setRowStyle(boldCellStyle);

					// After populating the data, apply autoSizeColumn to fit content
					for (int i = 0; i < dataHeaderRow.getLastCellNum(); i++) {
						sheet.autoSizeColumn(i);
					}

					Row row1 = sheet.createRow(rowNum++);
					row1.createCell(0).setCellValue(inCount);
					row1.createCell(1).setCellValue(outCount);
					row1.createCell(2).setCellValue(inCount + outCount);
					double percentage = 100.0 - (outCount / (double) inCount) * 100.0;
					row1.createCell(3).setCellValue(percentage);
					headerRow = sheet.createRow(rowNum++); // Empty row between buildings

				}
			}

			List<CarparkingModel> synopsysData = synopsysRep.getCarparkingRawData(dates, parkingClient,
					parkingBuildings);
			for (String building : parkingBuildings) {
				System.out.println("Process for building ...." + building);
				List<CarparkingModel> carparking = new ArrayList<>();
				for (CarparkingModel c : synopsysData) {
					if (c.getBuildingName().equalsIgnoreCase(building)) {
						carparking.add(c);
					}
				}
				createSheet(workbook, building, carparking);
			}

			// Set content type and header for the response
		    response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=CAR PARKING_DATA.xlsx");

			// Write the workbook to the response
			workbook.write(response.getOutputStream());
			response.flushBuffer();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createSheet(Workbook workbook, String sheetName, List<CarparkingModel> carparkingData) {
		Sheet sheet = workbook.createSheet(sheetName);

		CellStyle boldCellStyle = workbook.createCellStyle();
		Font boldFont = workbook.createFont();
		boldFont.setBold(true);
		boldCellStyle.setFont(boldFont);
		// Create header row
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Building Name");
		headerRow.createCell(1).setCellValue("Area ID");
		headerRow.createCell(2).setCellValue("Date");
		headerRow.createCell(3).setCellValue("Time");
		headerRow.createCell(4).setCellValue("Entrance Type");
		headerRow.createCell(5).setCellValue("Car Count");
		headerRow.setRowStyle(boldCellStyle);

		// After populating the data, apply autoSizeColumn to fit content
		for (int i = 0; i < headerRow.getLastCellNum(); i++) {
			sheet.autoSizeColumn(i);
		}

		// Fill data rows
		int rowNum = 1;
		for (CarparkingModel model : carparkingData) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(model.getBuildingName());
			row.createCell(1).setCellValue(model.getAreaId());
			row.createCell(2).setCellValue(model.getdDate());
			row.createCell(3).setCellValue(model.getdTime());
			row.createCell(4).setCellValue(model.getEntrenceType());
			row.createCell(5).setCellValue(model.getCarCount());

		}
	}

}
