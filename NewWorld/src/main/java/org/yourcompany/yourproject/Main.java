package org.yourcompany.yourproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("NewWorld.txt"));
            int n = scanner.nextInt();
            int j = scanner.nextInt();
            scanner.nextLine();

            Grafo grafo = new Grafo();

            for (int i = 0; i < n; i++) {
                String vertice = scanner.nextLine().trim();
                grafo.adicionarVertice(vertice);
            }

            for (int i = 0; i < j; i++) {
                String aresta = scanner.nextLine().trim();
                String[] dados = aresta.split("\\s+");
                if (dados.length < 3) {
                    System.err.println("\nDados da aresta inválidos: " + aresta);
                    continue;
                }
                String origem = dados[0];
                String destino = dados[1];
                int peso = Integer.parseInt(dados[2]);
                grafo.adicionarAresta(origem, destino, peso);
            }

            BellmanFord bellmanFord = new BellmanFord(grafo);
            try (Scanner input = new Scanner(System.in)) {
                int opcao;
                do {
                    System.out.println("\n~~One Piece~~:\n");
                    System.out.println("1 - Imprimir o grafo");
                    System.out.println("2 - Encontrar um menor caminho entre duas ilhas");
                    System.out.println("3 - Imprimir a quantidade de ilhas(Vértices)");
                    System.out.println("4 - Imprimir a quantidade de rotas possíveis entre ilhas(Arestas)");
                    System.out.println("99 - Sair");
                    System.out.print("\nEscolha uma opção: ");
                    opcao = input.nextInt();
                    input.nextLine();

                    switch (opcao) {
                        case 1 -> grafo.imprimirGrafo();
                        case 2 -> {
                            System.out.print("\nDigite o vértice de origem: ");
                            String verticeOrigem = input.nextLine().trim();
                            System.out.print("Digite o vértice de destino: ");
                            String verticeDestino = input.nextLine().trim();
                            int distancia = bellmanFord.calcular(verticeOrigem, verticeDestino);
                            if (distancia == Integer.MAX_VALUE) {
                                System.out.println("\nNão há caminho entre " + verticeOrigem + " e " + verticeDestino + ".");
                            } else {
                                System.out.println("\nA menor distância entre " + verticeOrigem + " e " + verticeDestino + " é: " + distancia);
                            }
                        }
                        case 3 -> System.out.println("\nQuantidade de Ilhas: " + grafo.vertices.size());
                        case 4 -> System.out.println("\nQuantidade de Rotas: " + grafo.contarArestas());
                        case 99 -> System.out.println("\nSaindo...");
                        default -> System.out.println("\nOpção inválida. Tente novamente.");
                    }
                } while (opcao != 99);
            } finally {
                scanner.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
        }
    }
}
