package com.example.ordi2.DTO;

import java.util.UUID;

public class CommentDTO {
	private UUID commentId;
	private UUID postId;
	private UUID commentUserId;
    private String content;
    private String name;
    private String profile_URl;
    private String email;
	public UUID getCommentId() {
		return commentId;
	}
	public void setCommentId(UUID commentId) {
		this.commentId = commentId;
	}
	public UUID getPostId() {
		return postId;
	}
	public void setPostId(UUID postId) {
		this.postId = postId;
	}
	public UUID getCommentUserId() {
		return commentUserId;
	}
	public void setCommentUserId(UUID commentUserId) {
		this.commentUserId = commentUserId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProfile_URl() {
		return profile_URl;
	}
	public void setProfile_URl(String profile_URl) {
		this.profile_URl = profile_URl;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    
    
}
