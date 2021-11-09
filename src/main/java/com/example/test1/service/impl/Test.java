package com.example.test1.service.impl;

import com.example.test1.dto.BillForReport;
import com.example.test1.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Test {
    private File createReport(List<BillForReport> billList, String fileName){
        File file = null;
        File pathFile = new File("D:\\data\\");
        boolean exists = pathFile.exists();
        if (!exists) {
            System.out.println("路径不存在");
            return null;
        }

        try {
            file = File.createTempFile(fileName, ".xls", pathFile);  //临时文件
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 添加一个大板
        HSSFSheet sheet = workbook.createSheet("模版");
        // 定义标题样式，这个是单元样式
        HSSFCellStyle setStyle = workbook.createCellStyle();
        setStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        setStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平

        //设置全边框
        setStyle.setBorderBottom(HSSFCellStyle.BORDER_THICK); //下边框
        setStyle.setBorderLeft(HSSFCellStyle.BORDER_THICK);//左边框
        setStyle.setBorderTop(HSSFCellStyle.BORDER_THICK);//上边框
        setStyle.setBorderRight(HSSFCellStyle.BORDER_THICK);//右边框

        // 表头标题样式
        HSSFFont headfont = workbook.createFont();
        headfont.setFontName("黑体");
        headfont.setFontHeightInPoints((short) 20);// 字体大小
        setStyle.setFont(headfont);


        // 这里设置一下普通单元格格式
        HSSFFont font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font2.setFontHeightInPoints((short) 12);
        // 这里设置一下普通样式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font2);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        style.setWrapText(true); // 换行

        // 设置列宽  （第几列，宽度）
        sheet.setColumnWidth( 0, 3200);
        sheet.setColumnWidth( 1, 3200);
        sheet.setColumnWidth( 2, 3200);
        sheet.setColumnWidth( 3, 3200);
        sheet.setColumnWidth( 4, 3200);
        sheet.setColumnWidth( 5, 3200);
        sheet.setColumnWidth( 6, 3200);
        sheet.setColumnWidth( 7, 3800);
        sheet.setDefaultRowHeight((short) 0x270);//设置行高----貌似没有效果，所以下面每行独立设置行高

        // 固定内容
        String [] two = new String[]{"客户", "", "石种", "", "荒料号", "", "扎号", ""};
        String [] three = new String[]{"片号", "大板编号", "大板规格", "扣损规格", "销售面积"};
        String [] four = new String[]{"长", "宽", "厚", "长", "宽", "长", "宽", "长", "宽", "长", "宽"};
        // 第一行
        HSSFRow rowHeadOne = sheet.createRow((short)0);
        HSSFCell cellOne = rowHeadOne.createCell(0);
        // cellOne.setCellValue("福建省南安市弘一石业有限公司");
        cellOne.setCellStyle(setStyle);

        // 循环给一行加上边框
        for(int i=0; i<=14; i++){
            cellOne = rowHeadOne.createCell(i);
            if(i == 0){
                cellOne.setCellValue("福建省南安市弘一石业有限公司");
            }
            setStyle = workbook.createCellStyle();
            setStyle.setAlignment(CellStyle.ALIGN_CENTER);
            //下边框
            setStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            //左边框
            setStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            //上边框
            setStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            //右边框
            setStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellOne.setCellStyle(setStyle);
        }

        // 设置合并
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 14);
        sheet.addMergedRegion(cellRangeAddress);

        // 第二行
        HSSFRow rowTwo = sheet.createRow((short)1);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 2));
        HSSFCell cellTwo = rowTwo.createCell(0);
        cellTwo.setCellValue("客户");


        HSSFCell cellTwo3 = rowTwo.createCell(3);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));
        cellTwo3.setCellValue("石种");


        HSSFCell cellTwo6 = rowTwo.createCell(6);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 8));
        cellTwo6.setCellValue("荒料号");


        HSSFCell cellTwo9 = rowTwo.createCell(9);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 10, 11));
        cellTwo9.setCellValue("扎号");


        for(int i=1;i<billList.size();i++) {
            HSSFRow row = sheet.createRow((short) (i+1));
            BillForReport bill = billList.get(i);
            row.createCell(0).setCellValue(bill.getSerialNumber());
            row.createCell(1).setCellValue(bill.getOrderNumber());
            row.createCell(2).setCellValue(bill.getProductName());
            row.createCell(3).setCellValue(bill.getPaymentUserId());
            row.createCell(4).setCellValue(bill.getHandler());
            row.createCell(5).setCellValue(bill.getTransactionAmount());
        }

        //auto column width 自适应列宽
        /*HSSFRow row = workbook.getSheetAt(0).getRow(2);
        for(int colNum=0;colNum<row.getLastCellNum();colNum++){
            workbook.getSheetAt(0).autoSizeColumn(colNum);
        }*/

        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return file;
    }

    @org.junit.Test
    public void Tests(){
        BillForReport bill = new BillForReport();
        bill.setHandler("head");
        bill.setSerialNumber(1);
        bill.setOrderNumber(1);
        bill.setProductName("name");
        bill.setPaymentUserId("id");
        bill.setTransactionAmount("Amount");
        List<BillForReport> billForReportList = new ArrayList<>();
        billForReportList.add(bill);
        createReport(billForReportList , "file");
    }
}
