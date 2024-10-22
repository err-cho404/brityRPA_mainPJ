package co.kr.shop.mapper;

import java.util.List;

import co.kr.shop.model.AttachImageVO;

public interface AttachMapper {
	/* 이미지 데이터 반환 */
	public List<AttachImageVO> getAttachList(int productId);
}
