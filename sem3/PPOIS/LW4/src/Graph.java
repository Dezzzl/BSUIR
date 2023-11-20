import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Graph<T> {
    private List<Node<T>> nodes;
    private List<List<Integer>> incidenceMatrix;

    private int countOfEdges;

    public Graph(List<Node<T>> nodes, List<List<Integer>> incidenceMatrix, int countOfEdges) {
        this.nodes = nodes;
        this.incidenceMatrix = incidenceMatrix;
        this.countOfEdges=0;
    }

    public void addNode(Node<T> node){
        nodes.add(node);
        node.setIndex(incidenceMatrix.size());
        if(incidenceMatrix.size()>1) {
            incidenceMatrix.add(new ArrayList<>(Collections.nCopies(incidenceMatrix.get(0).size(), 0)));
        }
        else{
            incidenceMatrix.add(new ArrayList<>());
        }
    }

    public void addEdge(Node<T> from, Node<T> to){
        countOfEdges++;
        int firstIndex = from.getIndex();
        int secondIndex = to.getIndex();
        for (List<Integer> row : incidenceMatrix) {
            row.add(0);
        }
        int size = incidenceMatrix.get(0).size();
        incidenceMatrix.get(firstIndex).set(size-1, 1);
        incidenceMatrix.get(secondIndex).set(size-1, -1);
    }

    public void showMatrix(){
        for (List<Integer> row : incidenceMatrix) {
            for (Integer element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
    public int getCountOfEdges(){
        return countOfEdges;
    }

    public int getCountOfNodes(){
        return incidenceMatrix.size();
    }

    public Optional<Node<T>> getNode(T info) {
        for (Node<T> node : nodes) {
            if (node.getInfo().equals(info)) {
                return Optional.of(node);
            }
        }
        return Optional.empty();
    }

    public boolean isEdgeInGraph(Node<T> from, Node<T> to){
        for (int i=0; i<countOfEdges; i++){
            if (incidenceMatrix.get(from.getIndex()).get(i)==1 && incidenceMatrix.get(to.getIndex()).get(i)==-1)
                return true;
        }
        return false;
    }

    public int getDegreeOfNode(Node<T> node){
        int counter=0;
        for (int i=0; i<countOfEdges; i++){
            if (incidenceMatrix.get(node.getIndex()).get(i)!=0)counter++;
        }
        return counter;
    }

    public void deleteEdge(Node<T>from, Node<T> to){
        if (!isEdgeInGraph(from, to))return;

        int indexOfEdge = getIndexOfEdge(from, to);

        for (int i = 0; i< incidenceMatrix.size(); i++){
            incidenceMatrix.get(i).remove(indexOfEdge);
        }
        countOfEdges--;
    }

    private int getIndexOfEdge(Node<T> from, Node<T> to){
        for (int i=0; i<countOfEdges; i++){
            if (incidenceMatrix.get(from.getIndex()).get(i)==1 && incidenceMatrix.get(to.getIndex()).get(i)==-1)
                return i;
        }
        return -1;
    }

    public void deleteNode(Node<T> node){
        List<Node<T>> incidentNodes = getIncidentNodes(node);

        for(Node<T> incidentNode : incidentNodes){
            if (isEdgeInGraph(node, incidentNode))
                deleteEdge(node, incidentNode);
            else
                deleteEdge(incidentNode, node);
        }

        incidenceMatrix.remove(node.getIndex());

        nodes.remove(node);
    }

   public List<Node<T>> getIncidentNodes(Node<T> node){
        List<Node<T>> incidentNodes = new ArrayList<>();
        for (int i=0; i<countOfEdges; i++){
            if(incidenceMatrix.get(node.getIndex()).get(i)!=0)
                for(int j=0; j<nodes.size(); j++){
                    if (incidenceMatrix.get(j).get(i)!=0&&j!=node.getIndex())
                        incidentNodes.add(nodes.get(j));
                }
        }
        return incidentNodes;
   }


}
