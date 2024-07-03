package com.alex.ScreenMatch.repository;

import com.alex.ScreenMatch.dto.EpisodioDTO;
import com.alex.ScreenMatch.model.Categoria;
import com.alex.ScreenMatch.model.Episodio;
import com.alex.ScreenMatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    //COnsultas con Derived Queries
    //Usar optional si retorna o no un valor
    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie> findTop5ByOrderByEvaluacionDesc();

    List<Serie> findByGenero(Categoria categoria);

    //List<Serie> findByTotalDeTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(int totalTemporadas, Double evaluacion);
    //Consulta con JPQL y este trabja con Las calses y sus atributos
    @Query(value = "select s from Serie s where s.totalDeTemporadas <= :temporada and s.evaluacion >= :evaluacion")
    List<Serie> seriePorTemporadaYEvaluacion(Integer temporada, Double evaluacion);

    //Optional<Episodio> findTop1ByTituloContainsIgnoreCase(String nombreEpisodio);
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio")
    List<Episodio> episodiosPorNombre(String nombreEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> top5MejoresEpisodiosPorSerie(Serie serie);

    @Query("SELECT s FROM Serie s JOIN s.episodios e GROUP BY s  ORDER BY MAX(e.fechaDeLanzamiento) DESC LIMIT 5")
    List<Serie> lanzamientosMasRecientes();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numeroDeTemporada")
    List<Episodio> ontenerTemporadasPorNumero(Long id, Long numeroDeTemporada);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> topEpisodiosPorSerie(Serie serie);
}
