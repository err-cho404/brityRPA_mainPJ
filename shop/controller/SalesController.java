package co.kr.shop.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.kr.shop.model.Criteria;
import co.kr.shop.model.OrderItemDTO;
import co.kr.shop.model.SalesDataDTO;
import co.kr.shop.service.AdminService;
import co.kr.shop.service.SalesService;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/admin")
public class SalesController {
	@Autowired
    private SalesService salesService;
	@Autowired AdminService adminService;
    @GetMapping("/exportToExcel")
    public void exportToExcel(HttpServletResponse response, @RequestParam(required = false) String keyword) throws IOException {
        // Criteria 설정
        Criteria cri = new Criteria();
        cri.setPageNum(1);
        cri.setAmount(Integer.MAX_VALUE); // 모든 데이터 가져오기

        List<OrderItemDTO> list = adminService.getSalesList(cri);

        // 중복된 상품 ID에 대한 판매 수량 합치기
        Map<Integer, OrderItemDTO> aggregatedData = list.stream()
            .collect(Collectors.toMap(
                OrderItemDTO::getProductId,
                item -> {
                    item.setTotalPrice(item.getProductCount() * item.getProductPrice());
                    item.setSavePoint((int) (Math.floor(item.getTotalPrice() * 0.02)));
                    item.setTotalSavePoint(item.getSavePoint() * item.getProductCount());
                    return item;
                },
                (existing, replacement) -> {
                    existing.setProductCount(existing.getProductCount() + replacement.getProductCount());
                    existing.setTotalPrice(existing.getProductCount() * existing.getProductPrice());
                    existing.setSavePoint((int) (Math.floor(existing.getTotalPrice() * 0.02)));
                    existing.setTotalSavePoint(existing.getSavePoint() * existing.getProductCount());
                    return existing;
                }
            ));

        // 엑셀 파일 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sales Report");

        // 헤더 생성
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("상품ID");
        header.createCell(1).setCellValue("상품명");
        header.createCell(2).setCellValue("색상");
        header.createCell(3).setCellValue("용량");
        header.createCell(4).setCellValue("판매 수량");
        header.createCell(5).setCellValue("단가");
        header.createCell(6).setCellValue("공급가액");
        header.createCell(7).setCellValue("부가세");
        header.createCell(8).setCellValue("합계");

        // 데이터 행 생성
        int rowNum = 1;
        double totalSupplyValue = 0;
        double totalVat = 0;
        double totalAmount = 0;

        for (OrderItemDTO item : aggregatedData.values()) {
            Row row = sheet.createRow(rowNum++);
            double unitPrice =item.getProductPrice()*0.9*(1-item.getProductDiscount()); 
            double supplyValue = item.getProductCount() * item.getProductPrice()*(1-item.getProductDiscount());
            double vat = supplyValue * 0.1;
            double amount = supplyValue * 0.9;

            row.createCell(0).setCellValue(item.getProductId());
            row.createCell(1).setCellValue(item.getProductName());
            row.createCell(2).setCellValue(item.getColorName());
            row.createCell(3).setCellValue(item.getGb());
            row.createCell(4).setCellValue(item.getProductCount());
            row.createCell(5).setCellValue(unitPrice);
            row.createCell(6).setCellValue(amount);
            row.createCell(7).setCellValue(vat);
            row.createCell(8).setCellValue(supplyValue);

            // 총합 계산
            totalSupplyValue += supplyValue;
            totalVat += vat;
            totalAmount += amount;
        }

        // 총합 행 생성
        Row totalRow = sheet.createRow(rowNum);
        totalRow.createCell(0).setCellValue("총 합계");
        totalRow.createCell(6).setCellValue(totalAmount);
        totalRow.createCell(7).setCellValue(totalVat);
        totalRow.createCell(8).setCellValue(totalSupplyValue);

        // 응답 설정
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=sales_report.xlsx");

        // 워크북을 응답에 쓰기
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    @GetMapping("/DaySalesToExcel")
    public void DaySalesToExcel(HttpServletResponse response, @RequestParam(required = false) String keyword) throws IOException {
        // Criteria 설정
        Criteria cri = new Criteria();
        cri.setPageNum(1);
        cri.setAmount(Integer.MAX_VALUE); // 모든 데이터 가져오기

        List<OrderItemDTO> list = adminService.getDaySalesList(cri);

        // 중복된 상품 ID에 대한 판매 수량 합치기
        Map<Integer, OrderItemDTO> aggregatedData = list.stream()
            .collect(Collectors.toMap(
                OrderItemDTO::getProductId,
                item -> {
                    item.setTotalPrice(item.getProductCount() * item.getProductPrice());
                    item.setSavePoint((int) (Math.floor(item.getTotalPrice() * 0.02)));
                    item.setTotalSavePoint(item.getSavePoint() * item.getProductCount());
                    return item;
                },
                (existing, replacement) -> {
                    existing.setProductCount(existing.getProductCount() + replacement.getProductCount());
                    existing.setTotalPrice(existing.getProductCount() * existing.getProductPrice());
                    existing.setSavePoint((int) (Math.floor(existing.getTotalPrice() * 0.02)));
                    existing.setTotalSavePoint(existing.getSavePoint() * existing.getProductCount());
                    return existing;
                }
            ));

        // 엑셀 파일 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sales Report");

        // 헤더 생성
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("상품ID");
        header.createCell(1).setCellValue("상품명");
        header.createCell(2).setCellValue("색상");
        header.createCell(3).setCellValue("용량");
        header.createCell(4).setCellValue("판매 수량");
        header.createCell(5).setCellValue("단가");
        header.createCell(6).setCellValue("공급가액");
        header.createCell(7).setCellValue("부가세");
        header.createCell(8).setCellValue("합계");

        // 데이터 행 생성
        int rowNum = 1;
        double totalSupplyValue = 0;
        double totalVat = 0;
        double totalAmount = 0;

        for (OrderItemDTO item : aggregatedData.values()) {
            Row row = sheet.createRow(rowNum++);
            double unitPrice =item.getProductPrice()*0.9*(1-item.getProductDiscount()); 
            double supplyValue = item.getProductCount() * item.getProductPrice()*(1-item.getProductDiscount());
            double vat = supplyValue * 0.1;
            double amount = supplyValue * 0.9;

            row.createCell(0).setCellValue(item.getProductId());
            row.createCell(1).setCellValue(item.getProductName());
            row.createCell(2).setCellValue(item.getColorName());
            row.createCell(3).setCellValue(item.getGb());
            row.createCell(4).setCellValue(item.getProductCount());
            row.createCell(5).setCellValue(unitPrice);
            row.createCell(6).setCellValue(amount);
            row.createCell(7).setCellValue(vat);
            row.createCell(8).setCellValue(supplyValue);

            // 총합 계산
            totalSupplyValue += supplyValue;
            totalVat += vat;
            totalAmount += amount;
        }

        // 총합 행 생성
        Row totalRow = sheet.createRow(rowNum);
        totalRow.createCell(0).setCellValue("총 합계");
        totalRow.createCell(6).setCellValue(totalAmount);
        totalRow.createCell(7).setCellValue(totalVat);
        totalRow.createCell(8).setCellValue(totalSupplyValue);

        // 응답 설정
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=day_sales_report.xlsx");

        // 워크북을 응답에 쓰기
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    @GetMapping("/salesMember")
    public String getSalesData(
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "age", required = false) String age,
            Model model) {

        List<SalesDataDTO> salesData = salesService.getSalesData(); // 모든 데이터 가져오기

        // 필터링 적용
        if ("male".equalsIgnoreCase(filter)) {
            salesData = salesData.stream()
                .filter(data -> "M".equalsIgnoreCase(data.getGender()))
                .collect(Collectors.toList());
        } else if ("female".equalsIgnoreCase(filter)) {
            salesData = salesData.stream()
                .filter(data -> "F".equalsIgnoreCase(data.getGender()))
                .collect(Collectors.toList());
        }

        // 연령대 필터링
        if (age != null && !age.isEmpty()) {
            salesData = salesData.stream()
                .filter(data -> data.getAgeGroup().equalsIgnoreCase(age))
                .collect(Collectors.toList());
        }

        // 중복 값 합치기
        Map<String, SalesDataDTO> aggregatedData = salesData.stream()
            .collect(Collectors.toMap(
                data -> String.valueOf(data.getProductId()),  // Integer를 String으로 변환
                data -> data,
                (existing, newData) -> {
                    existing.setCountUnder10(existing.getCountUnder10() + newData.getCountUnder10());
                    existing.setCountTeen(existing.getCountTeen() + newData.getCountTeen());
                    existing.setCountTwenties(existing.getCountTwenties() + newData.getCountTwenties());
                    existing.setCountThirties(existing.getCountThirties() + newData.getCountThirties());
                    existing.setCountForties(existing.getCountForties() + newData.getCountForties());
                    existing.setCountFifties(existing.getCountFifties() + newData.getCountFifties());
                    existing.setCountSixties(existing.getCountSixties() + newData.getCountSixties());
                    existing.setCountSeventies(existing.getCountSeventies() + newData.getCountSeventies());
                    existing.setCountEighties(existing.getCountEighties() + newData.getCountEighties());
                    existing.setCountNineties(existing.getCountNineties() + newData.getCountNineties());
                    existing.setCountHundred(existing.getCountHundred() + newData.getCountHundred());
                    return existing;
                }));

        model.addAttribute("salesData", new ArrayList<>(aggregatedData.values()));
        model.addAttribute("selectedAge", age); // 선택한 연령대 추가

        // 반환할 JSP 페이지 결정
        if ("male".equalsIgnoreCase(filter)) {
            return "admin/salesMemberMale";  
        } else if ("female".equalsIgnoreCase(filter)) {
            return "admin/salesMemberFemale"; 
        } else {
            return "admin/salesMember";       
        }
    }


    @GetMapping("/memberSalesToExcel")
    public void memberSalesToExcel(
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "age", required = false) String age,
            HttpServletResponse response) throws IOException {

        List<SalesDataDTO> salesData = salesService.getSalesData(); // 모든 데이터 가져오기

        // 필터링 적용
        if ("male".equalsIgnoreCase(filter)) {
            salesData = salesData.stream()
                .filter(data -> "m".equalsIgnoreCase(data.getGender()))
                .collect(Collectors.toList());
        } else if ("female".equalsIgnoreCase(filter)) {
            salesData = salesData.stream()
                .filter(data -> "f".equalsIgnoreCase(data.getGender()))
                .collect(Collectors.toList());
        }

        // 연령대 필터링
        if (age != null && !age.isEmpty()) {
            salesData = salesData.stream()
                .filter(data -> data.getAgeGroup().equalsIgnoreCase(age))
                .collect(Collectors.toList());
        }

     // 중복 값 합치기
        Map<String, SalesDataDTO> aggregatedData = salesData.stream()
            .collect(Collectors.toMap(
                data -> String.valueOf(data.getProductId()),  // Integer를 String으로 변환
                data -> data,
                (existing, newData) -> {
                    existing.setCountUnder10(existing.getCountUnder10() + newData.getCountUnder10());
                    existing.setCountTeen(existing.getCountTeen() + newData.getCountTeen());
                    existing.setCountTwenties(existing.getCountTwenties() + newData.getCountTwenties());
                    existing.setCountThirties(existing.getCountThirties() + newData.getCountThirties());
                    existing.setCountForties(existing.getCountForties() + newData.getCountForties());
                    existing.setCountFifties(existing.getCountFifties() + newData.getCountFifties());
                    existing.setCountSixties(existing.getCountSixties() + newData.getCountSixties());
                    existing.setCountSeventies(existing.getCountSeventies() + newData.getCountSeventies());
                    existing.setCountEighties(existing.getCountEighties() + newData.getCountEighties());
                    existing.setCountNineties(existing.getCountNineties() + newData.getCountNineties());
                    existing.setCountHundred(existing.getCountHundred() + newData.getCountHundred());
                    return existing;
                }));

        // 엑셀 파일 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sales Data");

        // 헤더 작성
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("상품ID");
        headerRow.createCell(1).setCellValue("상품명");
        headerRow.createCell(2).setCellValue("색상");
        headerRow.createCell(3).setCellValue("용량");
        // 연령대별 판매 수량 헤더 추가
        headerRow.createCell(4).setCellValue("10세 이하 판매수량");
        headerRow.createCell(5).setCellValue("10대 판매수량");
        headerRow.createCell(6).setCellValue("20대 판매수량");
        headerRow.createCell(7).setCellValue("30대 판매수량");
        headerRow.createCell(8).setCellValue("40대 판매수량");
        headerRow.createCell(9).setCellValue("50대 판매수량");
        headerRow.createCell(10).setCellValue("60대 판매수량");
        headerRow.createCell(11).setCellValue("70대 판매수량");
        headerRow.createCell(12).setCellValue("80대 판매수량");
        headerRow.createCell(13).setCellValue("90대 판매수량");
        headerRow.createCell(14).setCellValue("100세 이상 판매수량");

        // 데이터 작성
        int rowNum = 1;
        for (SalesDataDTO item : aggregatedData.values()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(item.getProductId());
            row.createCell(1).setCellValue(item.getProductName());
            row.createCell(2).setCellValue(item.getColorName());
            row.createCell(3).setCellValue(item.getGb());
            
            // 연령대별 판매 수량 데이터 추가
            row.createCell(4).setCellValue(item.getCountUnder10());
            row.createCell(5).setCellValue(item.getCountTeen());
            row.createCell(6).setCellValue(item.getCountTwenties());
            row.createCell(7).setCellValue(item.getCountThirties());
            row.createCell(8).setCellValue(item.getCountForties());
            row.createCell(9).setCellValue(item.getCountFifties());
            row.createCell(10).setCellValue(item.getCountSixties());
            row.createCell(11).setCellValue(item.getCountSeventies());
            row.createCell(12).setCellValue(item.getCountEighties());
            row.createCell(13).setCellValue(item.getCountNineties());
            row.createCell(14).setCellValue(item.getCountHundred());
        }

        // HTTP 응답 설정
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=sales_data.xlsx");

        // 엑셀 파일 출력
        workbook.write(response.getOutputStream());
        workbook.close();
    }





    


}