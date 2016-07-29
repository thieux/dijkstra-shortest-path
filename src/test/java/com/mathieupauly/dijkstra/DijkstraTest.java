package com.mathieupauly.dijkstra;

import com.mathieupauly.dijkstra.network.NamedEdge;
import com.mathieupauly.dijkstra.network.Network;
import com.mathieupauly.dijkstra.network.VertexTable;
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

        vertexTable.registerNode("A");
        vertexTable.registerNode("B");
        vertexTable.registerNode("C");
        vertexTable.registerNode("D");
        vertexTable.registerNode("E");
        vertexTable.registerNode("F");
        vertexTable.registerNode("G");
        vertexTable.registerNode("H");
        vertexTable.registerNode("I");
        vertexTable.registerNode("J");

        final Network namedGraph = new Network(vertexTable);

        namedGraph.insertEdge(new NamedEdge("A", "B"));
        namedGraph.insertEdge(new NamedEdge("A", "C"));
        namedGraph.insertEdge(new NamedEdge("A", "E"));
        namedGraph.insertEdge(new NamedEdge("B", "F"));
        namedGraph.insertEdge(new NamedEdge("C", "G"));
        namedGraph.insertEdge(new NamedEdge("C", "H"));
        namedGraph.insertEdge(new NamedEdge("D", "H"));
        namedGraph.insertEdge(new NamedEdge("E", "J"));
        namedGraph.insertEdge(new NamedEdge("F", "I"));
        namedGraph.insertEdge(new NamedEdge("H", "J"));

        graph = namedGraph.buildGraph();
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
