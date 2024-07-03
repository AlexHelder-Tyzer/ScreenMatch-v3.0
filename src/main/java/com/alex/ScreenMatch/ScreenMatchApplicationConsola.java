//package com.alex.ScreenMatch;
//
//import com.alex.ScreenMatch.principal.Principal;
//import com.alex.ScreenMatch.repository.SerieRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ScreenMatchApplicationConsola implements CommandLineRunner {
//
//	@Autowired //indica a Spring que haga una inyeccion de dependencias
//	private SerieRepository repository; // indica la dependencia que manejara
//
//	public static void main(String[] args) {
//		SpringApplication.run(ScreenMatchApplicationConsola.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		Principal principal = new Principal(repository);
//		principal.muestraElMenu();
//	}
//}
