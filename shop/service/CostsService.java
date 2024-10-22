package co.kr.shop.service;

import java.util.List;

import co.kr.shop.model.CostsVO;
import co.kr.shop.model.Criteria;

public interface CostsService {
	/* 상품 등록 */
	public void costsEnroll(CostsVO costs);	
	/* 상품 리스트 */
	public List<CostsVO> costsGetList(Criteria cri);
	
	/* 상품 총 개수 */
	public int costsGetTotal(Criteria cri);
	/* 상품 조회 페이지 */
	public CostsVO costsGetDetail(int costId);
	/* 상품 수정 */
	public int costsModify(CostsVO vo);
	/* 상품 정보 삭제 */
	public int costsDelete(int costId);
	
	List<CostsVO> getMonthlyManagementCosts();
	
}
