package org.yourcompany.yourproject;

import java.util.ArrayList;

public class BellmanFord {
    private final Grafo grafo;

    public BellmanFord(Grafo grafo) {
        this.grafo = grafo;
    }

    public int calcular(String origem, String destino) {
        int[] distancias = inicializar(origem);
        String[] predecessores = new String[grafo.vertices.size()];

        relaxar(distancias, predecessores);

        if (ciclosNegativos(distancias)) {
            System.out.println("\nO grafo contém um ciclo negativo.");
            return Integer.MAX_VALUE;
        }

        imprimirCaminho(predecessores, origem, destino);
        return distancias[grafo.vertices.indexOf(grafo.buscarVertice(destino))];
    }

    private int[] inicializar(String origem) {
        int[] distancias = new int[grafo.vertices.size()];
        Vertice verticeOrigem = grafo.buscarVertice(origem);

        for (int i = 0; i < distancias.length; i++) {
            distancias[i] = Integer.MAX_VALUE;
        }

        distancias[grafo.vertices.indexOf(verticeOrigem)] = 0;
        return distancias;
    }

    private void relaxar(int[] distancias, String[] predecessores) {
        for (int i = 1; i < grafo.vertices.size(); i++) {
            for (Vertice vertice : grafo.vertices) {
                for (Aresta aresta : vertice.arestas) {
                    int uIndex = grafo.vertices.indexOf(vertice);
                    int vIndex = grafo.vertices.indexOf(grafo.buscarVertice(aresta.destino));
                    if (distancias[uIndex] != Integer.MAX_VALUE &&
                        distancias[uIndex] + aresta.peso < distancias[vIndex]) {
                        distancias[vIndex] = distancias[uIndex] + aresta.peso;
                        predecessores[vIndex] = vertice.nome;
                    }
                }
            }
        }
    }

    private boolean ciclosNegativos(int[] distancias) {
        for (Vertice vertice : grafo.vertices) {
            for (Aresta aresta : vertice.arestas) {
                int uIndex = grafo.vertices.indexOf(vertice);
                int vIndex = grafo.vertices.indexOf(grafo.buscarVertice(aresta.destino));
                if (distancias[uIndex] != Integer.MAX_VALUE &&
                    distancias[uIndex] + aresta.peso < distancias[vIndex]) {
                    return true;
                }
            }
        }
        return false;
    }

    private void imprimirCaminho(String[] predecessores, String origem, String destino) {
        ArrayList<String> caminho = new ArrayList<>();
        String caminhar = destino;

        while (caminhar != null) {
            caminho.add(0, caminhar);
            int index = grafo.vertices.indexOf(grafo.buscarVertice(caminhar));
            caminhar = index != -1 ? predecessores[index] : null;
        }

        System.out.print("\nCaminho de " + origem + " até " + destino + ": ");
        for (int i = 0; i < caminho.size(); i++) {
            System.out.print(caminho.get(i) + (i < caminho.size() - 1 ? " -> " : ""));
        }
        System.out.println();
    }
}


