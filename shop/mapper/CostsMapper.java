package co.kr.shop.mapper;

import java.util.List;

import co.kr.shop.model.CostsVO;
import co.kr.shop.model.Criteria;

public interface CostsMapper {
	/* 지출 등록 */
	public void costsEnroll(CostsVO costs);
	/* 지출 리스트 */
	public List<CostsVO> costsGetList(Criteria cri);
	
	/* 지출 총 개수 */
	public int costsGetTotal(Criteria cri);
	/* 지출 조회 페이지 */
	public CostsVO costsGetDetail(int costId);
	/* 지출 수정 */
	public int costsModify(CostsVO vo);
	/* 지출 정보 삭제 */
	public int costsDelete(int costId);
	List<CostsVO> getMonthlyManagementCosts();
}
