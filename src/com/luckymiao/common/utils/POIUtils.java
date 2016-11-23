package com.luckymiao.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POIUtils {
	/**
	 * 参数必须是元素为Map类型的List集合
	 * @param mapList
	 * @return
	 */
	public static Workbook exportListNoStyle(List<Map<String, String>> mapList){
		HSSFWorkbook workbook = new HSSFWorkbook();// 创建一个Excel文件  
		HSSFSheet sheet = workbook.createSheet();// 创建一个Excel的Sheet  
		int i = 0;
		for (Map eMap : mapList) {
			Iterator it = eMap.values().iterator();
			Row row = sheet.createRow(i);
			int j = 0;
			while(it.hasNext()){
				Cell cell = row.createCell(j);
				String value = (String)it.next();
				cell.setCellValue(value);
				j++;
			}
			i++;
		}
		return workbook;
	}
	/**
	 * 读取标准的Excel文件（不含合并单元格）
	 * @param excelName Excel文件位置 
	 * @param sheetIndex sheet索引位置
	 * @param x1 起始行索引
	 * @param y1 起始列索引
	 * @param y2 截止列索引
	 * @return
	 */
	public static List readStandardExcelToList(String excelName, int sheetIndex, int x1, int y1, int y2){
		Workbook workbook = null;
		List resultList = new ArrayList();
		try{
			File excelFile = new File(excelName);
			if(excelFile.exists() && excelFile.isFile()){
				InputStream excelIn = new FileInputStream(excelName);
				if(excelName.endsWith(".xls")){
					workbook = new HSSFWorkbook(excelIn); 
				}else if(excelName.endsWith(".xlsx")){
					workbook = new XSSFWorkbook(excelIn); 
				}
				if(workbook != null){
					Sheet sheet = workbook.getSheetAt(sheetIndex);
					Row currRow = null;
					Cell currCell = null;
					for(int i=x1; i<=sheet.getLastRowNum(); i++){
						currRow = sheet.getRow(i);
						if(currRow != null){
							List rowList = new ArrayList();
							for(int j=y1; j<=y2; j++){
								currCell = currRow.getCell(j);
								if(currCell != null){
									rowList.add(getStringCellValue(currCell));
								}
							}
							resultList.add(rowList);
						}
						
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return resultList;
	}
	private static String getStringCellValue(Cell cell){
		String val = "";
		if(cell != null){
			int cellType = cell.getCellType();
			switch(cellType){
				case Cell.CELL_TYPE_STRING : val = cell.getStringCellValue(); break;
				case Cell.CELL_TYPE_NUMERIC : val = String.valueOf(cell.getNumericCellValue()); break;
				case Cell.CELL_TYPE_FORMULA : val = cell.getCellFormula(); break;
				case Cell.CELL_TYPE_BOOLEAN : val = String.valueOf(cell.getBooleanCellValue()); break;
			}
		}
		return val;
	}
	public static void main(String[] args) throws Exception {
		List list = readStandardExcelToList("D:\\temp\\data.xlsx", 0, 1, 0 ,0);
		System.out.println(list.size());
	}

}
