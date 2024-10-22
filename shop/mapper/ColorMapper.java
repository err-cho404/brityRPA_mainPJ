package co.kr.shop.mapper;

import java.util.List;

import co.kr.shop.model.ColorVO;
import co.kr.shop.model.Criteria;

public interface ColorMapper {
	/* 색상 등록 */
    public void colorEnroll(ColorVO color);
    /* 색상 목록*/
    public List<ColorVO> colorGetList(Criteria cri);
    /* 색상 총 수 */
    public int colorGetTotal(Criteria cri);
    /* 색상 상세 */
	public ColorVO colorGetDetail(int colorId);
	/* 색상 정보 수정 */
	public int colorModify(ColorVO color);
	/* 작가 정보 삭제 */
	public int colorDelete(int colorId);
}
