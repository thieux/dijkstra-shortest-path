package com.mathieupauly.dijkstra;

import com.mathieupauly.dijkstra.network.Link;
import com.mathieupauly.dijkstra.network.Network;
import com.mathieupauly.dijkstra.network.HostTable;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class DijkstraTest {

    private static final int INFINITY = Integer.MAX_VALUE;
    private static final int UNDEFINED_VERTEX = -1;
    private HostTable hostTable;
    private Graph graph;

    @Before
    public void setup() {
        hostTable = new HostTable();

        hostTable.registerNode("A");
        hostTable.registerNode("B");
        hostTable.registerNode("C");
        hostTable.registerNode("D");
        hostTable.registerNode("E");
        hostTable.registerNode("F");
        hostTable.registerNode("G");
        hostTable.registerNode("H");
        hostTable.registerNode("I");
        hostTable.registerNode("J");

        final Network net = new Network(hostTable);

        net.insertLink(new Link("A", "B"));
        net.insertLink(new Link("A", "C"));
        net.insertLink(new Link("A", "E"));
        net.insertLink(new Link("B", "F"));
        net.insertLink(new Link("C", "G"));
        net.insertLink(new Link("C", "H"));
        net.insertLink(new Link("D", "H"));
        net.insertLink(new Link("E", "J"));
        net.insertLink(new Link("F", "I"));
        net.insertLink(new Link("H", "J"));

        graph = net.buildGraph();
    }

    @Test
    public void path_a_b() {
        int startVertex = hostTable.vertexFor("A");
        int targetVertex = hostTable.vertexFor("B");

        final List<Integer> path = findShortestPath(graph, startVertex, targetVertex);
        final List<String> namedPath = path.stream().map(hostTable::hostFor).collect(Collectors.toList());

        assertThat(namedPath).containsExactly("A", "B");
    }

    @Test
    public void path_a_h() {
        int startVertex = hostTable.vertexFor("A");
        int targetVertex = hostTable.vertexFor("H");

        final List<Integer> path = findShortestPath(graph, startVertex, targetVertex);
        final List<String> namedPath = path.stream().map(hostTable::hostFor).collect(Collectors.toList());

        assertThat(namedPath).containsExactly("A", "C", "H");
    }

    @Test
    public void path_a_j() {
        int startVertex = hostTable.vertexFor("A");
        int targetVertex = hostTable.vertexFor("J");

        final List<Integer> path = findShortestPath(graph, startVertex, targetVertex);

        final List<String> namedPath = path.stream().map(hostTable::hostFor).collect(Collectors.toList());

        assertThat(namedPath).containsExactly("A", "E", "J");
    }

    @Test
    public void path_a_a() {
        int startVertex = hostTable.vertexFor("A");
        int targetVertex = hostTable.vertexFor("A");

        final List<Integer> path = findShortestPath(graph, startVertex, targetVertex);

        final List<String> namedPath = path.stream().map(hostTable::hostFor).collect(Collectors.toList());

        assertThat(namedPath).containsExactly("A");
    }

    private List<Integer> findShortestPath(Graph graph, int startVertex, int target) {
        int size = graph.vertices;
        int distances[] = new int[size];
        int[] reversePath = new int[size];
        Set<Integer> complementary = new TreeSet<>();

        for (int vertex = 0; vertex < distances.length; vertex++) {
            distances[vertex] = INFINITY;
            reversePath[vertex] = UNDEFINED_VERTEX;
            complementary.add(vertex);
        }

        distances[startVertex] = 0;

        while (!complementary.isEmpty()) {
            int closestVertex = findClosestVertex(complementary, distances);
            complementary.remove(closestVertex);

            for (Integer neighbour : graph.adjacencies(closestVertex)) {
                int alt = distances[closestVertex] + 1;
                if (alt < distances[neighbour]) {
                    distances[neighbour] = alt;
                    reversePath[neighbour] = closestVertex;
                }
            }
        }

        final List<Integer> path = new ArrayList<>();

        while (reversePath[target] != -1) {
            path.add(0, target);
            target = reversePath[target];
        }
        path.add(0, target);

        return path;
    }

    private int findClosestVertex(Set<Integer> complementary, int[] distances) {
        int min = INFINITY;
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
