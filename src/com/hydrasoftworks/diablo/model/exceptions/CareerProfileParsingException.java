package com.hydrasoftworks.diablo.model.exceptions;

import java.text.ParseException;

public class CareerProfileParsingException extends ParseException {
	public CareerProfileParsingException(String detailMessage) {
		super(detailMessage, 0);
	}

	private static final long serialVersionUID = -1707306407166551655L;

}
