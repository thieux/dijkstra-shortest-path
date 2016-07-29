package com.mathieupauly.dijkstra;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    final int vertices;
    private final boolean[][] adjacencies;

    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencies = new boolean[vertices][vertices];
    }

    public void insertEdge(Edge e) {
        int vertex1 = e.vertex1;
        int vertex2 = e.vertex2;

        adjacencies[vertex1][vertex2] = true;
        adjacencies[vertex2][vertex1] = true;
    }

    List<Integer> adjacencies(int center) {
        List<Integer> neighbours = new ArrayList<>();
        for (int v = 0; v < adjacencies.length; v++) {
            if (adjacencies[center][v]) {
                neighbours.add(v);
            }
        }
        return neighbours;
    }
}
