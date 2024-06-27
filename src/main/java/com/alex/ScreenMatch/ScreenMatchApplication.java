package com.alex.ScreenMatch;

import com.alex.ScreenMatch.model.DatosEpisodio;
import com.alex.ScreenMatch.model.DatosSerie;
import com.alex.ScreenMatch.model.DatosTemporada;
import com.alex.ScreenMatch.principal.EjemploStreams;
import com.alex.ScreenMatch.principal.Principal;
import com.alex.ScreenMatch.service.ConsumoAPI;
import com.alex.ScreenMatch.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.muestraElMenu();

	}
}
