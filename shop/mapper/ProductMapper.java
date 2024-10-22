package co.kr.shop.mapper;

import java.util.List;

import co.kr.shop.model.Criteria;
import co.kr.shop.model.ProductVO;

public interface ProductMapper {
	/* 상품 검색 */
	public List<ProductVO> getProductList(Criteria cri);
	
	/* 상품 총 갯수 */
	public int productGetTotal(Criteria cri);
	/* 상품 정보 */
	public ProductVO getProductInfo(int productId);
	
	public List<ProductVO> getProductStock();
	/* 상품 목록*/
    public List<ProductVO> productGetList(Criteria cri);
    public List<ProductVO> getTopSoldProducts();
}
