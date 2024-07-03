package com.alex.ScreenMatch.service;

import com.alex.ScreenMatch.dto.EpisodioDTO;
import com.alex.ScreenMatch.dto.SerieDTO;
import com.alex.ScreenMatch.model.Categoria;
import com.alex.ScreenMatch.model.Serie;
import com.alex.ScreenMatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obtenerTodasLasSeries() {
        return convierteDatos(repository.findAll());
    }

    public List<SerieDTO> obtenerTop5() {
        return convierteDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return convierteDatos(repository.lanzamientosMasRecientes());
    }

    public SerieDTO obtenerPorId(Long id)
    {
        Optional<Serie> serie = repository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(
                    s.getId(),
                    s.getTitulo(),
                    s.getTotalDeTemporadas(),
                    s.getEvaluacion(),
                    s.getPoster(),
                    s.getGenero(),
                    s.getActores(),
                    s.getSinopsis());
        }
        else{
            return null;
        }
    }

    public List<SerieDTO> convierteDatos(List<Serie> serie){
        // convertir una Serie a SerieDTO usando streams
        return serie.stream()
                .map(s -> new SerieDTO(s.getId(),
                    s.getTitulo(),
                    s.getTotalDeTemporadas(),
                    s.getEvaluacion(),
                    s.getPoster(),
                    s.getGenero(),
                    s.getActores(),
                    s.getSinopsis()))
                .collect(Collectors.toList());
    }

    public List<EpisodioDTO> obtenerTodasLasTemporadas(Long id) {
        // consulta usando streams
        Optional<Serie> serie = repository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(
                            e.getId(), //No necesario para este modulo
                            e.getTemporada(),
                            e.getTitulo(),
                            e.getNumeroEpisodio()))
                    .collect(Collectors.toList());
        }
        else{
            return null;
        }
    }

    public List<EpisodioDTO> obtenerTemporadasPorNumero(Long id, Long numeroDeTemporada) {
        return repository.ontenerTemporadasPorNumero(id, numeroDeTemporada).stream()
                .map(e -> new EpisodioDTO(
                        e.getId(), //No necesario para este modulo
                        e.getTemporada(),
                        e.getTitulo(),
                        e.getNumeroEpisodio()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obtenerSeriesPorCategoria(String nombreGenero) {
        Categoria categoria = Categoria.fromEspanhol(nombreGenero);
        return convierteDatos(repository.findByGenero(categoria));
    }

    public List<EpisodioDTO> obtenerTopEpisodios(Long id) {
        Serie serie = repository.findById(id).get();
        return repository.topEpisodiosPorSerie(serie)
                .stream()
                .map(e -> new EpisodioDTO(e.getId(), e.getTemporada(),e.getTitulo(),
                        e.getNumeroEpisodio()))
                .collect(Collectors.toList());
    }
}
