package co.kr.shop.service;

import java.util.List;

import co.kr.shop.model.Criteria;
import co.kr.shop.model.ProductVO;

public interface ProductService {
	/* 상품 검색 */
	public List<ProductVO> getProductList(Criteria cri);
	
	/* 상품 총 갯수 */
	public int productGetTotal(Criteria cri);
	/* 상품 정보 */
	public ProductVO getProductInfo(int productId);
	/* 상품별 총 판매 갯수*/
	public int salesOrderTotal(Criteria cri);
	
	List<ProductVO> getProductStock();
	/* 상품 목록 */
    public List<ProductVO> productGetList(Criteria cri) throws Exception;
    public List<ProductVO> getTopSoldProducts();
}
