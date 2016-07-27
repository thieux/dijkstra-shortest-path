package com.mathieupauly.dijkstra;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class DijkstraTest {

    private VertexTable vertexTable;
    private Graph graph;

    @Before
    public void setup() {
        vertexTable = new VertexTable();

        vertexTable.register("A");
        vertexTable.register("B");
        vertexTable.register("C");
        vertexTable.register("D");
        vertexTable.register("E");
        vertexTable.register("F");
        vertexTable.register("G");
        vertexTable.register("H");
        vertexTable.register("I");
        vertexTable.register("J");

        graph = new Graph(vertexTable.size());

        graph.insertEdge(vertexTable.numberedEdge(new NamedEdge("A", "B")));
        graph.insertEdge(vertexTable.numberedEdge(new NamedEdge("A", "C")));
        graph.insertEdge(vertexTable.numberedEdge(new NamedEdge("A", "E")));
        graph.insertEdge(vertexTable.numberedEdge(new NamedEdge("B", "F")));
        graph.insertEdge(vertexTable.numberedEdge(new NamedEdge("C", "G")));
        graph.insertEdge(vertexTable.numberedEdge(new NamedEdge("C", "H")));
        graph.insertEdge(vertexTable.numberedEdge(new NamedEdge("D", "H")));
        graph.insertEdge(vertexTable.numberedEdge(new NamedEdge("E", "J")));
        graph.insertEdge(vertexTable.numberedEdge(new NamedEdge("F", "I")));
        graph.insertEdge(vertexTable.numberedEdge(new NamedEdge("H", "J")));
    }

    @Test
    public void path_a_b() {
        int startVertex = vertexTable.get("A");
        int target = vertexTable.get("B");

        final List<Integer> path = findShortestPath(graph, startVertex, target);
        final List<String> namedPath = path.stream().map(vertexTable::getNumber).collect(Collectors.toList());

        assertThat(namedPath).containsExactly("A", "B");
    }

    @Test
    public void path_a_h() {
        int startVertex = vertexTable.get("A");
        int target = vertexTable.get("H");

        final List<Integer> path = findShortestPath(graph, startVertex, target);
        final List<String> namedPath = path.stream().map(vertexTable::getNumber).collect(Collectors.toList());

        assertThat(namedPath).containsExactly("A", "C", "H");
    }

    @Test
    public void path_a_j() {
        int startVertex = vertexTable.get("A");
        int target = vertexTable.get("J");

        final List<Integer> path = findShortestPath(graph, startVertex, target);

        final List<String> namedPath = path.stream().map(vertexTable::getNumber).collect(Collectors.toList());

        assertThat(namedPath).containsExactly("A", "E", "J");
    }

    private List<Integer> findShortestPath(Graph graph, int startVertex, int target) {
        int size = graph.vertices;
        Set<Integer> complementary = new TreeSet<>();
        int distances[] = new int[size];
        int[] previous = new int[size];

        for (int vertex = 0; vertex < distances.length; vertex++) {
            distances[vertex] = Integer.MAX_VALUE;
            previous[vertex] = -1;
            complementary.add(vertex);
        }

        distances[startVertex] = 0;

        while (!complementary.isEmpty()) {
            int closestVertex = findClosestVertex(complementary, distances);
            complementary.remove(closestVertex);

            for (Integer neighbour : graph.neighbours(closestVertex)) {
                int alt = distances[closestVertex] + 1;
                if (alt < distances[neighbour]) {
                    distances[neighbour] = alt;
                    previous[neighbour] = closestVertex;
                }
            }
        }

        final List<Integer> path = new ArrayList<>();

        while (previous[target] != -1) {
            path.add(0, target);
            target = previous[target];
        }
        path.add(0, target);

        return path;
    }

    private int findClosestVertex(Set<Integer> complementary, int[] distances) {
        int min = Integer.MAX_VALUE;
        int closestVertex = -1;
        for (Integer vertex : complementary) {
            if (distances[vertex] < min) {
                min = distances[vertex];
                closestVertex = vertex;
            }
        }
        return closestVertex;
    }
}
