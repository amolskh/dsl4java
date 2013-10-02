package org.selenium.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	public static Map<String,String> testcaseMapping = new HashMap<String,String>();

	public static void  ReadTestcaseExcel(String filePath) throws IOException {
		CreateTestNGxml nt = new CreateTestNGxml();		
        File file = new File(filePath);
        InputStream inp = new FileInputStream(file.getAbsolutePath());
		String fileExtn = GetFileExtension(filePath);
		Workbook wb_xssf; //Declare XSSF WorkBook
		Workbook wb_hssf; //Declare HSSF WorkBook
		Sheet sheet = null; // sheet can be used as common for XSSF and HSSF

		if (fileExtn.equalsIgnoreCase("xlsx"))
		{
			wb_xssf = new XSSFWorkbook(inp);
			//log("xlsx="+wb_xssf.getSheetName(0));
			sheet = wb_xssf.getSheetAt(0);
		}
		if (fileExtn.equalsIgnoreCase("xls"))
		{
			POIFSFileSystem fs = new POIFSFileSystem(inp);
			wb_hssf = new HSSFWorkbook(fs);
			//log("xls="+wb_hssf.getSheetName(0));
			sheet = wb_hssf.getSheetAt(0);
		}
		Iterator<Row> rows = sheet.rowIterator(); // Now we have rows ready from

		while (rows.hasNext()) 
		{ 
			Row row = (Row) rows.next();
			Iterator<Cell> cells = row.cellIterator();

			String key = null;
			String value=null;

			while (cells.hasNext())
			{
				Cell cell = (Cell) cells.next();

				switch ( cell.getCellType() ) 
				{
				case Cell.CELL_TYPE_STRING:
					if(cell.getColumnIndex()==0){
						nt.createTestList(cell.getRichStringCellValue().getString());
						key=cell.getRichStringCellValue().getString();
						break;
					}
					value = cell.getRichStringCellValue().getString();		
					testcaseMapping.put(key, value);					
					break;
				case Cell.CELL_TYPE_NUMERIC:
					if(DateUtil.isCellDateFormatted(cell)) {
						log(cell.getDateCellValue()+"");
					} else {
						System.out.println(cell.getNumericCellValue());
					}
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					log(cell.getBooleanCellValue()+"");
					break;
				case Cell.CELL_TYPE_FORMULA:
					log(cell.getCellFormula());
					break;
				default:
				}
			}
		}
		inp.close();
	} 

	private static void log(String message)
	{
		System.out.println(message);
	}
	
	private static String GetFileExtension(String fname2)
	{
		String fileName = fname2;
		int mid= fileName.lastIndexOf(".");
		String fname=fileName.substring(0,mid);
		String ext=fileName.substring(mid+1,fileName.length());
		return ext;
	}	
}




