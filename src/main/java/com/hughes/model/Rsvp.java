package com.hughes.model;

import java.util.Date;

public class Rsvp {

	private int rsvpId;
	private int eventId;
	private String userId;
	private Date createdAt;
	private Date updatedAt;

	// Getters and setters

	public int getRsvpId() {
		return rsvpId;
	}

	public void setRsvpId(int rsvpId) {
		this.rsvpId = rsvpId;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}