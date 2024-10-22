package co.kr.shop.model;

import java.sql.Date;

public class OrderItemDTO {
	private Double totalSales;
	private Double totalCost;
	private Double totalProfit;
	 private Double totalRevenue; // 총 수익 필드 추가
	 private double revenue;    // 예측 수익
	public double getRevenue() {
		return revenue;
	}
	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}
	public Double getTotalRevenue() {
		return totalRevenue;
	}
	public int getOrderMonthAsInt() {
        if (this.orderMonth == null || this.orderMonth.isEmpty()) {
            return 0; // 기본값
        }
        return Integer.parseInt(this.orderMonth); // 문자열을 정수로 변환
    }
	public void setTotalRevenue(Double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	// Getter와 Setter 추가
	public Double getTotalSales() {
	    return totalSales;
	}

	public void setTotalSales(double totalSales) {
	    this.totalSales = totalSales;
	}

	public Double getTotalCost() {
	    return totalCost;
	}

	public void setTotalCost(double totalCost) {
	    this.totalCost = totalCost;
	}

	public Double getTotalProfit() {
	    return totalProfit;
	}

	public void setTotalProfit(double totalProfit) {
	    this.totalProfit = totalProfit;
	}
	public double getCost() {
		return cost;
	}


	public void setCost(double cost) {
		this.cost = cost;
	}


	public double getProfit() {
		return profit;
	}


	public void setProfit(double profit) {
		this.profit = profit;
	}


	public void setOrderMonth(String orderMonth) {
		this.orderMonth = orderMonth;
	}


	 /* 월별 주문 정보를 위한 추가 필드 */
    private String orderMonth;
    private String orderMonth2;
    public String getOrderMonth2() {
		return orderMonth2;
	}

	public void setOrderMonth2(String orderMonth2) {
		this.orderMonth2 = orderMonth2;
	}


	/* 매출 원가 */
    private double cost;

    /* 매출 총 이익 */
    private double profit;
	/* 주문 번호 */
	private String orderId;
	private String publisher;
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setTotalSales(Double totalSales) {
		this.totalSales = totalSales;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public void setTotalProfit(Double totalProfit) {
		this.totalProfit = totalProfit;
	}


	/* 상품 번호 */
    private int productId;
    
	/* 주문 수량 */
    private int productCount;
    private String productName; // 이 속성을 추가해야 합니다.
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/* 주문 날짜 추가 */
    private Date orderDate; // 추가된 부분입니다.
	/* pdt_orderItem 기본키 */
    private int orderItemId;
    
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	/* 상품 한 개 가격 */
    private int productPrice;
    
	/* 상품 할인 율 */
    private double productDiscount;
    
    private double margin;
    
	public double getMargin() {
		return margin;
	}
	public void setMargin(double margin) {
		this.margin = margin;
	}

	/* 상품 한개 구매 시 획득 포인트 */
    private int savePoint;
    /*용량 기가바이트*/
    private int gb;
    private String colorName;
    private int colorId;
    
	public int getGb() {
		return gb;
	}
	public void setGb(int gb) {
		this.gb = gb;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public int getColorId() {
		return colorId;
	}
	public void setColorId(int colorId) {
		this.colorId = colorId;
	}

	/* 할인 적용된 가격 */
    private int salePrice;
    
	/* 총 가격(할인 적용된 가격 * 주문 수량) */
    private int totalPrice;
    
	/* 총 획득 포인트(상품 한개 구매 시 획득 포인트 * 수량) */
    private int totalSavePoint;
    
    public String getOrderMonth() {
        if (this.orderDate == null) {
            return null; // 또는 적절한 기본값을 반환
        }

        // 'YY-MM' 형식으로 날짜를 반환
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(this.orderDate);
        
        int year = calendar.get(java.util.Calendar.YEAR) % 100; // 마지막 두 자리만 추출
        int month = calendar.get(java.util.Calendar.MONTH) + 1; // 0 기반에서 1 기반으로 변환

        return String.format("%02d-%02d", year, month); // YY-MM 형식
    }



    public void initSaleTotal() {
        this.salePrice = (int) (this.productPrice * (1 - this.productDiscount));
        this.totalPrice = this.salePrice * this.productCount;
        this.savePoint = (int) (Math.floor(this.salePrice * 0.02));
        this.totalSavePoint = this.savePoint * this.productCount;

        // 매출 원가 및 매출 총 이익 계산
        this.cost = this.productPrice * (this.margin / 100) * this.productCount; // 수정된 매출 원가 계산
        this.profit = this.totalPrice - this.cost; // 총 이익 계산
    }
	@Override
	public String toString() {
		return "OrderItemDTO [totalSales=" + totalSales + ", totalCost=" + totalCost + ", totalProfit=" + totalProfit
				+ ", orderMonth=" + orderMonth + ", cost=" + cost + ", profit=" + profit + ", orderId=" + orderId
				+ ", productId=" + productId + ", productCount=" + productCount + ", productName=" + productName
				+ ", orderDate=" + orderDate + ", orderItemId=" + orderItemId + ", productPrice=" + productPrice
				+ ", productDiscount=" + productDiscount + ", margin=" + margin + ", savePoint=" + savePoint + ", gb="
				+ gb + ", colorName=" + colorName + ", colorId=" + colorId + ", salePrice=" + salePrice
				+ ", totalPrice=" + totalPrice + ", totalSavePoint=" + totalSavePoint + "]";
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public double getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(double productDiscount) {
		this.productDiscount = productDiscount;
	}

	public int getSavePoint() {
		return savePoint;
	}

	public void setSavePoint(int savePoint) {
		this.savePoint = savePoint;
	}

	public int getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getTotalSavePoint() {
		return totalSavePoint;
	}

	public void setTotalSavePoint(int totalSavePoint) {
		this.totalSavePoint = totalSavePoint;
	}
}
