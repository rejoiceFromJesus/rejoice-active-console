package com.rejoice.active.console.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.rejoice.active.console.entity.User;

/**
 * Created by aboullaite on 2017-02-23.
 */
public class ExcelView extends AbstractXlsView{

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");

        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) model.get("users");

        // create excel xls sheet
        Sheet sheet = workbook.createSheet("User Detail");
        sheet.setDefaultColumnWidth(30);

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);


        // create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Firstname");
        header.getCell(0).setCellStyle(style);
        header.createCell(1).setCellValue("LastName");
        header.getCell(1).setCellStyle(style);
        header.createCell(2).setCellValue("Age");
        header.getCell(2).setCellStyle(style);
        header.createCell(3).setCellValue("Job Title");
        header.getCell(3).setCellStyle(style);
        header.createCell(4).setCellValue("Company");
        header.getCell(4).setCellStyle(style);
        header.createCell(5).setCellValue("Address");
        header.getCell(5).setCellStyle(style);
        header.createCell(6).setCellValue("City");
        header.getCell(6).setCellStyle(style);
        header.createCell(7).setCellValue("Country");
        header.getCell(7).setCellStyle(style);
        header.createCell(8).setCellValue("Phone Number");
        header.getCell(8).setCellStyle(style);



        int rowCount = 1;


    }

}
