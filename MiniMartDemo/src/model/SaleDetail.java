package model;

public class SaleDetail {
	private String saleId;
	private String productId;
	private String productName;
	private int amount;
	private int price;
	
	public String getSaleId() {
		return saleId;
	}
	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public SaleDetail(String saleId, String productId, String productName, int amount, int price) {
		super();
		this.saleId = saleId;
		this.productId = productId;
		this.productName = productName;
		this.amount = amount;
		this.price = price;
	}
	public SaleDetail() {
		super();
	}
		
}
