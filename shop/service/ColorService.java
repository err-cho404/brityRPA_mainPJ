package co.kr.shop.service;

import java.util.List;

import co.kr.shop.model.ColorVO;
import co.kr.shop.model.Criteria;

public interface ColorService {
	/* 색상 등록 */
    public void colorEnroll(ColorVO color) throws Exception;
    /* 색상 목록 */
    public List<ColorVO> colorGetList(Criteria cri) throws Exception;
    /* 색상 총 수 */
    public int colorGetTotal(Criteria cri) throws Exception;
    /* 색상 상세 페이지 */
	public ColorVO colorGetDetail(int colorId) throws Exception;
	/* 색상 정보 수정 */
	public int colorModify(ColorVO color) throws Exception;
	/* 색상 정보 삭제 */
	public int colorDelete(int colorId);
}
