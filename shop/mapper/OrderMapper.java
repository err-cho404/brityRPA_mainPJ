package co.kr.shop.mapper;

import java.util.List;

import co.kr.shop.model.MemberVO;
import co.kr.shop.model.OrderDTO;
import co.kr.shop.model.OrderItemDTO;
import co.kr.shop.model.OrderPageItemDTO;
import co.kr.shop.model.ProductVO;

public interface OrderMapper {
	/* 주문 상품 정보 */	
	public OrderPageItemDTO getProductInfo(int productId);
	/* 주문 상품 정보(주문 처리) */	
	public OrderItemDTO getOrderInfo(int productId);
	/* 주문 테이블 등록 */
	public int enrollOrder(OrderDTO ord);
	/* 주문 아이템 테이블 등록 */
	public int enrollOrderItem(OrderItemDTO orid);
	/* 주문 금액 차감 */
	public int deductMoney(MemberVO member);
	/* 주문 재고 차감 */
	public int deductStock(ProductVO product);
	/* 주문 취소 */
	public int orderCancle(String orderId);
	
	/* 주문 상품 정보(주문취소) */
	public List<OrderItemDTO> getOrderItemInfo(String orderId);
	
	/* 주문 정보(주문취소) */
	public OrderDTO getOrder(String orderId);
}
