package com.Spring.Boot.EU;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EURawdataService {

	EUSummaryService summary = new EUSummaryService();
	public void Exportexcel(XSSFWorkbook workbook) {
		
		
		
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationcontex.xml");
			// Spring check the blueprint for studentDao bean 
			// from application-context.xml file and return it
		
			EURepositoryDao deviceDao= (EURepositoryDao)context.getBean("repository");
			String[] instance ={"kaercher","kaerchernl","sodexo","soniq","kaercheruk"};
			//XSSFWorkbook workbook = new XSSFWorkbook();
			String excelFilePath = "Registration-export.xlsx";
			for(int i=0;i<instance.length;i++)
			{
			// Getting student data
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
			System.out.println(device.get(0).getDeviceName()+" - "+instance[i]);
		     System.out.println(sheet.getSheetName());
			deviceDao.writeDataLines(device, workbook, sheet);

			
			}
			FileOutputStream outputStream = new FileOutputStream(excelFilePath);
			workbook.write(outputStream);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	    
		
	}
}
