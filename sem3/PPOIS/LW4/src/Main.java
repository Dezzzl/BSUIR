import java.util.ArrayList;

public class Main {
    public static void main(String[] args)
    {
        Node<String>node1 = new Node<>("node1");
        Node<String>node2 = new Node<>("node2");
        Node<String>node3 = new Node<>("node3");
        Node<String>node4 = new Node<>("node4");
        Node<String>node5 = new Node<>("node5");

        Graph<String> graph = new Graph<>(new ArrayList<>(), new ArrayList<>(), 0);

        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);
        graph.addNode(node5);

        graph.addEdge(node1, node2);
        graph.addEdge(node2, node3);
        graph.addEdge(node3, node4);
        graph.addEdge(node4, node1);
        graph.addEdge(node1, node5);
//
//        System.out.println(graph.getNode("node2"));
//
//        System.out.println(graph.isEdgeInGraph(node1, node2));
//
//        System.out.println(graph.getDegreeOfNode(node2));
        graph.showMatrix();

        graph.deleteNode(node1);

//        graph.deleteEdge(node1, node2);
        System.out.println();
        graph.showMatrix();


    }
}