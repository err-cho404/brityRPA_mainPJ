package co.kr.shop.model;

import java.util.List;

public class SalesDataDTO {
    private String gender;
    private String ageGroup;
    private List<OrderItemDTO> orderItems;  // 해당 연령대, 성별의 판매 상품 목록
    private int totalCount;  // 총 판매 수
    private String publisher;
    private int totalQuantity;
    public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public int getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public int getGb() {
		return gb;
	}
	public void setGb(int gb) {
		this.gb = gb;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	private int gb;
    private int productId;
    private String productName;
    private String colorName;
    // 연령대별 판매 수량
    private int countUnder10;
    private int countTeen;
    private int countTwenties;
    private int countThirties;
    private int countForties;
    private int countFifties;
    private int countSixties;
    private int countSeventies;
    private int countEighties;	
    private int countNineties;
    private int countHundred;
	public String getGender() {
		return gender;
	}
	@Override
	public String toString() {
		return "SalesDataDTO [gender=" + gender + ", ageGroup=" + ageGroup + ", orderItems=" + orderItems
				+ ", totalCount=" + totalCount + ", countUnder10=" + countUnder10 + ", countTeen=" + countTeen
				+ ", countTwenties=" + countTwenties + ", countThirties=" + countThirties + ", countForties="
				+ countForties + ", countFifties=" + countFifties + ", countSixties=" + countSixties
				+ ", countSeventies=" + countSeventies + ", countEighties=" + countEighties + ", countNineties="
				+ countNineties + ", countHundred=" + countHundred + "]";
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAgeGroup() {
		return ageGroup;
	}
	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}
	public List<OrderItemDTO> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getCountUnder10() {
		return countUnder10;
	}
	public void setCountUnder10(int countUnder10) {
		this.countUnder10 = countUnder10;
	}
	public int getCountTeen() {
		return countTeen;
	}
	public void setCountTeen(int countTeen) {
		this.countTeen = countTeen;
	}
	public int getCountTwenties() {
		return countTwenties;
	}
	public void setCountTwenties(int countTwenties) {
		this.countTwenties = countTwenties;
	}
	public int getCountThirties() {
		return countThirties;
	}
	public void setCountThirties(int countThirties) {
		this.countThirties = countThirties;
	}
	public int getCountForties() {
		return countForties;
	}
	public void setCountForties(int countForties) {
		this.countForties = countForties;
	}
	public int getCountFifties() {
		return countFifties;
	}
	public void setCountFifties(int countFifties) {
		this.countFifties = countFifties;
	}
	public int getCountSixties() {
		return countSixties;
	}
	public void setCountSixties(int countSixties) {
		this.countSixties = countSixties;
	}
	public int getCountSeventies() {
		return countSeventies;
	}
	public void setCountSeventies(int countSeventies) {
		this.countSeventies = countSeventies;
	}
	public int getCountEighties() {
		return countEighties;
	}
	public void setCountEighties(int countEighties) {
		this.countEighties = countEighties;
	}
	public int getCountNineties() {
		return countNineties;
	}
	public void setCountNineties(int countNineties) {
		this.countNineties = countNineties;
	}
	public int getCountHundred() {
		return countHundred;
	}
	public void setCountHundred(int countHundred) {
		this.countHundred = countHundred;
	}

    // Getters and Setters
    // ...
}
