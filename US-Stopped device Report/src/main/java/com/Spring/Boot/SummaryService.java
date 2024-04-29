package com.Spring.Boot;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SummaryService {

	public void Exportexcel1() {

		try {
			RawdataService rawdata = new RawdataService();
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationcontex.xml");
			// Spring check the blueprint for studentDao bean
			// from application-context.xml file and return it

			SummaryRepositoryDao summaryDao = (SummaryRepositoryDao) context.getBean("repository2");
			String[] instance = { "apple","applebgm","att","bgm","bobrick","bos","bwh","dartmouth","dial","enlighted","gilead","hpe","kitepharma","nationwide","pbm","pegasus","pepsico","pit","pnptechcenter","providence","seatac","shellsmarttoilet","synopsys","takeda","takedala","takedato","wtc"};;
			XSSFWorkbook workbook = new XSSFWorkbook();
			String excelFilePath = "Stopped device ReportUS-export.xlsx";

			XSSFSheet sheet = workbook.createSheet("Summary");

			summaryDao.writeHeaderLine(sheet);
			for (int i = 0; i < instance.length; i++) {
				// Getting student data
				List<DeviceModel> summary;

				summary = summaryDao.getAllDeviceSummary(instance[i]);

				/*
				 * for(DeviceModel index : device) { System.out.println(index.getDeviceName()+
				 * " - "+instance[i]);
				 * 
				 * }
				 */
				System.out.println(summary.size() + " - size");
				System.out.println(summary.get(0).getTotal() + " - " + instance[i]);
				System.out.println(sheet.getSheetName());
				summaryDao.writeDataLines(summary, workbook, sheet);

			}
			// FileOutputStream outputStream = new FileOutputStream(excelFilePath);
			// workbook.write(outputStream);

			rawdata.Exportexcel(workbook);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
