package co.kr.shop.mapper;

import java.util.List;

import co.kr.shop.model.OrderItemDTO;
import co.kr.shop.model.SalesDataDTO;

public interface GraphMapper {
	List<OrderItemDTO> getTop10SoldProducts();
	List<OrderItemDTO> getWeeklySales();
	List<OrderItemDTO> getMonthlySalesByCompany2024();
	List<SalesDataDTO> getAgePublisherSalesData();
	List<OrderItemDTO> getMonthlyRevenueByCompany2024();
}
