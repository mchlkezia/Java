package main;

import java.sql.Date;

public class History1 {
	
	private String transactionId;
	private String email;
	private Date date;
	
	public History1(String transactionId, String email, Date date) {
		this.transactionId = transactionId;
		this.email = email;
		this.date = date;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
}
