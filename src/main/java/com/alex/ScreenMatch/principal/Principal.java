package com.alex.ScreenMatch.principal;

import com.alex.ScreenMatch.model.*;
import com.alex.ScreenMatch.repository.SerieRepository;
import com.alex.ScreenMatch.service.ConsumoAPI;
import com.alex.ScreenMatch.service.ConvierteDatos;

import org.springframework.cglib.core.Local;

import javax.xml.transform.Source;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    // Busca los datos generales de las series
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();

    private final String API_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey="+System.getenv().get("OMDB_API"); // USANDO VARIABLES DE ENTORNO
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private SerieRepository repositorio;
    private List<Serie> series;

    public Principal(SerieRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu(){
        var opcion = -1;

        while (opcion != 0){
            var menu = """
                1. Buscar Series
                2. Buscar Episodios
                3. Mostrar series buscados
                4. Buscar series por titulo
                5. Top 5 mejores series
                6. Buscar series por categoria
                
                0. Salir
                """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch(opcion){
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEposidioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriesPorTitulo();
                    break;
                case 5:
                    buscarTop5Series();
                    break;
                case 6:
                    buscarSeriesPorCategoria();
                    break;
                case 0:
                    System.out.println("Cerrando la palicacion!!");
                    break;
                default:
                    System.out.println("Opcion invalida!!");
            }
        }

        //Mostrar solo el titulo de las episodios para los temporadas
        /*for (int i = 0; i < datos.totalDeTemporadas(); i++) {
            List<DatosEpisodio> episodosTemporada = temporadas.get(i).episodios();
            for (int j = 0; j < episodosTemporada.size(); j++) {
                System.out.println(episodosTemporada.get(j).titulo());
            }
        }*/

        // otra forma de reducir codigo
        //temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        // COnvertir todas las informaciones en una lista del tipo DatosEpidsorio
        /*List<DatosEpisodio>  datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        // TOp 5 epdisodios
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primer filtro N/A " + e))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .peek(e -> System.out.println("Segundo filtro Ordenacion M > m" + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("tercer filtro Mayusculas " + e))
                .limit(5)
                .forEach(System.out::println);

        //Convirtiendo los datos a una lista del tipo Episodio
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                    .map(d -> new Episodio(t.numeroDeTemporada(), d)))
                .collect(Collectors.toList());*/

        /*episodios.forEach(System.out::println);

        //Busqueda de episodioa a partir de x anho
        /*System.out.println("Indica el anho a partir del cual deseas ver los episodios:");
        var fecha = teclado.nextInt();
        teclado.nextLine();
        LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
                .forEach(e -> System.out.println(
                        "Temporada " + e.getTemporada() +
                                "Episodio " + e.getTitulo() +
                                "Fecha de lanzamiento " + e.getFechaDeLanzamiento().format(dtf)
                ));*/

        //Busca episodios por pedazo de titulo
        /*System.out.println("Escriba el titulo del episodio que desea ver: ");
        var pedazoTitulo = teclado.nextLine();
        Optional<Episodio> espisodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
                .findFirst();

        if(espisodioBuscado.isPresent()){
            System.out.println("Episodio encontrado!");
            System.out.println("Los datos son: " + espisodioBuscado.get());
        }
        else{
            System.out.println("Episodio no encontrado!!");
        }*/

        /*Map<Integer, Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getEvaluacion)));

        System.out.println(evaluacionesPorTemporada);


        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));

        System.out.println("La Media es: " + est.getAverage());
        System.out.println("Episodio Mejor evaluado: " + est.getMax());
        System.out.println("Episodio peor evaluado: " + est.getMin());*/
    }

    private DatosSerie getDatosSerie(){
        System.out.println("Escribe el nombre de la serie que deseas buscar: ");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(API_BASE+nombreSerie.replace(" ", "+")+API_KEY);
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }

    private void buscarEposidioPorSerie(){
        mostrarSeriesBuscadas();
        System.out.println("Escribe el nombre de la serie para ver los episodios: ");
        var nombreSerie = teclado.nextLine();

        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();

        if(serie.isPresent()){
            var serieEncontrada = serie.get();
            List<DatosTemporada> temporadas = new ArrayList<>();
            for (int i = 1; i <= serieEncontrada.getTotalDeTemporadas() ; i++) {
                var json = consumoApi.obtenerDatos(API_BASE+serieEncontrada.getTitulo().replace(" ","+")+"&Season="+i+API_KEY);
                DatosTemporada datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
                temporadas.add(datosTemporada);
            }
            temporadas.forEach(System.out::println);
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numeroDeTemporada(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }
    }

    private void buscarSerieWeb(){
        DatosSerie datos = getDatosSerie();
        //datosSeries.add(datos);
        Serie serie = new Serie(datos);
        repositorio.save(serie); // guardar
        System.out.println(datos);

    }

    private void mostrarSeriesBuscadas() {
        series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriesPorTitulo(){
        System.out.println("Escribe el nombre de la serie que desea buscar: ");
        var nombreSerie = teclado.nextLine();
        Optional<Serie> serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);

        if(serieBuscada.isPresent()){
            System.out.println("La serie buscada es: " + serieBuscada.get());
        }
        else{
            System.out.println("Serie no encontrada");
        }
    }

    private void buscarTop5Series(){
        List<Serie> topSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(s -> System.out.println("Serie: " + s.getTitulo() + "Evaluacion: " + s.getEvaluacion()));
    }

    private void buscarSeriesPorCategoria(){
        System.out.println("Escriba el genero/categoria de la serie que desea buscar: ");
        var genero = teclado.nextLine();
        var categoria = Categoria.fromEspanhol(genero);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Las series de la categoria " + genero);
        seriesPorCategoria.forEach(System.out::println);
    }



}
