package com.Spring.Boot;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RawdataService {

	SummaryService summary = new SummaryService();

	public void Exportexcel(XSSFWorkbook workbook) {

		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationcontex.xml");
			// Spring check the blueprint for studentDao bean
			// from application-context.xml file and return it

			RepositoryDao deviceDao = (RepositoryDao) context.getBean("repository");
			String[] instance = { "apple","applebgm","att","bgm","bobrick","bos","bwh","dartmouth","dial","enlighted","gilead","hpe","kitepharma","nationwide","pbm","pegasus","pepsico","pit","pnptechcenter","providence","seatac","shellsmarttoilet","synopsys","takeda","takedala","takedato","wtc"  };
			// String[] instance = {
			// "att","apple","bgm","bos","hdh","jfk","linkedin","pepsico","pnptechcenter","nationwide","synopsys","takeda","dartmouth","pegasus","takedala","hpe","okc"
			// };

			// XSSFWorkbook workbook = new XSSFWorkbook();
			String excelFilePath = "Registration-export.xlsx";

			for (int i = 0; i < instance.length; i++) {

				// Getting device data
				List<DeviceModel> device = null;

				device = deviceDao.getAllDeviceDetails(instance[i]);

				XSSFSheet sheet = workbook.createSheet(instance[i]);

				deviceDao.writeHeaderLine(sheet);
				/*
				 * for(DeviceModel index : device) { System.out.println(index.getDeviceName()+
				 * " - "+instance[i]);
				 * 
				 * }
				 */

				if (device.isEmpty()) {
					System.out.println("Empty");
					device = deviceDao.getAllDeviceDetails(instance[i++]);
				} else {
					System.out.println("Move on next index");
				}
				// System.out.println(device.get(0).getDeviceName()+" - "+instance[i]);
				System.out.println(sheet.getSheetName());
				deviceDao.writeDataLines(device, workbook, sheet);
			}

			FileOutputStream outputStream = new FileOutputStream(excelFilePath);
			workbook.write(outputStream);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
