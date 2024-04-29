package com.Spring.Boot.EU;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EUSummaryService {

public void Exportexcel1() {
		
		
		
		try {
			EURawdataService rawdata = new EURawdataService();
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationcontex.xml");
			// Spring check the blueprint for studentDao bean 
			// from application-context.xml file and return it
		
			EUSummaryRepositoryDao summaryDao= (EUSummaryRepositoryDao)context.getBean("repository2");
			String[] instance ={ "kaercher","kaerchernl","sodexo","soniq","kaercheruk"};
			XSSFWorkbook workbook = new XSSFWorkbook();
			String excelFilePath = "Stopped device Report-exportEU.xlsx";
			
			
			XSSFSheet sheet = workbook.createSheet("Summary");
			
	
			
	        
			summaryDao.writeHeaderLine(sheet);
			for(int i=0;i<instance.length;i++)
			{
			// Getting student data
			List<DeviceModel> summary;
			
			summary = summaryDao.getAllDeviceSummary(instance[i]);
	
			/*
			 * for(DeviceModel index : device) { System.out.println(index.getDeviceName()+
			 * " - "+instance[i]);
			 * 
			 * }
			 */
			System.out.println(summary.size()+" - size");
			System.out.println(summary.get(0).getTotal()+" - "+instance[i]);
		     System.out.println(sheet.getSheetName());
		     summaryDao.writeDataLines(summary, workbook, sheet);

			
			}
			//FileOutputStream outputStream = new FileOutputStream(excelFilePath);
			//workbook.write(outputStream);
			
			rawdata.Exportexcel(workbook);
		}catch(Exception e) {
			e.printStackTrace();
		}
	    
		
	}
}
