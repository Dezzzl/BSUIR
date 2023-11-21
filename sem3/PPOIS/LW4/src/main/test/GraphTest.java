import com.dezzzl.Graph;
import com.dezzzl.Node;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GraphTest {
    private final Graph graph = new Graph<>();

    private Node<String> node1 = new Node<>("node1");
    private Node<String> node2 = new Node<>("node2");
    private Node<String> node3 = new Node<>("node3");

    private Node<String> node4 = new Node<>("node4");

    private Node<String> node5 = new Node<>("node5");

    @Test
    public void addNode() {
        graph.addNode(node1);
        graph.addNode(node2);
        assertEquals(2, graph.getIncidenceMatrix().size());
    }

    @Test public void addEdge(){
        int a =0;
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(node1, node2);
        List<Integer> firstRow = Arrays.asList(1);
        List<Integer> secondRow = Arrays.asList(-1);
        List<List<Integer>> expectedList = Arrays.asList(firstRow, secondRow);
        assertEquals(expectedList, graph.getIncidenceMatrix());
    }

    @Test public void getNode(){
        int a =0;
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(node1, node2);
       Optional<Node<String>> nodeOptional1 =  graph.getNode("node1");
        nodeOptional1.ifPresent(stringNode -> assertEquals("node1", stringNode.getInfo()));
        Optional<Node<String>> nodeOptional2 =  graph.getNode("node100");
        assertTrue(nodeOptional2.isEmpty());
    }

    @Test public void isEdgeInGraph(){
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(node1, node2);
        assertTrue(graph.isEdgeInGraph(node1, node2));
        assertFalse(graph.isEdgeInGraph(node2, node1));
    }

    @Test public void getDegreeOfNode(){
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addEdge(node1, node2);
        graph.addEdge(node3, node1);
        assertEquals(2, graph.getDegreeOfNode(node1));
    }

    @Test public void deleteEdge(){
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addEdge(node1, node2);
        graph.addEdge(node3, node1);
        List<Integer> firstRow = Arrays.asList(1,-1);
        List<Integer> secondRow = Arrays.asList(-1, 0);
        List<Integer> thirdRow = Arrays.asList(0,1);
        List<List<Integer>> expectedList = Arrays.asList(firstRow, secondRow, thirdRow);
        assertEquals(expectedList, graph.getIncidenceMatrix());

        graph.deleteEdge(node1, node2);
        firstRow = Arrays.asList(-1);
        secondRow = Arrays.asList( 0);
        thirdRow = Arrays.asList(1);
        expectedList = Arrays.asList(firstRow, secondRow, thirdRow);
        assertEquals(expectedList, graph.getIncidenceMatrix());
    }


    @Test public void deleteNode(){
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addEdge(node1, node2);
        graph.addEdge(node3, node1);
        List<Integer> firstRow = Arrays.asList(1,-1);
        List<Integer> secondRow = Arrays.asList(-1, 0);
        List<Integer> thirdRow = Arrays.asList(0,1);
        List<List<Integer>> expectedList = Arrays.asList(firstRow, secondRow, thirdRow);
        assertEquals(expectedList, graph.getIncidenceMatrix());

        graph.deleteNode(node2);
        firstRow = Arrays.asList(-1);
        secondRow = Arrays.asList( 1);
        expectedList = Arrays.asList(firstRow, secondRow);
        assertEquals(expectedList, graph.getIncidenceMatrix());
    }


    @Test public void getNodeWithIter(){
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addEdge(node1, node2);
        graph.addEdge(node3, node1);
       Optional<Node<String>>  nodeOptional=graph.getNodeWithIter(node1);
        nodeOptional.ifPresent(stringNode -> assertEquals(node1, stringNode));
    }

    @Test public void getEdgeWithIter(){
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addEdge(node1, node2);
        graph.addEdge(node3, node1);
        List<Node<String>> edge = List.of(node1, node2);
        List<Node<String>> edgeForFind=graph.getEdgeWithIter(edge);
        assertEquals(edge, edgeForFind);
    }

    @Test public void deleteEdgeWithIter(){
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addEdge(node1, node2);
        graph.addEdge(node3, node1);
        List<Node<String>> edge = List.of(node1, node2);
        graph.deleteEdgeWithIter(edge);
        List<Integer> firstRow = Arrays.asList(-1);
        List<Integer> secondRow = Arrays.asList(0);
        List<Integer> thirdRow = Arrays.asList(1);
        List<List<Integer>> expected = Arrays.asList(firstRow, secondRow, thirdRow);
        List<List<Integer>> incidenceMatrix=graph.getIncidenceMatrix();
        assertEquals(expected, incidenceMatrix);
    }

    @Test public void deleteNodeWithIter(){
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addEdge(node1, node2);
        graph.addEdge(node3, node1);
        graph.deleteNodeWithIter(node1);
        List<Integer> firstRow = new ArrayList<>();
        List<Integer> secondRow = new ArrayList<>();
        List<List<Integer>> expected = Arrays.asList(firstRow, secondRow);
        List<List<Integer>> incidenceMatrix=graph.getIncidenceMatrix();
        assertEquals(expected, incidenceMatrix);
    }

    @Test public void getNodeWithReversedIter(){
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addEdge(node1, node2);
        graph.addEdge(node3, node1);
        Optional<Node<String>>  nodeOptional=graph.getNodeWithReversedIter(node1);
        nodeOptional.ifPresent(stringNode -> assertEquals(node1, stringNode));
    }

    @Test public void getEdgeWithReversedIter(){
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addEdge(node1, node2);
        graph.addEdge(node3, node1);
        List<Node<String>> edge = List.of(node1, node2);
        List<Node<String>> edgeForFind=graph.getEdgeWithReversedIter(edge);
        assertEquals(edge, edgeForFind);
    }

    @Test public void getAdjacentEdges(){
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        graph.addEdge(node1, node2);
        graph.addEdge(node3, node1);
        graph.addEdge(node4, node1);
        graph.addEdge(node5, node2);
        graph.addEdge(node5, node3);
        List<List<Node<String>>> adjacentEdges=graph.getAdjacentEdges(node1);
        assertEquals(3, adjacentEdges.size());
        assertTrue(adjacentEdges.contains(List.of(node1, node2)));
        assertTrue(adjacentEdges.contains(List.of(node3, node1)));
        assertTrue(adjacentEdges.contains(List.of(node4, node1)));
        assertFalse(adjacentEdges.contains(List.of(node1, node4)));
    }


    @Test public void getAdjacentNodes(){
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);
        graph.addEdge(node1, node2);
        graph.addEdge(node3, node1);
        graph.addEdge(node4, node1);
        graph.addEdge(node5, node2);
        graph.addEdge(node5, node3);
        List<Node<String>> adjacentEdges=graph.getAdjacentNodes(node1);
        assertEquals(3, adjacentEdges.size());
        assertTrue(adjacentEdges.contains(node2));
        assertTrue(adjacentEdges.contains(node3));
        assertTrue(adjacentEdges.contains(node4));
        assertFalse(adjacentEdges.contains(node5));
    }

}