package com.mathieupauly.dijkstra.network;

import com.mathieupauly.dijkstra.Edge;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class VertexTable {

    private Map<String, Integer> nameTable;
    private Map<Integer, String> index;

    public VertexTable() {
        nameTable = new HashMap<>();
        index = new TreeMap<>();
    }

    public void registerNode(String name) {
        final int vertex = nameTable.size();
        nameTable.put(name, vertex);
        index.put(vertex, name);
    }

    public int get(String name) {
        return nameTable.get(name);
    }

    public String getNumber(int vertex) {
        return index.get(vertex);
    }

    Edge numberedEdge(NamedEdge e) {
        return new Edge(nameTable.get(e.v1), nameTable.get(e.v2));
    }
}
