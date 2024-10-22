package co.kr.shop.service;

import java.util.List;
import java.util.Map;

import co.kr.shop.model.OrderItemDTO;
import co.kr.shop.model.SalesDataDTO;

public interface GraphService {
	List<OrderItemDTO> getTop10SoldProducts();
	List<OrderItemDTO> getWeeklySales();
	List<OrderItemDTO> getMonthlySalesByCompany2024();
	List<SalesDataDTO>getAgePublisherSalesData();
	List<OrderItemDTO> getMonthlyRevenueByCompany2024();
	 Map<String, Double> predictRevenueFor2025(List<OrderItemDTO> monthlyRevenue);
	Map<String, List<Double>> predictMonthlyRevenueFor2025(List<OrderItemDTO> monthlyRevenue);
}
