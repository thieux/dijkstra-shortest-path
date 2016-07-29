package com.mathieupauly.dijkstra.network;

import com.mathieupauly.dijkstra.Graph;

import java.util.ArrayList;
import java.util.List;

public class Network {
    private final List<Link> links;
    private final HostTable hostTable;

    public Network(HostTable hostTable) {
        this.links = new ArrayList<>();
        this.hostTable = hostTable;
    }

    public void insertLink(Link link) {
        links.add(link);
    }

    public Graph buildGraph() {
        final Graph graph = new Graph(links.size());

        for (Link link : links) {
            graph.insertEdge(hostTable.edge(link));
        }

        return graph;
    }
}
