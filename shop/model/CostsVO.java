package co.kr.shop.model;

public class CostsVO {
    private int costId;            // 관리 비용 ID
    private String costType;       // 비용 유형
    private String description;    // 비용 설명
    private double price;          // 비용 금액
    private String costDate;       // 비용 발생 날짜
    private String paymentMethod;  // 비용 지불 방법 (예: 카드, 현금, 송금 등)
    private String costMonth;  // 비용 지불 방법 (예: 카드, 현금, 송금 등)
    public String getCostMonth() {
		return costMonth;
	}

	public void setCostMonth(String costMonth) {
		this.costMonth = costMonth;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	private double totalCost;
    // 기본 생성자
    public CostsVO() {}

    // 매개변수 있는 생성자
    public CostsVO(int costId, String costType, String description, double price, String costDate, String paymentMethod) {
        this.costId = costId;
        this.costType = costType;
        this.description = description;
        this.price = price;
        this.costDate = costDate;
        this.paymentMethod = paymentMethod;
    }

    // Getter 및 Setter 메소드
    public int getCostId() {
        return costId;
    }

    public void setCostId(int costId) {
        this.costId = costId;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCostDate() {
        return costDate;
    }

    public void setCostDate(String costDate) {
        this.costDate = costDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "CostsVO [costId=" + costId + ", costType=" + costType + ", description=" + description + ", price=" 
               + price + ", costDate=" + costDate + ", paymentMethod=" + paymentMethod + "]";
    }
}
