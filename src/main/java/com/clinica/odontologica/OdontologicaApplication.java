package com.clinica.odontologica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.clinica.odontologica.dao.DB;

@SpringBootApplication
public class OdontologicaApplication {

	public static void main(String[] args) {
		DB.crearTablas();
		SpringApplication.run(OdontologicaApplication.class, args);
	}

}
