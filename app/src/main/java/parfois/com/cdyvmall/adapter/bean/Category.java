package parfois.com.cdyvmall.adapter.bean;

import java.util.List;

public class Category {
	private String name;
	private List<Product> list;
	
	public String toString() {
		return this.name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getList() {
		return list;
	}

	public void setList(List<Product> list) {
		this.list = list;
	}

}
