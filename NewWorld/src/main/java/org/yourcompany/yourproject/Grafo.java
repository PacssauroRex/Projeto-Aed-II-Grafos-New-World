/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.yourcompany.yourproject;

/**
 *
 * @author Victor
 */
import java.util.ArrayList;
import java.util.List;

public class Grafo {
    List<Vertice> vertices;

    public Grafo() {
        vertices = new ArrayList<>();
    }

    public void adicionarVertice(String vertice) {
        if (buscarVertice(vertice) == null) {
            vertices.add(new Vertice(vertice));
        }
    }

    public Vertice buscarVertice(String nome) {
        for (Vertice v : vertices) {
            if (v.nome.equals(nome)) {
                return v;
            }
        }
        return null;
    }

    public void adicionarAresta(String origem, String destino, int peso) {
        adicionarVertice(origem);
        adicionarVertice(destino);

        Vertice vOrigem = buscarVertice(origem);
        Aresta aresta = new Aresta(origem, destino, peso);
        vOrigem.arestas.add(aresta);
    }

    public void imprimirGrafo() {
        for (Vertice vertice : vertices) {
            System.out.print("\n" + vertice.nome + ": ");
            for (Aresta aresta : vertice.arestas) {
                System.out.print("(" + aresta.destino + ", peso: " + aresta.peso + ") ");
            }
            System.out.println();
        }
    }

    public int contarArestas() {
        int totalArestas = 0;
        for (Vertice vertice : vertices) {
            totalArestas += vertice.arestas.size();
        }
        return totalArestas;
    }
}
