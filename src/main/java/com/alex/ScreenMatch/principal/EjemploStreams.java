package com.alex.ScreenMatch.principal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EjemploStreams {
    public void muestraEjemplo(){
        List<String> nombres = Arrays.asList("Brenda", "Luis", "Maria Fernanda", "Eric", "Genesys");
        nombres.stream()
                .sorted()
                .limit(2)
                .filter(l -> l.startsWith("E"))
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);
    }
}
