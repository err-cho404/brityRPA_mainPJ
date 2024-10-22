package co.kr.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.kr.shop.mapper.CostsMapper;
import co.kr.shop.model.CostsVO;
import co.kr.shop.model.Criteria;
@Service
public class CostsServiceImpl implements CostsService {
	@Autowired
	private CostsMapper costsMapper;	
	/* 지출 등록 */
	@Override
	public void costsEnroll(CostsVO costs) {
		costsMapper.costsEnroll(costs);		
	}
	/* 지출 리스트 */
	@Override
	public List<CostsVO> costsGetList(Criteria cri) {
		return costsMapper.costsGetList(cri);
	}

	/* 지출 총 갯수 */
	public int costsGetTotal(Criteria cri) {
		return costsMapper.costsGetTotal(cri);
	}		
	/* 지출 조회 페이지 */
	@Override
	public CostsVO costsGetDetail(int costId) {
		
		return costsMapper.costsGetDetail(costId);
	}	
	/* 지출 정보 수정 */
	@Transactional
	@Override
	public int costsModify(CostsVO vo) {
		int result = costsMapper.costsModify(vo);
		
		return result;
		
	}
	/* 지출 정보 삭제 */
	@Override
	public int costsDelete(int costId) {
		
		return costsMapper.costsDelete(costId);
	}
	@Override
    public List<CostsVO> getMonthlyManagementCosts() {
        return costsMapper.getMonthlyManagementCosts();
    }
}
