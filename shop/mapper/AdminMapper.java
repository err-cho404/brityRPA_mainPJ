package co.kr.shop.mapper;

import java.util.List;

import co.kr.shop.model.AttachImageVO;
import co.kr.shop.model.Criteria;
import co.kr.shop.model.OrderDTO;
import co.kr.shop.model.OrderItemDTO;
import co.kr.shop.model.ProductVO;

public interface AdminMapper {
	/* 상품 등록 */
	public void productEnroll(ProductVO product);
	/* 상품 리스트 */
	public List<ProductVO> productGetList(Criteria cri);
	
	/* 상품 총 개수 */
	public int productGetTotal(Criteria cri);
	/* 상품 조회 페이지 */
	public ProductVO productGetDetail(int productId);
	/* 상품 수정 */
	public int productModify(ProductVO vo);
	/* 상품 정보 삭제 */
	public int productDelete(int productId);
	/* 이미지 등록 */
	public void imageEnroll(AttachImageVO vo);
	/* 지정 상품 이미지 전체 삭제 */
	public void deleteImageAll(int productId);
	/* 지정 상품 이미지 정보 얻기 */
	public List<AttachImageVO> getAttachInfo(int productId);
	/* 주문 상품 리스트 */
	public List<OrderDTO> getOrderList(Criteria cri);	
	/* 주문 총 갯수 */
	public int getOrderTotal(Criteria cri);
	/* 상품별 판매 리스트 */
	public List<OrderItemDTO> getSalesList(Criteria cri);	
	/* 상품별 총 판매 갯수*/
	public int salesOrderTotal(Criteria cri);
	public List<OrderItemDTO> getSalesListByMonth(Criteria cri);
	public List<OrderItemDTO> getDaySalesList(Criteria cri);	

}
