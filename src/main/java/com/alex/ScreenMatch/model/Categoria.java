package com.alex.ScreenMatch.model;

public enum Categoria {
    ACCION("Action", "Acción"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comedia"),
    DRAMA("Drama", "Drama"),
    CRIMEN("Crime", "Crimen");

    private String categoriaOmdb;
    private String categoriaEspanhol;

    Categoria(String categoriaOmdb, String categoriaEspanhol){
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaEspanhol = categoriaEspanhol;
    }

    public static Categoria fromString(String texto){
        for (Categoria categoria : Categoria.values()){
            if (categoria.categoriaOmdb.equalsIgnoreCase(texto)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria fie encontrada " + texto);
    }

    public static Categoria fromEspanhol(String texto){
        for (Categoria categoria : Categoria.values()){
            if (categoria.categoriaEspanhol.equalsIgnoreCase(texto)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria fie encontrada " + texto);
    }

}
