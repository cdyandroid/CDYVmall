package parfois.com.cdyvmall.adapter.bean;

import parfois.com.cdyvmall.R;

public class Goods {
	private String prdDescription;
	private String prdName;
	private String prdPicUrl;
	private int prdStatus;
	private String prdUnitPrice;
	private int isDisplayPrice;
	private String prdId;

	// private String skuId;

	public String getPrdDescription() {
		return prdDescription;
	}

	public String getPrdId() {
		return prdId;
	}

	public void setPrdId(String prdId) {
		this.prdId = "http://mw.vmall.com/product/" + prdId + ".html";
	}

	public int getIsDisplayPrice() {
		return isDisplayPrice;
	}

	public void setIsDisplayPrice(int isDisplayPrice) {
		this.isDisplayPrice = isDisplayPrice;
	}

	public void setPrdDescription(String prdDescription) {
		this.prdDescription = prdDescription;
	}

	public String getPrdName() {
		return prdName;
	}

	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}

	public String getPrdPicUrl() {
		return prdPicUrl;
	}

	public void setPrdPicUrl(String prdPicUrl) {
		this.prdPicUrl = prdPicUrl;
	}

	public int getPrdStatus() {
		return prdStatus;
	}

	public void setPrdStatus(int prdStatus) {
		if (prdStatus == 1) {
			this.prdStatus = R.drawable.status_hot_sale;
		} else if (prdStatus == 5) {
			this.prdStatus = R.drawable.status_first;
		} else if (prdStatus == 2) {
			this.prdStatus = R.drawable.status_new;
		} else {
			this.prdStatus = -1;
		}
	}

	public String getPrdUnitPrice() {
		return prdUnitPrice;
	}

	public void setPrdUnitPrice(String prdUnitPrice) {
		this.prdUnitPrice = "￥" + prdUnitPrice;
	}

}
