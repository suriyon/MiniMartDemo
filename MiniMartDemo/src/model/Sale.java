package model;

public class Sale {
	private String id;
	private String saleDate;
	private int totalPrice;
	
	public Sale() {
		super();
	}
	public Sale(String id, String saleDate, int totalPrice) {
		super();
		this.id = id;
		this.saleDate = saleDate;
		this.totalPrice = totalPrice;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
