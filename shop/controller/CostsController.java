package co.kr.shop.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.kr.shop.model.CostsVO;
import co.kr.shop.model.Criteria;
import co.kr.shop.model.PageDTO;
import co.kr.shop.service.CostsService;

@Controller
@RequestMapping("/admin")
public class CostsController {
	private static final Logger logger = LoggerFactory.getLogger(CostsController.class);
	@Autowired CostsService costsService;
	/* 지출 등록 페이지 접속 */
    @RequestMapping(value = "costsEnroll", method = RequestMethod.GET)
    public void costsEnrollGET() throws Exception{
        logger.info("지출 등록 페이지 접속");
    }
    /* 지출 조회 페이지 */
    @GetMapping({"/costsDetail", "/costsModify"})
	public void costsGetInfoGET(int costId, Criteria cri, Model model) {
		
    	logger.info("costsGetInfo()........." + costId);

        
    	model.addAttribute("cri", cri);
    	
    	model.addAttribute("costsInfo", costsService.costsGetDetail(costId));
        
	}
    /* 지출 관리 페이지 접속 */
    @RequestMapping(value = "costsManage", method = RequestMethod.GET)
    public void costsManageGET(Criteria cri, Model model) throws Exception{
        logger.info("제품 관리 페이지 접속");
        /* 상품 리스트 데이터 */
		List list = costsService.costsGetList(cri);
		
		if(!list.isEmpty()) {
			model.addAttribute("list", list);
		} else {
			model.addAttribute("listCheck", "empty");
			return;
		}
		
		/* 페이지 인터페이스 데이터 */
		model.addAttribute("pageMaker", new PageDTO(cri, costsService.costsGetTotal(cri)));
    }  
    @GetMapping("/costsExportToExcel")
    public void exportCostsToExcel(HttpServletResponse response, Criteria cri) {
        // 엑셀 파일 생성
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Costs");

        // 헤더 생성
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("지출 번호");
        headerRow.createCell(1).setCellValue("지출 내역");
        headerRow.createCell(2).setCellValue("지출 가격");
        headerRow.createCell(3).setCellValue("지출 방식");
        headerRow.createCell(4).setCellValue("지출 날짜");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 기존 날짜 형식

        int rowNum = 1;
        boolean hasMoreData = true; // 더 많은 데이터가 있는지 확인하는 플래그

        while (hasMoreData) {
            // 현재 페이지의 데이터 가져오기
            List<CostsVO> costsList = costsService.costsGetList(cri);
            
            if (costsList.isEmpty()) {
                hasMoreData = false; // 데이터가 없으면 루프 종료
            } else {
                // 데이터 추가
                for (CostsVO cost : costsList) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(cost.getCostId());
                    row.createCell(1).setCellValue(cost.getCostType());
                    row.createCell(2).setCellValue(cost.getPrice());
                    row.createCell(3).setCellValue(cost.getPaymentMethod());

                    String costDateStr = cost.getCostDate(); // 문자열로 가져오기
                    try {
                        Date costDate = originalFormat.parse(costDateStr); // 문자열을 Date로 변환
                        String formattedDate = dateFormat.format(costDate); // 원하는 형식으로 포맷
                        row.createCell(4).setCellValue(formattedDate);
                    } catch (ParseException e) {
                        e.printStackTrace(); // 변환 오류 처리
                        row.createCell(4).setCellValue(costDateStr); // 변환 실패 시 원래 문자열 사용
                    }
                }
                
                // 다음 페이지로 이동
                cri.setPageNum(cri.getPageNum() + 1); // 페이지 수 증가
            }
        }

        // 파일 다운로드 설정
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=costs.xlsx");
        
        // 엑셀 파일 쓰기
        try (OutputStream os = response.getOutputStream()) {
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /* 지출 등록 */
	@PostMapping("/costsEnroll")
	public String costsEnrollPOST(CostsVO costs, RedirectAttributes rttr) {
		
		logger.info("costsEnrollPOST......" + costs);
		
		costsService.costsEnroll(costs);
		
		rttr.addFlashAttribute("enroll_result", costs.getCostType());
		
		return "redirect:/admin/costsManage";
	}	
	/* 지출 정보 수정 */
	@PostMapping("/costsModify")
	public String costsModifyPOST(CostsVO vo, RedirectAttributes rttr) {
		
		logger.info("costsModifyPOST.........." + vo);
		
		int result = costsService.costsModify(vo);
		
		rttr.addFlashAttribute("modify_result", result);
		
		return "redirect:/admin/costsManage";		
		
	}
	/* 상품 정보 삭제 */
	@PostMapping("/costsDelete")
	public String costsDeletePOST(int costId, RedirectAttributes rttr) {
		
		logger.info("costsDeletePOST..........");
		
		int result = costsService.costsDelete(costId);
		
		rttr.addFlashAttribute("delete_result", result);
		
		return "redirect:/admin/costsManage";
		
	}
	
}
