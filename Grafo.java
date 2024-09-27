import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Aresta {
    String destino;
    int peso;

    public Aresta(String origem, String destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    } 
}

class Vertice {
    String nome;
    List<Aresta> arestas;

    public Vertice(String nome) {
        this.nome = nome;
        this.arestas = new ArrayList<>();
    }
}

public class Grafo {
    private final List<Vertice> vertices;

    public Grafo() {
        vertices = new ArrayList<>();
    }

    public void adicionarVertice(String vertice) {
        if (buscarVertice(vertice) == null) {
            vertices.add(new Vertice(vertice));
        }
    }

    private Vertice buscarVertice(String nome) {
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

    public int BellmanFord(String origem, String destino) {
        int[] distancias = inicializar(origem);
        String[] predecessores = new String[vertices.size()];

        relaxar(distancias, predecessores);

        if (ciclosNegativos(distancias)) {
            System.out.println("\nO grafo contém um ciclo negativo.");
            return Integer.MAX_VALUE;
        }

        imprimirCaminho(predecessores, origem, destino);
        return distancias[vertices.indexOf(buscarVertice(destino))];
    }

    private int[] inicializar(String origem) {
        int[] distancias = new int[vertices.size()];
        Vertice verticeOrigem = buscarVertice(origem);

        for (int i = 0; i < distancias.length; i++) {
            distancias[i] = Integer.MAX_VALUE;
        }

        distancias[vertices.indexOf(verticeOrigem)] = 0;
        return distancias;
    }

    private void relaxar(int[] distancias, String[] predecessores) {
        for (int i = 1; i < vertices.size(); i++) {
            for (Vertice vertice : vertices) {
                for (Aresta aresta : vertice.arestas) {
                    int uIndex = vertices.indexOf(vertice);
                    int vIndex = vertices.indexOf(buscarVertice(aresta.destino));
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
        for (Vertice vertice : vertices) {
            for (Aresta aresta : vertice.arestas) {
                int uIndex = vertices.indexOf(vertice);
                int vIndex = vertices.indexOf(buscarVertice(aresta.destino));
                if (distancias[uIndex] != Integer.MAX_VALUE &&
                    distancias[uIndex] + aresta.peso < distancias[vIndex]) {
                    return true; 
                }
            }
        }
        return false;
    }

    private void imprimirCaminho(String[] predecessores, String origem, String destino) {
        List<String> caminho = new ArrayList<>();
        String caminhar = destino;

        while (caminhar != null) {
            caminho.add(0, caminhar);
            int index = vertices.indexOf(buscarVertice(caminhar));
            caminhar = index != -1 ? predecessores[index] : null;
        }

        System.out.print("\nCaminho de " + origem + " até " + destino + ": ");
        for (int i = 0; i < caminho.size(); i++) {
            System.out.print(caminho.get(i) + (i < caminho.size() - 1 ? " -> " : ""));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("Grafo.txt"));
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

            try (Scanner input = new Scanner(System.in)) {
                int opcao;
                do {
                    System.out.println("\n~~One Piece~~:\n");
                    System.out.println("1 - Imprimir o grafo");
                    System.out.println("2 - Encontrar um menor caminho entre duas ilhas");
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
                            int distancia = grafo.BellmanFord(verticeOrigem, verticeDestino);
                            if (distancia == Integer.MAX_VALUE) {
                                System.out.println("\nNão há caminho entre " + verticeOrigem + " e " + verticeDestino + ".");
                            } else {
                                System.out.println("\nA menor distância entre " + verticeOrigem + " e " + verticeDestino + " é: " + distancia);
                            }
                        }
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
