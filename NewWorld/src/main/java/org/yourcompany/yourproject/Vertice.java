package org.yourcompany.yourproject;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
    String nome;
    List<Aresta> arestas;

    public Vertice(String nome) {
        this.nome = nome;
        this.arestas = new ArrayList<>();
    }
}
