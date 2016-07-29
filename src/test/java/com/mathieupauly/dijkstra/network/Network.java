package com.mathieupauly.dijkstra.network;

import com.mathieupauly.dijkstra.Graph;

import java.util.ArrayList;
import java.util.List;

public class Network {
    private final List<NamedEdge> edges;
    private final VertexTable vertexTable;

    public Network(VertexTable vertexTable) {
        this.vertexTable = vertexTable;
        this.edges = new ArrayList<>();
    }

    public void insertEdge(NamedEdge e) {
        edges.add(e);
    }

    public Graph buildGraph() {
        final Graph graph = new Graph(edges.size());

        for (NamedEdge namedEdge : edges) {
            graph.insertEdge(vertexTable.numberedEdge(namedEdge));
        }

        return graph;
    }
}
