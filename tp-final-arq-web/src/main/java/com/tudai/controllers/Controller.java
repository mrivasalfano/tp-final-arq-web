package com.tudai.controllers;

import javax.servlet.http.HttpServletResponse;

public class Controller {

	public Controller() {

	}

	protected void responseStatus(int code,HttpServletResponse response) {
		response.setStatus(code);
	}

}
