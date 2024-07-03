package com.alex.ScreenMatch.dto;

import com.alex.ScreenMatch.model.Categoria;
import com.alex.ScreenMatch.model.Episodio;
import jakarta.persistence.*;

import java.util.List;

public record SerieDTO(
        Long id,
        String titulo,
        Integer totalDeTemporadas,
        Double evaluacion,
        String poster,
        Categoria genero,
        String actores,
        String sinopsis
) {
}
