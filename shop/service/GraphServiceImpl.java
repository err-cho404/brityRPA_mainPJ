package co.kr.shop.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.kr.shop.mapper.GraphMapper;
import co.kr.shop.model.OrderItemDTO;
import co.kr.shop.model.SalesDataDTO;
@Service
public class GraphServiceImpl implements GraphService {
	

    @Autowired
    private GraphMapper graphMapper;

    @Override
    public List<OrderItemDTO> getTop10SoldProducts() {
        return graphMapper.getTop10SoldProducts();
    }

	@Override
	public List<OrderItemDTO> getWeeklySales() {
		return graphMapper.getWeeklySales();
	}

	@Override
	public List<OrderItemDTO> getMonthlySalesByCompany2024() {
		return graphMapper.getMonthlySalesByCompany2024();
	}

	@Override
	public List<SalesDataDTO> getAgePublisherSalesData() {
		return graphMapper.getAgePublisherSalesData();
	}
	@Override
    public List<OrderItemDTO> getMonthlyRevenueByCompany2024() {
        return graphMapper.getMonthlyRevenueByCompany2024();
    }
	public Map<String, Double> predictRevenueFor2025(List<OrderItemDTO> monthlyRevenue) {
	    Map<String, List<Double>> companyRevenue = new HashMap<>();

	    // 월별 수익을 회사별로 분류
	    for (OrderItemDTO revenue : monthlyRevenue) {
	        String publisher = revenue.getPublisher();
	        double totalRevenue = revenue.getTotalRevenue(); // 수정된 부분
	        int month = revenue.getOrderMonthAsInt(); // 월 정보가 필요하면 추가하세요.

	        companyRevenue.computeIfAbsent(publisher, k -> new ArrayList<>()).add(totalRevenue);
	    }

	    // 회귀 예측 결과 저장
	    Map<String, Double> predictions = new HashMap<>();

	    // 각 회사에 대해 선형 회귀 수행
	    for (Map.Entry<String, List<Double>> entry : companyRevenue.entrySet()) {
	        String publisher = entry.getKey();
	        List<Double> revenues = entry.getValue();

	        double m = calculateSlope(revenues);
	        double b = calculateIntercept(revenues, m);
	        double prediction = m * 13 + b; // 2025년 예측 (12월의 다음)

	        predictions.put(publisher, prediction);
	    }

	    return predictions;
	}
	public Map<String, List<Double>> predictMonthlyRevenueFor2025(List<OrderItemDTO> monthlyRevenue) {
	    Map<String, List<Double>> companyMonthlyRevenue = new HashMap<>();

	    // 월별 수익을 회사별로 분류
	    for (OrderItemDTO revenue : monthlyRevenue) {
	        String publisher = revenue.getPublisher();
	        double totalRevenue = revenue.getTotalRevenue();
	        int month = revenue.getOrderMonthAsInt();

	        companyMonthlyRevenue.computeIfAbsent(publisher, k -> new ArrayList<>(Collections.nCopies(12, 0.0)));
	        companyMonthlyRevenue.get(publisher).set(month - 1, totalRevenue); // 인덱스는 0부터 시작하므로 month-1
	    }

	    // 회귀 예측 결과 저장
	    Map<String, List<Double>> predictions = new HashMap<>();

	    // 각 회사에 대해 선형 회귀 수행
	    for (Map.Entry<String, List<Double>> entry : companyMonthlyRevenue.entrySet()) {
	        String publisher = entry.getKey();
	        List<Double> revenues = entry.getValue();

	        List<Double> monthlyPredictions = new ArrayList<>();
	        for (int i = 0; i < 12; i++) {
	            double m = calculateSlope(revenues);
	            double b = calculateIntercept(revenues, m);
	            double prediction = m * (i + 1) + b; // i+1은 1부터 12까지
	            monthlyPredictions.add(Math.round(prediction * 100.0) / 100.0); // 두 자리 소수로 반올림
	        }
	        predictions.put(publisher, monthlyPredictions);
	    }

	    return predictions;
	}
    private double calculateSlope(List<Double> revenues) {
        double n = revenues.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < revenues.size(); i++) {
            sumX += (i + 1); // 월 (1, 2, ..., 12)
            sumY += revenues.get(i);
            sumXY += (i + 1) * revenues.get(i);
            sumX2 += Math.pow((i + 1), 2);
        }

        return (n * sumXY - sumX * sumY) / (n * sumX2 - Math.pow(sumX, 2));
    }

    private double calculateIntercept(List<Double> revenues, double slope) {
        double n = revenues.size();
        double sumX = 0, sumY = 0;

        for (int i = 0; i < revenues.size(); i++) {
            sumX += (i + 1); // 월 (1, 2, ..., 12)
            sumY += revenues.get(i);
        }

        return (sumY - slope * sumX) / n;
    }
}
