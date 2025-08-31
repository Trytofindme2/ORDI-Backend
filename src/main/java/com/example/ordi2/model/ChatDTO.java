package com.example.ordi2.model;

public class ChatDTO {
	
	private String senderEmail;
	private String receiverEmail;
	private String textcontent;
	public ChatDTO(String senderEmail, String receiverEmail, String textcontent) {
		super();
		this.senderEmail = senderEmail;
		this.receiverEmail = receiverEmail;
		this.textcontent = textcontent;
	}
	public String getSenderEmail() {
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	public String getReceiverEmail() {
		return receiverEmail;
	}
	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}
	public String getTextcontent() {
		return textcontent;
	}
	public void setTextcontent(String textcontent) {
		this.textcontent = textcontent;
	}
	

}
