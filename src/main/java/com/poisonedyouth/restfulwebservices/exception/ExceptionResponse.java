package com.poisonedyouth.restfulwebservices.exception;

import java.util.Date;

public class ExceptionResponse {

	private Date timestamp;
	private String messsage;
	private String details;

	public ExceptionResponse(Date timestamp, String messsage, String details) {
		this.timestamp = timestamp;
		this.messsage = messsage;
		this.details = details;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMesssage() {
		return messsage;
	}

	public String getDetails() {
		return details;
	}
}
