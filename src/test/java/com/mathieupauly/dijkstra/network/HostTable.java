package com.mathieupauly.dijkstra.network;

import com.mathieupauly.dijkstra.Edge;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class HostTable {

    private Map<String, Integer> vertexIndex;
    private Map<Integer, String> hostIndex;

    public HostTable() {
        vertexIndex = new HashMap<>();
        hostIndex = new TreeMap<>();
    }

    public void registerNode(String name) {
        final int vertex = vertexIndex.size();
        vertexIndex.put(name, vertex);
        hostIndex.put(vertex, name);
    }

    public int vertexFor(String name) {
        return vertexIndex.get(name);
    }

    public String hostFor(int vertex) {
        return hostIndex.get(vertex);
    }

    Edge edge(Link e) {
        return new Edge(vertexIndex.get(e.hostA), vertexIndex.get(e.hostB));
    }
}
