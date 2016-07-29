package com.mathieupauly.dijkstra;

import java.util.ArrayList;
import java.util.List;

class NamedGraph {
    private final List<NamedEdge> edges;
    private final VertexTable vertexTable;

    NamedGraph(VertexTable vertexTable) {
        this.vertexTable = vertexTable;
        this.edges = new ArrayList<>();
    }

    void insertEdge(NamedEdge e) {
        edges.add(e);
    }

    Graph buildGraph() {
        final Graph graph = new Graph(edges.size());

        for (NamedEdge namedEdge : edges) {
            graph.insertEdge(vertexTable.numberedEdge(namedEdge));
        }

        return graph;
    }
}
