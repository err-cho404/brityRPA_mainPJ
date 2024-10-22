package co.kr.shop.model;

public class ColorVO {
	/* 색상 아이디 */
    private int colorId;
    
    /* 색상 이름 */
    private String colorName;

	@Override
	public String toString() {
		return "ColorVO [colorId=" + colorId + ", colorName=" + colorName + "]";
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


}
