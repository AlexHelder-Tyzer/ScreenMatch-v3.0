package com.alex.ScreenMatch.dto;

public record EpisodioDTO(
        Long id, // no necesario para la app
        Integer temporada,
        String titulo,
        Integer numeroEpisodio
) {
}
