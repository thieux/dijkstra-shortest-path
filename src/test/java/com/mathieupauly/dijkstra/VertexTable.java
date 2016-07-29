package com.mathieupauly.dijkstra;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class VertexTable {

    private Map<String, Integer> nameTable;
    private Map<Integer, String> index;

    VertexTable() {
        nameTable = new HashMap<>();
        index = new TreeMap<>();
    }

    void registerNode(String name) {
        final int vertex = nameTable.size();
        nameTable.put(name, vertex);
        index.put(vertex, name);
    }

    int size() {
        return nameTable.size();
    }

    Edge numberedEdge(NamedEdge e) {
        return new Edge(nameTable.get(e.v1), nameTable.get(e.v2));
    }

    int get(String name) {
        return nameTable.get(name);
    }

    String getNumber(int vertex) {
        return index.get(vertex);
    }
}
