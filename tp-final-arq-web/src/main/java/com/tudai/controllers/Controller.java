package com.tudai.controllers;

import javax.servlet.http.HttpServletResponse;

/**
 * Controller genérico
 * @author Team-Bolivar
 * @version v1.0
 * @since   2020-11-24
 */
public class Controller {

	public Controller() {

	}

	/**
	 * Setea un status HTTP a la respuesta
	 * @param code a setear
	 * @param response con código seteado
	 */
	protected void responseStatus(int code,HttpServletResponse response) {
		response.setStatus(code);
	}

}
