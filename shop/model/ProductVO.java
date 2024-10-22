package co.kr.shop.model;

import java.sql.Date;
import java.util.List;

public class ProductVO {
	/* 상품 id */
	private int productId;
	
	/* 상품 이름 */
	private String productName;
	
	/* 모델 이름 */
	private String productModelName;
	
	/* 색상 id */
	private int colorId;
	
	/* 색상 이름 */
	private String colorName;
	
	/* 출시일 */
	private String publeYear;
	
	/* 제조사 */
	private String publisher;
	
	/* 제품상태 코드 */
	private int productState;
	
	/* 카테고리 이름 */
	private String cateName;
	
	/* 용량 GB */
	private int gb;
	
	/* 상품 판매가 */
	private int productPrice;
	
	/* 상품 재고 */
	private int productStock;
	
	public int getGb() {
		return gb;
	}

	public void setGb(int gb) {
		this.gb = gb;
	}

	/* 상품 할인률(백분율) */
	private double productDiscount;
	
	/* 상품 소개 */
	private String productIntro;
	
	/* 상품 목차 */
	private String productContents;
	
	/* 등록 날짜 */
	private Date regDate;
	
	/* 수정 날짜 */
	private Date updateDate;
	
	private AttachImageVO imageVO;
	/* 이미지 정보 */
	private List<AttachImageVO> imageList;

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

	public String getProductModelName() {
		return productModelName;
	}

	public void setProductModelName(String productModelName) {
		this.productModelName = productModelName;
	}

	public int getColorId() {
		return colorId;
	}

	public void setColorId(int colorId) {
		this.colorId = colorId;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getPubleYear() {
		return publeYear;
	}

	public void setPubleYear(String publeYear) {
		this.publeYear = publeYear;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getProductState() {
		return productState;
	}

	public void setProductState(int productState) {
		this.productState = productState;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductStock() {
		return productStock;
	}

	public void setProductStock(int productStock) {
		this.productStock = productStock;
	}

	public double getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(double productDiscount) {
		this.productDiscount = productDiscount;
	}

	public String getProductIntro() {
		return productIntro;
	}

	public void setProductIntro(String productIntro) {
		this.productIntro = productIntro;
	}

	public String getProductContents() {
		return productContents;
	}

	public void setProductContents(String productContents) {
		this.productContents = productContents;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public List<AttachImageVO> getImageList() {
		return imageList;
	}

	public void setImageList(List<AttachImageVO> imageList) {
		this.imageList = imageList;
	}

	@Override
	public String toString() {
		return "ProductVO [productId=" + productId + ", productName=" + productName + ", productModelName="
				+ productModelName + ", colorId=" + colorId + ", colorName=" + colorName + ", publeYear=" + publeYear
				+ ", publisher=" + publisher + ", productState=" + productState + ", cateName=" + cateName + ", gb="
				+ gb + ", productPrice=" + productPrice + ", productStock=" + productStock + ", productDiscount="
				+ productDiscount + ", productIntro=" + productIntro + ", productContents=" + productContents
				+ ", regDate=" + regDate + ", updateDate=" + updateDate + ", imageVO=" + imageVO + ", imageList="
				+ imageList + "]";
	}

	
}
