package com.alex.ScreenMatch.model;

public enum Categoria {
    ACCION("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIMEN("Crime");

    private String categiriaOmdb;

    Categoria(String categoriaOmdb){
        this.categiriaOmdb = categoriaOmdb;
    }

    public static Categoria fromString(String texto){
        for (Categoria categoria : Categoria.values()){
            if (categoria.categiriaOmdb.equalsIgnoreCase(texto)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria fie encontrada " + texto);
    }

}
