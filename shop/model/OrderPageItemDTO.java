package co.kr.shop.model;

import java.util.List;

public class OrderPageItemDTO {
	/* 뷰로부터 전달받을 값 */
    private int productId;
    
    private int productCount;
    
	/* DB로부터 꺼내올 값 */
    private String productName;
    
    private int productPrice;
    
    private double productDiscount;
    
	/* 만들어 낼 값 */
    private int salePrice;
    /* 상품 이미지 */
	private List<AttachImageVO> imageList;
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

	public List<AttachImageVO> getImageList() {
		return imageList;
	}

	public void setImageList(List<AttachImageVO> imageList) {
		this.imageList = imageList;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}

	private int totalPrice;
    
    private int point;
    
    private int totalPoint;
    public void initSaleTotal() {
		this.salePrice = (int) (this.productPrice * (1-this.productDiscount));
		this.totalPrice = this.salePrice*this.productCount;
		this.point = (int)(Math.floor(this.salePrice*0.02));
		this.totalPoint =this.point * this.productCount;
	}

	@Override
	public String toString() {
		return "OrderPageItemDTO [productId=" + productId + ", productCount=" + productCount + ", productName="
				+ productName + ", productPrice=" + productPrice + ", productDiscount=" + productDiscount
				+ ", salePrice=" + salePrice + ", imageList=" + imageList + ", totalPrice=" + totalPrice + ", point="
				+ point + ", totalPoint=" + totalPoint + "]";
	}
}
