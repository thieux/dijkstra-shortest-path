package com.mathieupauly.dijkstra;

import java.util.ArrayList;
import java.util.List;

class Graph {
    final int vertices;
    private final boolean[][] adjacencies;

    Graph(int vertices) {
        this.vertices = vertices;
        adjacencies = new boolean[vertices][vertices];
    }

    void insertEdge(Edge e) {
        int e1 = e.v1;
        int e2 = e.v2;

        adjacencies[e1][e2] = true;
        adjacencies[e2][e1] = true;
    }

    List<Integer> neighbours(int start) {
        List<Integer> neighbours = new ArrayList<>();
        for (int v = 0; v < adjacencies.length; v++) {
            if (adjacencies[start][v]) {
                neighbours.add(v);
            }
        }
        return neighbours;
    }
}
