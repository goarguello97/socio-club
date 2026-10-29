package ar.edu.siglo21.socioclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada del backend. @SpringBootApplication activa el escaneo de componentes:
 * Spring busca en este paquete y sus subpaquetes las clases anotadas (@Controller, @Service,
 * repositorios) y crea un bean de cada una.
 */
@SpringBootApplication
public class SocioclubApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocioclubApplication.class, args);
	}

}
