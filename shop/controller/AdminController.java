package co.kr.shop.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.kr.shop.model.AttachImageVO;
import co.kr.shop.model.ColorVO;
import co.kr.shop.model.CostsVO;
import co.kr.shop.model.Criteria;
import co.kr.shop.model.MemberVO;
import co.kr.shop.model.OrderCancelDTO;
import co.kr.shop.model.OrderDTO;
import co.kr.shop.model.OrderItemDTO;
import co.kr.shop.model.PageDTO;
import co.kr.shop.model.ProductVO;
import co.kr.shop.model.SalesDataDTO;
import co.kr.shop.service.AdminService;
import co.kr.shop.service.AttachService;
import co.kr.shop.service.ColorService;
import co.kr.shop.service.CostsService;
import co.kr.shop.service.GraphService;
import co.kr.shop.service.MemberService;
import co.kr.shop.service.OrderService;
import co.kr.shop.service.ProductService;
import lombok.extern.log4j.Log4j;
@Log4j
@Controller
@RequestMapping("/admin")
public class AdminController {
 
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private ColorService colorService;
    @Autowired
	private AdminService adminService;
    @Autowired
	private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired 
    private AttachService attachService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CostsService costsService;
    @Autowired
	private GraphService graphService;
    /* 관리자 메인 페이지 이동 */
    @GetMapping("/main")
    public String getMainPage(Model model) {
        // Top 10 판매 제품을 가져옵니다.
        List<OrderItemDTO> topProducts = graphService.getTop10SoldProducts();
        model.addAttribute("topProducts", topProducts);
     // 최근 일주일 간의 수익을 가져옵니다.
        List<OrderItemDTO> weeklySales = graphService.getWeeklySales();
        model.addAttribute("weeklySales", weeklySales);
        List<OrderItemDTO> monthlySales = graphService.getMonthlySalesByCompany2024();
        model.addAttribute("monthlySales", monthlySales);
        List<SalesDataDTO> getSalesData = graphService.getAgePublisherSalesData();
        model.addAttribute("getSalesData", getSalesData);
        List<OrderItemDTO> monthlyRevenue = graphService.getMonthlyRevenueByCompany2024();
        model.addAttribute("monthlyRevenue", monthlyRevenue);
     // 2025년 수익 예측
        Map<String, List<Double>> predictions = graphService.predictMonthlyRevenueFor2025(monthlyRevenue);
        model.addAttribute("predictions", predictions);
        log.info("Predictions Data: " + predictions);
        return "admin/main"; // main.jsp로 이동
    }
    /* 색상 관리 페이지 접속 */
    @RequestMapping(value = "colorManage", method = RequestMethod.GET)
    public void colorManageGET(Criteria cri, Model model) throws Exception{
    	logger.info("색상 관리 페이지 접속.........." + cri);
        
        /* 색상 목록 출력 데이터 */
        List list = colorService.colorGetList(cri);
        if(!list.isEmpty()) {
			model.addAttribute("list",list);	// 색상 존재 경우
		} else {
			model.addAttribute("listCheck", "empty");	// 색상 존재하지 않을 경우
		}
        /* 페이지 이동 인터페이스 데이터 */
        int total = colorService.colorGetTotal(cri);
        
        PageDTO pageMaker = new PageDTO(cri, total);
        
        model.addAttribute("pageMaker", pageMaker);
    }
    /* 상품 조회 페이지 */
    @GetMapping({"/productDetail", "/productModify"})
	public void productGetInfoGET(int productId, Criteria cri, Model model) {
		
		logger.info("productGetInfo()........." + productId);
		
		/* 목록 페이지 조건 정보 */
		model.addAttribute("cri", cri);
		
		/* 조회 페이지 정보 */
		model.addAttribute("productInfo", adminService.productGetDetail(productId));
		
	}
    /* 색상 등록 페이지 접속 */
    @RequestMapping(value = "colorEnroll", method = RequestMethod.GET)
    public void colorEnrollGET() throws Exception{
        logger.info("색상 등록 페이지 접속");
    }
    
    /* 제품 등록 페이지 접속 */
    @RequestMapping(value = "productEnroll", method = RequestMethod.GET)
    public void productEnrollGET() throws Exception{
        logger.info("제품 등록 페이지 접속");
    }
    /* 제품 등록 페이지 접속 */
    @RequestMapping(value = "orderEnroll", method = RequestMethod.GET)
    public void orderEnrollGET() throws Exception{
        logger.info("주문 등록 페이지 접속");
    }
    /* 제품 관리 페이지 접속 */
    @RequestMapping(value = "productManage", method = RequestMethod.GET)
    public void productManageGET(Criteria cri, Model model) throws Exception{
        logger.info("제품 관리 페이지 접속");
        /* 상품 리스트 데이터 */
		List list = adminService.productGetList(cri);
		
		if(!list.isEmpty()) {
			model.addAttribute("list", list);
		} else {
			model.addAttribute("listCheck", "empty");
			return;
		}
		
		/* 페이지 인터페이스 데이터 */
		model.addAttribute("pageMaker", new PageDTO(cri, adminService.productGetTotal(cri)));
    }    
    @RequestMapping(value="colorEnroll.do", method = RequestMethod.POST)
    public String colorEnrollPOST(ColorVO color, RedirectAttributes rttr) throws Exception{
    	logger.info("colorEnroll :" +  color);
        
		colorService.colorEnroll(color);      // 색상 등록 쿼리 수행
        
        rttr.addFlashAttribute("enroll_result", color.getColorName());
        
        return "redirect:/admin/colorManage";
    }
    /* 색상 상세 페이지 */
    @GetMapping({"/colorDetail", "/colorModify"})
	public void colorGetInfoGET(int colorId, Criteria cri, Model model) throws Exception {
		
		logger.info("colorDetail......." + colorId);
		
		/* 색상 관리 페이지 정보 */
		model.addAttribute("cri", cri);
		
		/* 선택 색상 정보 */
		model.addAttribute("colorInfo", colorService.colorGetDetail(colorId));
		
	}
    /* 색상 정보 수정 */
	@PostMapping("/colorModify")
	public String colorModifyPOST(ColorVO color, RedirectAttributes rttr) throws Exception{
		
		logger.info("colorModifyPOST......." + color);
		
		int result = colorService.colorModify(color);
		
		rttr.addFlashAttribute("modify_result", result);
		
		return "redirect:/admin/colorManage";
		
	}
	/* 상품 등록 */
	@PostMapping("/productEnroll")
	public String productEnrollPOST(ProductVO product, RedirectAttributes rttr) {
		
		logger.info("productEnrollPOST......" + product);
		
		adminService.productEnroll(product);
		
		rttr.addFlashAttribute("enroll_result", product.getProductName());
		
		return "redirect:/admin/productManage";
	}	
	/* 색상 검색 팝업창 */
	@GetMapping("/productPop")
	public void productPopGET(Criteria cri, Model model) throws Exception{
		
		logger.info("productPopGET.......");
		cri.setAmount(5);
		/* 게시물 출력 데이터 */
		List list = productService.productGetList(cri);
		
		if(!list.isEmpty()) {
			model.addAttribute("list",list);	
		} else {
			model.addAttribute("listCheck", "empty");	
		}
		
		
		/* 페이지 이동 인터페이스 데이터 */
		model.addAttribute("pageMaker", new PageDTO(cri, productService.productGetTotal(cri)));	
	
	}
	/* 첨부 파일 업로드 */
	@PostMapping(value="/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AttachImageVO>> uploadAjaxActionPOST(MultipartFile[] uploadFile) {
		logger.info("uploadAjaxActionPOST..........");
		/* 이미지 파일 체크 */
		for(MultipartFile multipartFile: uploadFile) {
			
			File checkfile = new File(multipartFile.getOriginalFilename());
			String type = null;
			
			try {
				type = Files.probeContentType(checkfile.toPath());
				logger.info("MIME TYPE : " + type);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(!type.startsWith("image")) {
				
				List<AttachImageVO> list = null;
				return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
				
			}
			
		}// for
		String uploadFolder = "D:\\upload";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		String str = sdf.format(date);
		
		String datePath = str.replace("-", File.separator);
		/* 폴더 생성 */
		File uploadPath = new File(uploadFolder, datePath);
		
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}		
		/* 이미저 정보 담는 객체 */
		List<AttachImageVO> list = new ArrayList();
		// 향상된 for
		for(MultipartFile multipartFile : uploadFile) {
			/* 이미지 정보 객체*/
			AttachImageVO vo = new AttachImageVO();
			/* 파일 이름 */
			String uploadFileName = multipartFile.getOriginalFilename();
			vo.setFileName(uploadFileName);
			vo.setUploadPath(datePath);
			/* uuid 적용 파일 이름 */
			String uuid = UUID.randomUUID().toString();
			vo.setUuid(uuid);
			uploadFileName = uuid + "_" + uploadFileName;
			/* 파일 위치, 파일 이름을 합친 File 객체 */
			File saveFile = new File(uploadPath, uploadFileName);
			
			/* 파일 저장 */
			try {
				multipartFile.transferTo(saveFile);
				File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);
				
				BufferedImage bo_image = ImageIO.read(saveFile);
				/* 비율 */
				double ratio = 3;
				/*넓이 높이*/
				int width = (int) (bo_image.getWidth() / ratio);
				int height = (int) (bo_image.getHeight() / ratio);
				BufferedImage bt_image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
								
				Graphics2D graphic = bt_image.createGraphics();
				
				graphic.drawImage(bo_image, 0, 0,width,height, null);
					
				ImageIO.write(bt_image, "jpg", thumbnailFile); 
				/* 방법 2 */
				/* File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);	
				
				BufferedImage bo_image = ImageIO.read(saveFile);

					//비율 
					double ratio = 3;
					//넓이 높이
					int width = (int) (bo_image.getWidth() / ratio);
					int height = (int) (bo_image.getHeight() / ratio);					
				
				
				Thumbnails.of(saveFile)
		        .size(width, height)
		        .toFile(thumbnailFile);*/
			} catch (Exception e) {
				e.printStackTrace();
			} 		
			list.add(vo);
		}
		ResponseEntity<List<AttachImageVO>> result = new ResponseEntity<List<AttachImageVO>>(list, HttpStatus.OK);
		return result;
	}
	/* 상품 정보 수정 */
	@PostMapping("/productModify")
	public String productModifyPOST(ProductVO vo, RedirectAttributes rttr) {
		
		logger.info("productModifyPOST.........." + vo);
		
		int result = adminService.productModify(vo);
		
		rttr.addFlashAttribute("modify_result", result);
		
		return "redirect:/admin/productManage";		
		
	}
	/* 상품 정보 삭제 */
	@PostMapping("/productDelete")
	public String productDeletePOST(int productId, RedirectAttributes rttr) {
		
		logger.info("productDeletePOST..........");
		
		List<AttachImageVO> fileList = adminService.getAttachInfo(productId);
		
		if(fileList != null) {
			
			List<Path> pathList = new ArrayList();
			
			fileList.forEach(vo ->{
				
				// 원본 이미지
				Path path = Paths.get("D:\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName());
				pathList.add(path);
				
				// 섬네일 이미지
				path = Paths.get("D:\\upload", vo.getUploadPath(), "s_" + vo.getUuid()+"_" + vo.getFileName());
				pathList.add(path);
				
			});
			
			pathList.forEach(path ->{
				path.toFile().delete();
			});
			
		}
		
		int result = adminService.productDelete(productId);
		
		rttr.addFlashAttribute("delete_result", result);
		
		return "redirect:/admin/productManage";
		
	}
	/* 색상 정보 삭제 */
	@PostMapping("/colorDelete")
	public String colorDeletePOST(int colorId, RedirectAttributes rttr) {
		
		logger.info("colorDeletePOST..........");
		
		int result = 0;
		
		try {
			
			result = colorService.colorDelete(colorId);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result = 2;
			rttr.addFlashAttribute("delete_result", result);
			
			return "redirect:/admin/colorManage";
			
		}
		
		
		rttr.addFlashAttribute("delete_result", result);
		
		return "redirect:/admin/colorManage";
		
	}	
	/* 이미지 파일 삭제 */
	@PostMapping("/deleteFile")
	public ResponseEntity<String> deleteFile(String fileName){
		
		logger.info("deleteFile........" + fileName);
		File file = null;
		try {
			/* 썸네일 파일 삭제 */
			file = new File("D:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
			
			file.delete();
			
			/* 원본 파일 삭제 */
			String originFileName = file.getAbsolutePath().replace("s_", "");
			
			logger.info("originFileName : " + originFileName);
			
			file = new File(originFileName);
			
			file.delete();
			
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
			return new ResponseEntity<String>("fail", HttpStatus.NOT_IMPLEMENTED);
			
		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	/* 주문 현황 페이지 */
	@GetMapping("/orderList")
	public String orderListGET(Criteria cri, Model model) {
		List<OrderDTO> list = adminService.getOrderList(cri);
		
		if(!list.isEmpty()) {
			model.addAttribute("list", list);
			model.addAttribute("pageMaker", new PageDTO(cri, adminService.getOrderTotal(cri)));
		} else {
			model.addAttribute("listCheck", "empty");
		}
		
		return "/admin/orderList";
	}
	/* 주문 등록 */
	@PostMapping("/orderEnroll")
	public String orderEnrollPost(OrderDTO od, HttpServletRequest request) {
		
		System.out.println(od);		
		orderService.order(od);
		
		MemberVO member = new MemberVO();
		member.setMemberId(od.getMemberId());
		
		HttpSession session = request.getSession();
		
		try {
			MemberVO memberLogin = memberService.memberLogin(member);
			memberLogin.setMemberPw("");
			session.setAttribute("member", memberLogin);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return "redirect:/admin/orderList";
	}
	/* 상품 검색 팝업창 */
	@GetMapping("/colorPop")
	public void colorPopGET(Criteria cri, Model model) throws Exception{
		
		logger.info("colorPopGET.......");
		cri.setAmount(5);
		/* 게시물 출력 데이터 */
		List list = colorService.colorGetList(cri);
		
		if(!list.isEmpty()) {
			model.addAttribute("list",list);	// 작가 존재 경우
		} else {
			model.addAttribute("listCheck", "empty");	// 작가 존재하지 않을 경우
		}
		
		
		/* 페이지 이동 인터페이스 데이터 */
		model.addAttribute("pageMaker", new PageDTO(cri, colorService.colorGetTotal(cri)));	
	
	}
	/* 주문삭제 */
	@PostMapping("/orderCancle")
	public String orderCanclePOST(OrderCancelDTO dto) {
		
		orderService.orderCancle(dto);
		logger.info("member : " + dto.getMemberId() );
		return "redirect:/admin/orderList?keyword=" + dto.getKeyword() + "&amount=" + dto.getAmount() + "&pageNum=" + dto.getPageNum();
	}
	@GetMapping("/salesTally")
	public String salesOrderTotal(Criteria cri, Model model) {
	    List<OrderItemDTO> allData = new ArrayList<>();
	    int pageNum = 1;

	    // 페이지 네이션을 이용하여 모든 데이터를 가져오기
	    while (true) {
	        cri.setPageNum(pageNum);
	        List<OrderItemDTO> list = adminService.getSalesList(cri);
	        if (list.isEmpty()) {
	            break;
	        }
	        allData.addAll(list);
	        pageNum++;
	    }

	    if (!allData.isEmpty()) {
	        // 중복된 상품 ID에 대한 판매 수량 합치기
	        Map<Integer, OrderItemDTO> aggregatedData = allData.stream()
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

	        List<OrderItemDTO> finalList = new ArrayList<>(aggregatedData.values());

	        // 모델에 데이터 추가
	        model.addAttribute("list", finalList);
	        model.addAttribute("pageMaker", new PageDTO(cri, adminService.salesOrderTotal(cri)));
	    } else {
	        model.addAttribute("listCheck", "empty");
	    }

	    return "/admin/salesTally";
	}
	@GetMapping("/salesDayTally")
	public String salesDayOrderTotal(Criteria cri, Model model) {
	    List<OrderItemDTO> allData = new ArrayList<>();
	    int pageNum = 1;

	    // 페이지 네이션을 이용하여 모든 데이터를 가져오기
	    while (true) {
	        cri.setPageNum(pageNum);
	        List<OrderItemDTO> list = adminService.getDaySalesList(cri);
	        if (list.isEmpty()) {
	            break;
	        }
	        allData.addAll(list);
	        pageNum++;
	    }

	    if (!allData.isEmpty()) {
	        // 중복된 상품 ID에 대한 판매 수량 합치기
	        Map<Integer, OrderItemDTO> aggregatedData = allData.stream()
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

	        List<OrderItemDTO> finalList = new ArrayList<>(aggregatedData.values());

	        // 모델에 데이터 추가
	        model.addAttribute("list", finalList);
	        model.addAttribute("pageMaker", new PageDTO(cri, adminService.salesOrderTotal(cri)));
	    } else {
	        model.addAttribute("listCheck", "empty");
	    }

	    return "/admin/salesDayTally";
	}
	@GetMapping("/productProfit")
	public String productProfitTotal(Criteria cri, Model model) {
	    List<OrderItemDTO> allData = new ArrayList<>();
	    int pageNum = 1;

	    // 페이지 네이션을 이용하여 모든 데이터를 가져오기
	    while (true) {
	        cri.setPageNum(pageNum);
	        List<OrderItemDTO> list = adminService.getSalesList(cri);
	        if (list.isEmpty()) {
	            break;
	        }
	        allData.addAll(list);
	        pageNum++;
	    }

	    if (!allData.isEmpty()) {
	        // 중복된 상품 ID에 대한 판매 수량 합치기
	        Map<Integer, OrderItemDTO> aggregatedData = allData.stream()
	            .collect(Collectors.toMap(
	                OrderItemDTO::getProductId,
	                item -> {
	                    item.setTotalPrice(item.getProductCount() * item.getProductPrice());
	                    item.setSavePoint((int) (Math.floor(item.getTotalPrice() * 0.02)));
	                    item.setTotalSavePoint(item.getSavePoint() * item.getProductCount());
	                    System.out.println("Initial Margin value: " + item.getMargin()); // 초기 margin 값 로그
	                    return item;
	                },
	                (existing, replacement) -> {
	                    existing.setProductCount(existing.getProductCount() + replacement.getProductCount());
	                    existing.setTotalPrice(existing.getProductCount() * existing.getProductPrice());
	                    existing.setSavePoint((int) (Math.floor(existing.getTotalPrice() * 0.02)));
	                    existing.setTotalSavePoint(existing.getSavePoint() * existing.getProductCount());
	                    // 기존 값과 대체 값의 margin 값을 로그로 출력
	                    System.out.println("Aggregating: Existing Margin = " + existing.getMargin() + ", Replacement Margin = " + replacement.getMargin());
	                    return existing;
	                }
	            ));

	        List<OrderItemDTO> finalList = new ArrayList<>(aggregatedData.values());

	        // 모델에 데이터 추가
	        model.addAttribute("list", finalList);
	        model.addAttribute("pageMaker", new PageDTO(cri, adminService.salesOrderTotal(cri)));
	    } else {
	        model.addAttribute("listCheck", "empty");
	    }

	    return "/admin/productProfit";
	}
	@GetMapping("/profitToExcel")
	public void profitToExcel(HttpServletResponse response) throws IOException {
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
	    Sheet sheet = workbook.createSheet("품목별 이익현황");
	    
	    // Header creation
	    Row headerRow = sheet.createRow(0);
	    headerRow.createCell(0).setCellValue("상품ID");
	    headerRow.createCell(1).setCellValue("상품명");
	    headerRow.createCell(2).setCellValue("색상");
	    headerRow.createCell(3).setCellValue("용량");
	    headerRow.createCell(4).setCellValue("수량");
	    headerRow.createCell(5).setCellValue("단가");
	    headerRow.createCell(6).setCellValue("금액");
	    headerRow.createCell(7).setCellValue("단가 (원가)");
	    headerRow.createCell(8).setCellValue("금액 (원가)");
	    headerRow.createCell(9).setCellValue("단가 (이익)");
	    headerRow.createCell(10).setCellValue("금액 (이익)");
	    headerRow.createCell(11).setCellValue("이익율");

	    // 데이터 행 생성
	    int rowNum = 1;

	    // 총합 변수 초기화
	    double totalQuantity = 0;
	    double totalPrice = 0;
	    double totalCostPrice = 0;
	    double totalProfit = 0;

	    for (OrderItemDTO item : aggregatedData.values()) {
	        Row row = sheet.createRow(rowNum++);
	        double unitPrice = item.getProductPrice() * 0.9 * (1 - item.getProductDiscount());
	        double supplyValue = item.getProductCount() * item.getProductPrice() * (1 - item.getProductDiscount());
	        double vat = supplyValue * 0.1;
	        double amount = supplyValue * 0.9;

	        row.createCell(0).setCellValue(item.getProductId());
	        row.createCell(1).setCellValue(item.getProductName());
	        row.createCell(2).setCellValue(item.getColorName());
	        row.createCell(3).setCellValue(item.getGb());
	        row.createCell(4).setCellValue(item.getProductCount());
	        
	        double price = item.getProductPrice() * (1 - item.getProductDiscount());
	        row.createCell(5).setCellValue(Math.round(price)); // 반올림

	        double totalItemPrice = item.getProductCount() * price;
	        row.createCell(6).setCellValue(Math.round(totalItemPrice)); // 반올림

	        double costPrice = item.getProductPrice() * (1 - item.getProductDiscount()) -
	            (item.getProductPrice() * (1 - item.getProductDiscount()) * (item.getMargin() / 100));
	        row.createCell(7).setCellValue(Math.round(costPrice)); // 반올림

	        double totalItemCostPrice = totalItemPrice - (totalItemPrice * (item.getMargin() / 100));
	        row.createCell(8).setCellValue(Math.round(totalItemCostPrice)); // 반올림

	        double profitPerUnit = item.getProductPrice() * (1 - item.getProductDiscount()) * (item.getMargin() / 100);
	        row.createCell(9).setCellValue(Math.round(profitPerUnit)); // 반올림

	        double totalItemProfit = item.getProductCount() * profitPerUnit;
	        row.createCell(10).setCellValue(Math.round(totalItemProfit)); // 반올림

	        row.createCell(11).setCellValue(item.getMargin());

	        // 총합에 추가
	        totalQuantity += item.getProductCount();
	        totalPrice += totalItemPrice;
	        totalCostPrice += totalItemCostPrice;
	        totalProfit += totalItemProfit;
	    }

	    // 총합 행 생성
	    Row totalRow = sheet.createRow(rowNum);
	    totalRow.createCell(0).setCellValue("총합");
	    totalRow.createCell(4).setCellValue(Math.round(totalQuantity)); // 반올림
	    totalRow.createCell(6).setCellValue(Math.round(totalPrice)); // 반올림
	    totalRow.createCell(8).setCellValue(Math.round(totalCostPrice)); // 반올림
	    totalRow.createCell(10).setCellValue(Math.round(totalProfit)); // 반올림

	    // 응답 설정
	    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=productProfit_report.xlsx");

	    // 워크북을 응답에 쓰기
	    workbook.write(response.getOutputStream());
	    workbook.close();
	}




	@GetMapping("/productStock")
	public ModelAndView getProductStock() {
	    ModelAndView mav = new ModelAndView("/admin/productStock");

	    // Fetch all product information
	    List<ProductVO> productList = productService.getProductStock();
	    mav.addObject("list", productList);

	    // Check if the list is empty and add an appropriate flag
	    if (productList.isEmpty()) {
	        mav.addObject("listCheck", "empty");
	    } else {
	        mav.addObject("listCheck", "not_empty");
	    }

	    return mav;
	}
	@GetMapping("/stockToExcel")
    public ResponseEntity<InputStreamResource> exportToExcel() throws IOException {
        // Fetch product stock data
        List<ProductVO> productList = productService.getProductStock(); // 모든 상품 재고를 가져오는 메서드 필요

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Product Stock");

            // Header
            Row headerRow = sheet.createRow(0);
            String[] headers = {"제품코드", "제품명", "모델명", "카테고리", "색상", "용량GB", "제품수량", "상태코드"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Data rows
            int rowNum = 1;
            for (ProductVO product : productList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(product.getProductId());
                row.createCell(1).setCellValue(product.getProductName());
                row.createCell(2).setCellValue(product.getProductModelName());
                row.createCell(3).setCellValue(product.getCateName());
                row.createCell(4).setCellValue(product.getColorName());
                row.createCell(5).setCellValue(product.getGb());
                row.createCell(6).setCellValue(product.getProductStock());
                row.createCell(7).setCellValue(getProductStateText(product.getProductState()));
            }

            // Write to ByteArrayOutputStream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

            HttpHeaders headers1 = new HttpHeaders();
            headers1.add("Content-Disposition", "attachment; filename=product_stock.xlsx");

            return ResponseEntity
                    .ok()
                    .headers(headers1)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(in));
        }
    }

    // Converts product state code to text
    private String getProductStateText(int stateCode) {
        switch (stateCode) {
            case 0:
                return "정상";
            case 1:
                return "품절";
            case 2:
                return "입고 예정";
            case 3:
                return "단종";
            default:
                return "알 수 없음";
        }
    }
    @GetMapping("/operatingProfit")
    public String getOperatingProfit(Model model, Criteria cri) {
        // 월별 매출, 원가, 이익, 비용 초기화
        double[] monthlySales = new double[12];
        double[] monthlyCost = new double[12];
        double[] monthlyProfit = new double[12];
        double[] monthlyExpenses = new double[12];

        try {
            // 월별 매출 데이터 가져오기
            List<OrderItemDTO> salesList = adminService.getSalesListByMonth(cri);
            log.info("Sales list size: " + salesList.size());

            for (OrderItemDTO item : salesList) {
                if (item == null) {
                    log.warn("OrderItemDTO is null");
                    continue;  // null 체크
                }

                String orderMonth = item.getOrderMonth();
                if (orderMonth == null) {
                    log.warn("orderMonth is null for item: " + item);
                    continue;  // orderMonth null 체크
                }

                if (!isValidMonthFormat(orderMonth)) {
                    log.warn("Invalid orderMonth format for item: " + item);
                    continue;  // 형식이 잘못된 경우 건너뛰기
                }

                try {
                    int month = getMonthFromOrderMonth(orderMonth);
                    log.info("Processing month: " + month);
                    updateMonthlyData(month, item, monthlySales, monthlyCost, monthlyProfit);
                } catch (NumberFormatException e) {
                    log.warn("Failed to parse month for item: " + item, e);
                }
            }

            // 월별 관리 비용 데이터 가져오기
            List<CostsVO> costList = costsService.getMonthlyManagementCosts();
            for (CostsVO cost : costList) {
                int month = getMonthFromCostMonth(cost.getCostMonth());
                if (month >= 0 && month < 12) {
                    monthlyExpenses[month] += cost.getTotalCost();
                }
            }

            // 영업이익 계산
            double[] monthlyOperatingProfit = calculateMonthlyOperatingProfit(monthlyProfit, monthlyExpenses);

            // 모델에 데이터 추가
            model.addAttribute("monthlySales", monthlySales);
            model.addAttribute("monthlyCost", monthlyCost);
            model.addAttribute("monthlyProfit", monthlyProfit);
            model.addAttribute("monthlyExpenses", monthlyExpenses);
            model.addAttribute("monthlyOperatingProfit", monthlyOperatingProfit);
        } catch (Exception e) {
            log.error("Error while fetching operating profit data", e);
            model.addAttribute("errorMessage", "데이터를 가져오는 중 오류가 발생했습니다.");
        }

        // 뷰 이름 반환
        return "admin/operatingProfit"; // JSP 파일명
    }
    @GetMapping("/operatingprofitToExcel")
    public void exportOperatingProfitToExcel(HttpServletResponse response, Criteria cri) {
        // 월별 매출, 원가, 이익, 비용 초기화
        double[] monthlySales = new double[12];
        double[] monthlyCost = new double[12];
        double[] monthlyProfit = new double[12];
        double[] monthlyExpenses = new double[12];

        // 데이터 준비 로직 (이전과 유사)
        try {
            // 월별 매출 데이터 가져오기
            List<OrderItemDTO> salesList = adminService.getSalesListByMonth(cri);
            for (OrderItemDTO item : salesList) {
                if (item == null) {
                    log.warn("OrderItemDTO is null");
                    continue;  // null 체크
                }

                String orderMonth = item.getOrderMonth();
                if (orderMonth == null) {
                    log.warn("orderMonth is null for item: " + item);
                    continue;  // orderMonth null 체크
                }

                if (!isValidMonthFormat(orderMonth)) {
                    log.warn("Invalid orderMonth format for item: " + item);
                    continue;  // 형식이 잘못된 경우 건너뛰기
                }

                try {
                    int month = getMonthFromOrderMonth(orderMonth);
                    log.info("Processing month: " + month);
                    updateMonthlyData(month, item, monthlySales, monthlyCost, monthlyProfit);
                } catch (NumberFormatException e) {
                    log.warn("Failed to parse month for item: " + item, e);
                }
            }

            // 월별 관리 비용 데이터 가져오기
            List<CostsVO> costList = costsService.getMonthlyManagementCosts();
            for (CostsVO cost : costList) {
                int month = getMonthFromCostMonth(cost.getCostMonth());
                if (month >= 0 && month < 12) {
                    monthlyExpenses[month] += cost.getTotalCost();
                }
            }

            // 영업이익 계산
            double[] monthlyOperatingProfit = calculateMonthlyOperatingProfit(monthlyProfit, monthlyExpenses);

            // 엑셀 파일 생성
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Operating Profit");

            // 헤더 생성
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("항목");
            for (int i = 1; i <= 12; i++) {
                header.createCell(i).setCellValue(i + "월");
            }

            // 데이터 추가
            String[] categories = {"매출액", "매출원가", "매출총이익", "판매비와관리비", "영업이익"};
            double[][] data = {monthlySales, monthlyCost, monthlyProfit, monthlyExpenses, monthlyOperatingProfit};

            for (int i = 0; i < categories.length; i++) {
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(categories[i]);
                for (int j = 0; j < 12; j++) {
                    row.createCell(j + 1).setCellValue(data[i][j]);
                }
            }

            // Response 설정
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=operating_profit.xlsx");

            // 엑셀 파일을 response에 작성
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 월 형식 검증 메서드
    private boolean isValidMonthFormat(String orderMonth) {
        return orderMonth.matches("\\d{2}-\\d{2}");
    }

    // 월 계산 메서드
    private int getMonthFromOrderMonth(String orderMonth) {
        String[] monthParts = orderMonth.split("-");
        return Integer.parseInt(monthParts[1]) - 1; // 0-11로 변환
    }

    // 관리 비용 월 계산 메서드
    private int getMonthFromCostMonth(String costMonth) {
        String[] monthParts = costMonth.split("-");
        return Integer.parseInt(monthParts[1]) - 1; // 0-11로 변환
    }

    // 월별 데이터 업데이트 메서드
    private void updateMonthlyData(int month, OrderItemDTO item, double[] monthlySales, double[] monthlyCost, double[] monthlyProfit) {
        monthlySales[month] += item.getTotalSales() != null ? item.getTotalSales() : 0.0;
        monthlyCost[month] += item.getTotalCost() != null ? item.getTotalCost() : 0.0;
        monthlyProfit[month] += item.getTotalProfit() != null ? item.getTotalProfit() : 0.0;
    }

    // 영업이익 계산 메서드
    private double[] calculateMonthlyOperatingProfit(double[] monthlyProfit, double[] monthlyExpenses) {
        double[] monthlyOperatingProfit = new double[12];
        for (int i = 0; i < 12; i++) {
            monthlyOperatingProfit[i] = monthlyProfit[i] - monthlyExpenses[i];
        }
        return monthlyOperatingProfit;
    }


    @GetMapping("/revenueForecast")
    public String showRevenueForecast(Model model) {
    	// 2024년 월별 수익 가져오기
        List<OrderItemDTO> monthlyRevenue2024 = graphService.getMonthlyRevenueByCompany2024();

        // 2025년 예측 수익 계산
        Map<String, List<Double>> predictions2025 = graphService.predictMonthlyRevenueFor2025(monthlyRevenue2024);

        // Model에 데이터 추가
 //       model.addAttribute("monthlyRevenue", monthlyRevenue2024);
   //     model.addAttribute("predictions", predictions2025);


        // JSP 페이지 이름 반환
        return "admin/revenueForecast"; // revenueForecast.jsp
    }

    @GetMapping("/exportRevenueToExcel")
    public ResponseEntity<ByteArrayResource> exportRevenueToExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("2025 수익률 예측");

        // 헤더 생성
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("월");
        headerRow.createCell(1).setCellValue("애플");
        headerRow.createCell(2).setCellValue("삼성");

        // 데이터 생성 (임의의 데이터 사용)
        double[] applePredictions = {-2.15, -1.1, 0.9, 3.01, 5.08, 7.09, 9.1, 10.5, 12.7, 11, 13.95, 15.12};
        double[] samsungPredictions = {9.08, 8.07, 8.8, 7.04, 7, 6.88, 3.02, 2.8, 2.5, -1.25, -3.05, -3.99};

        // 데이터 추가
        for (int month = 0; month < 12; month++) {
            Row row = sheet.createRow(month + 1);
            row.createCell(0).setCellValue((month + 1) + "월");
            row.createCell(1).setCellValue(applePredictions[month]);
            row.createCell(2).setCellValue(samsungPredictions[month]);
        }

        // 파일 생성 및 다운로드
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            workbook.close();

            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=revenue_forecast.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
