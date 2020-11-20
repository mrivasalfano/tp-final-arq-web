package com.tudai.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Convierte strings de fechas a milisegundos
 * @author Team-Bolivar
 * @version v1.0
 * @since   2020-11-24
 */
public class DateToMilli {

    /**
     * Genera desde una fecha sin hora en formato string
     * a dejarlo en milisegundos para poder usarlo en el parametro new Date()
     * @param myDate
     * @return la fecha en milisegundos
     */
    public static long getDate(String myDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date date = null;
		try {
			date = sdf.parse(myDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long millis = date.getTime();
		return millis;
	}

}
