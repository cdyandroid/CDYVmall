package parfois.com.cdyvmall.adapter.bean;

import parfois.com.cdyvmall.R;

public class SGoods {
	private String name;
	private String picUrl;
	private String prdId;
	private int prdStatus;
	private String price;
	private String promotionWord;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getPrdId() {
		return prdId;
	}

	public void setPrdId(String prdId) {
		this.prdId = "http://mw.vmall.com/product/" + prdId + ".html";
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = "￥" + price;
	}

	public String getPromotionWord() {
		return promotionWord;
	}

	public void setPromotionWord(String promotionWord) {
		this.promotionWord = promotionWord;
	}

}
