package co.kr.shop.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.kr.shop.mapper.ColorMapper;
import co.kr.shop.model.ColorVO;
import co.kr.shop.model.Criteria;

@Service
public class ColorServiceImpl implements ColorService {

    private static final Logger log = LoggerFactory.getLogger(ColorServiceImpl.class);
	@Autowired
	ColorMapper colorMapper;

	@Override
    public void colorEnroll(ColorVO color) throws Exception {
        
		colorMapper.colorEnroll(color);
        
    }
	/* 색상 목록 */
    @Override
    public List<ColorVO> colorGetList(Criteria cri) throws Exception {
        
        return colorMapper.colorGetList(cri);
    }
    /* 색상 총 수 */
    @Override
    public int colorGetTotal(Criteria cri) throws Exception {
        log.info("(service)colorGetTotal()......." + cri);
        return colorMapper.colorGetTotal(cri);
    }
    /* 색상 상세 페이지 */
	@Override
	public ColorVO colorGetDetail(int colorId) throws Exception {
		log.info("colorGetDetail........" + colorId);
		return colorMapper.colorGetDetail(colorId);
	}	
	/* 색상 정보 수정 */
	@Override
	public int colorModify(ColorVO color) throws Exception {
		log.info("(service) colorModify........." + color);
		return colorMapper.colorModify(color);
	}
	/* 색상 정보 삭제 */
	@Override
	public int colorDelete(int colorId) {
		
		log.info("colorDelete..........");
		
		return colorMapper.colorDelete(colorId);
	}
}
