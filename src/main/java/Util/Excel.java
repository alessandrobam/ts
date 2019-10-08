/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/*import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;*/


/**
 *
 * @author Alessandro
 */
public class Excel {
    
    /*
 public static void readExcel(){
         try {
                long startTime = System.currentTimeMillis();
                FileInputStream fileInputStream = new FileInputStream("C:\\Temp\\GettingThere.xlsm");
                
                XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream); 
                
                XSSFSheet sheet = workBook.getSheetAt(0); 
                
                
                
                int totalRows = sheet.getPhysicalNumberOfRows(); 
                System.out.println("total no of rows >>>>"+totalRows); 
                
                long estimatedTime = System.currentTimeMillis() - startTime;
                
                
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                        System.out.print(cellRef.formatAsString());
                        System.out.print(" - ");

                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_STRING:
                                System.out.println(cell.getRichStringCellValue().getString());
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    System.out.println(cell.getDateCellValue());
                                } else {
                                    System.out.println(cell.getNumericCellValue());
                                }
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                System.out.println(cell.getBooleanCellValue());
                                break;
                            case Cell.CELL_TYPE_FORMULA:
                                System.out.println(cell.getCellFormula());
                                break;
                            default:
                                System.out.println();
                        }
                    }
                }
                
                
                
                
                
                
                Util.Messagebox(sheet.getRow(6-1).getCell(2-1).getStringCellValue());
                
                System.out.println(estimatedTime);
                
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
        }*/
}


