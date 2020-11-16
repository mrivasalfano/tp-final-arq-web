package com.tudai;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class TpFinalArqWebApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC")); //necesario para mantener coordinada las fechas de envio por el cliente al servidor
		SpringApplication.run(TpFinalArqWebApplication.class, args);
	}

}
