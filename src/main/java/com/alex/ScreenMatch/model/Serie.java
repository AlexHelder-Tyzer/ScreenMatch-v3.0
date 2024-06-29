package com.alex.ScreenMatch.model;

import com.alex.ScreenMatch.service.ConsultaChatGPT;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

@Entity // indica que Serie sera una tabla en la DB
@Table(name="series") // cambiar el nombre de la tabla en la DB
public class Serie {
    @Id // indicar que sera la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // forma como sera generado la clave promaria
    private Long id;
    @Column(unique = true) // Los nombres de seria sea unicos
    private String titulo;
    private Integer totalDeTemporadas;
    private Double evaluacion;
    private String poster;
    @Enumerated(EnumType.STRING) // utilizamos categorias con un enum
    private Categoria genero;
    private String actores;
    private String sinopsis;
    @Transient // indica que existen episodios, pero au no se usaran
    private List<Episodio> episodios;

    public Serie(DatosSerie datosSerie){
        this.titulo = datosSerie.titulo();
        this.totalDeTemporadas = datosSerie.totalDeTemporadas();
        this.evaluacion = Optional.of(Double.valueOf(datosSerie.evaluacion())).orElse(0.0);
        this.poster = datosSerie.poster();
        this.genero = Categoria.fromString(datosSerie.genero().split(",")[0]);
        this.actores = datosSerie.actores();
        this.sinopsis = datosSerie.sinopsis();//ConsultaChatGPT.obtenerTraduccion(datosSerie.sinopsis());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalDeTemporadas() {
        return totalDeTemporadas;
    }

    public void setTotalDeTemporadas(Integer totalDeTemporadas) {
        this.totalDeTemporadas = totalDeTemporadas;
    }

    public Double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    @Override
    public String toString() {
        return "genero=" + genero +
                ", titulo='" + titulo + '\'' +
                ", totalDeTemporadas=" + totalDeTemporadas +
                ", evaluacion=" + evaluacion +
                ", poster='" + poster + '\'' +
                ", actores='" + actores + '\'' +
                ", sinopsis='" + sinopsis + '\'';
    }
}
