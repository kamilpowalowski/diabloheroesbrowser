package com.hydrasoftworks.diablo.model.exceptions;

import java.text.ParseException;

public class HeroParsingException extends ParseException {
	public HeroParsingException(String detailMessage) {
		super(detailMessage, 0);
	}

	private static final long serialVersionUID = -1707306407166551655L;

}
