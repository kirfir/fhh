/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Test;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author trevor.witjes
 */
public class Test1 {
    
    public static void main(String[] args) throws IOException, InterruptedException {    
        //Workbook wb = new HSSFWorkbook();
        Workbook wb = new XSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("leaguedata");

        Row row = sheet.createRow((short)0);
        String headers [] = new String [] {
            "Team", "Player", "GP", "G", "A", "PTS", "+/-", "STP", "SOG",
            "Hits", "Blocks", "TOI", "G/60", "A/60", "PTS/60", "STP/60",
            "SOG/60", "Hits/60", "Blocks/60"
        };
        
        for (int i=0; i<headers.length; i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(createHelper.createRichTextString(headers[i]));
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyle);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("RFHL.xlsx");
        wb.write(fileOut);
        fileOut.close();
    }
    
}
