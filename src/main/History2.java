package main;

public class History2 {

	String transactionid, product;
	int price = 0;
	int quantity = 0;
	
	public History2(String transactionid, String product, int price, int quantity) {
		this.transactionid = transactionid;
		this.product = product;
		this.price = price;
		this.quantity = quantity;
	}
	
	public String getTransactionid() {
		return transactionid;
	}
	
	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}
	
	public String getProduct() {
		return product;
	}
	
	public void setProduct(String product) {
		this.product = product;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String gettransactionid() {
		return transactionid;
	}
	
	public int gethasil() {
		return price * quantity;
	}
	
	public int getprice() {
		return price;
	}

}
