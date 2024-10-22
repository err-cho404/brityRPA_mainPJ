package co.kr.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.kr.shop.mapper.AdminMapper;
import co.kr.shop.model.AttachImageVO;
import co.kr.shop.model.Criteria;
import co.kr.shop.model.OrderDTO;
import co.kr.shop.model.OrderItemDTO;
import co.kr.shop.model.ProductVO;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class AdminServiceImpl implements AdminService  {
	@Autowired
	private AdminMapper adminMapper;	
	/* 상품 등록 */
	@Override
	public void productEnroll(ProductVO product) {
		adminMapper.productEnroll(product);	
		if(product.getImageList() == null || product.getImageList().size() <= 0) {
			return;
		}
				// 일반적 for문
	for(int i = 0; i < product.getImageList().size(); i++) {
		AttachImageVO VO = product.getImageList().get(i);
		VO.setProductId(product.getProductId());
		adminMapper.imageEnroll(VO);
	 }
		
	}
	/* 상품 리스트 */
	@Override
	public List<ProductVO> productGetList(Criteria cri) {
		return adminMapper.productGetList(cri);
	}

	/* 상품 총 갯수 */
	public int productGetTotal(Criteria cri) {
		return adminMapper.productGetTotal(cri);
	}		
	/* 상품 조회 페이지 */
	@Override
	public ProductVO productGetDetail(int productId) {
		
		return adminMapper.productGetDetail(productId);
	}	
	/* 상품 정보 수정 */
	@Transactional
	@Override
	public int productModify(ProductVO vo) {
int result = adminMapper.productModify(vo);
		
		if(result == 1 && vo.getImageList() != null && vo.getImageList().size() > 0) {
			
			adminMapper.deleteImageAll(vo.getProductId());
			
			vo.getImageList().forEach(attach -> {
				
				attach.setProductId(vo.getProductId());
				adminMapper.imageEnroll(attach);
				
			});
			
		}
		
		return result;
		
	}
	/* 상품 정보 삭제 */
	@Override
	public int productDelete(int productId) {
		
		return adminMapper.productDelete(productId);
	}	
	/* 지정 상품 이미지 정보 얻기 */
	@Override
	@Transactional 
	public List<AttachImageVO> getAttachInfo(int productId) {
		
		log.info("getAttachInfo........");
		adminMapper.deleteImageAll(productId);
		return adminMapper.getAttachInfo(productId);
	}
	/* 주문 상품 리스트 */
	@Override
	public List<OrderDTO> getOrderList(Criteria cri) {
		return adminMapper.getOrderList(cri);
	}
	
	/* 주문 총 갯수 */
	@Override
	public int getOrderTotal(Criteria cri) {
		return adminMapper.getOrderTotal(cri);
	}
	@Override
	public List<OrderItemDTO> getSalesList(Criteria cri) {
	    // 페이지 번호를 설정하고 데이터를 가져옵니다.
		log.info(adminMapper.getSalesList(cri));
	    return adminMapper.getSalesList(cri);
	}

	@Override
	public int salesOrderTotal(Criteria cri) {
	    // 총 페이지 수를 계산합니다.
	    return adminMapper.salesOrderTotal(cri);
	}
	@Override
	public List<OrderItemDTO> getSalesListByMonth(Criteria cri) {
	    // adminMapper를 통해 매출 목록을 가져와서 반환
		List<OrderItemDTO> salesList = adminMapper.getSalesListByMonth(cri);
		System.out.println("Sales List: " + salesList);
	    return adminMapper.getSalesListByMonth(cri);
	}
	@Override
	public List<OrderItemDTO> getDaySalesList(Criteria cri) {
	    // 페이지 번호를 설정하고 데이터를 가져옵니다.
		log.info(adminMapper.getDaySalesList(cri));
	    return adminMapper.getDaySalesList(cri);
	}
	
}

	
