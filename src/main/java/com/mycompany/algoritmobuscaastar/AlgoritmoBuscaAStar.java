/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.algoritmobuscaastar;

/**
 *
 * @author marce
 */
import java.util.*;

class Grafo {
    private int numVertices; // Número de vértices
    private LinkedList<Aresta> adjList[]; // Lista de adjacência com custo

    // Construtor
    Grafo(int numVertices) {
        this.numVertices = numVertices;
        adjList = new LinkedList[numVertices];
        for (int i = 0; i < numVertices; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    // Adiciona uma aresta ao grafo com custo
    void adicionarAresta(int origem, int destino, int custo) {
        adjList[origem].add(new Aresta(destino, custo));
        adjList[destino].add(new Aresta(origem, custo)); // Para grafos não direcionados
    }

    // Realiza a busca A* a partir de um vértice fonte até o objetivo
    void aStar(int inicio, int objetivo, int[] heuristica) {
        // PriorityQueue para armazenar os nós com base na função f = g + h
        PriorityQueue<Nodo> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));

        // Mapa para armazenar o custo g para chegar a cada vértice
        int[] custoG = new int[numVertices];
        Arrays.fill(custoG, Integer.MAX_VALUE);
        custoG[inicio] = 0;

        // Inicializa a fila de prioridade com o vértice inicial
        pq.add(new Nodo(inicio, 0, heuristica[inicio]));

        while (!pq.isEmpty()) {
            Nodo atual = pq.poll();
            int vertice = atual.vertice;

            // Se o vértice atual é o objetivo, imprime o custo e retorna
            if (vertice == objetivo) {
                System.out.println("Custo mínimo para alcançar o vértice " + objetivo + " é " + custoG[objetivo]);
                return;
            }

            // Processa todos os vizinhos do vértice atual
            for (Aresta aresta : adjList[vertice]) {
                int novoCustoG = custoG[vertice] + aresta.custo;
                if (novoCustoG < custoG[aresta.destino]) {
                    custoG[aresta.destino] = novoCustoG;
                    int custoH = heuristica[aresta.destino];
                    pq.add(new Nodo(aresta.destino, novoCustoG, custoH));
                }
            }
        }

        System.out.println("Não há caminho do vértice " + inicio + " ao vértice " + objetivo);
    }

    // Classe interna para representar uma aresta com custo
    private static class Aresta {
        int destino;
        int custo;

        Aresta(int destino, int custo) {
            this.destino = destino;
            this.custo = custo;
        }
    }

    // Classe interna para representar um nó na fila de prioridade
    private static class Nodo {
        int vertice;
        int custoG; // Custo real para chegar a este nó
        int heuristica; // Estimativa heurística
        int f; // Função de avaliação (f = g + h)

        Nodo(int vertice, int custoG, int heuristica) {
            this.vertice = vertice;
            this.custoG = custoG;
            this.heuristica = heuristica;
            this.f = custoG + heuristica;
        }
    }
}

public class AlgoritmoBuscaAStar  {
    public static void main(String[] args) {
        Grafo g = new Grafo(5);

        g.adicionarAresta(0, 1, 10);
        g.adicionarAresta(0, 4, 3);
        g.adicionarAresta(1, 2, 2);
        g.adicionarAresta(1, 4, 4);
        g.adicionarAresta(2, 3, 9);
        g.adicionarAresta(3, 4, 7);
        g.adicionarAresta(4, 2, 6);

        // Heurísticas (distâncias estimadas ao objetivo, devem ser ajustadas conforme o problema)
        int[] heuristicas = {7, 6, 2, 0, 1};

        System.out.println("Busca A* do vértice 0 ao vértice 3:");

        g.aStar(0, 3, heuristicas);
    }
}
