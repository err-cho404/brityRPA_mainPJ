package co.kr.shop.service;

import java.util.List;

import co.kr.shop.model.OrderCancelDTO;
import co.kr.shop.model.OrderDTO;
import co.kr.shop.model.OrderPageItemDTO;

public interface OrderService {
	/* 주문 정보 */
	public List<OrderPageItemDTO> getProductInfo(List<OrderPageItemDTO> orders);
	/* 주문 */
	public void  order(OrderDTO ord);
	/* 주문 취소 */
	public void orderCancle(OrderCancelDTO dto);
	
}
