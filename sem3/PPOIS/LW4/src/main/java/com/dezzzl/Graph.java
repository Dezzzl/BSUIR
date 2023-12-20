package com.dezzzl;

import com.dezzzl.exceptions.EdgeNotFoundException;
import com.dezzzl.iterator.*;

import java.util.*;

public class Graph<T> implements Iterable<T> {
    private final Map<Node<T>, Integer> nodes = new HashMap<>();
    private final List<List<Integer>> incidenceMatrix = new ArrayList<>();

    private  int countOfEdges;

    /**
     * Конструктор, создающий граф
     */

    public Graph() {
    }

    /**
     * Возвращает ребро по его индексу в матрице инцидентности
     *
     * @param countOfEdge индекс ребра в матрице инцидентности
     *
     * @return ребро по его индексу в матрице инцидентности
     */
    public Edge<T> getEdge(int countOfEdge) throws EdgeNotFoundException {
        if (countOfEdge>countOfEdges||countOfEdge<0)throw new EdgeNotFoundException("Ребро не существует");
        int indexOfOutNode=0;
        int indexOfInNode=0;
        for (int i =0; i<countOfEdges; i++){
            if(incidenceMatrix.get(i).get(countOfEdge)==-1)indexOfInNode=i;
            if(incidenceMatrix.get(i).get(countOfEdge)==1)indexOfOutNode=i;
        }
        return new Edge<>(getNodeByIndex(indexOfOutNode).get(), getNodeByIndex(indexOfInNode).get());
    }
    /**
     * Возвращает узел по его индексу в матрице инцидентности
     *
     * @param index индекс узла в матрице инцидентности
     *
     * @return узел по его индексу в матрице инцидентности
     */
    public Optional<Node<T>> getNodeByIndex(int index) {
        for (Map.Entry<Node<T>, Integer> entry : nodes.entrySet()) {
            if (entry.getValue() == index) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }
    /**
     * Добавляет узел в граф
     *
     * @param node узел
     */

    public void addNode(Node<T> node) {
        Optional<Node<T>> nodeOptional = getNode(node.getInfo());
        if (nodeOptional.isPresent())return;
        nodes.put(node, nodes.size());
        if (incidenceMatrix.size() > 1) {
            incidenceMatrix.add(new ArrayList<>(Collections.nCopies(incidenceMatrix.get(0).size(), 0)));
        } else {
            incidenceMatrix.add(new ArrayList<>());
        }
    }

    /**
     * Добавляет ребро между двумя вершинами
     * @param edge ребро
     */
    public void addEdge(Edge<T> edge) {
        try {
            if (isEdgeInGraph(edge))return;
        } catch (EdgeNotFoundException e) {
            return;
        }
        countOfEdges++;
        int firstIndex = nodes.get(edge.getFrom());
        int secondIndex = nodes.get(edge.getTo());
        for (List<Integer> row : incidenceMatrix) {
            row.add(0);
        }
        int size = incidenceMatrix.get(0).size();
        incidenceMatrix.get(firstIndex).set(size - 1, 1);
        incidenceMatrix.get(secondIndex).set(size - 1, -1);
    }

    /**
     * Возвращает количество ребер в графе
     *
     * @return количество ребер в графе
     */
    public int getCountOfEdges() {
        return countOfEdges;
    }

    /**
     * Возвращает количество вершин в графе
     *
     * @return количество вершин в графе
     */
    public int getCountOfNodes() {
        return incidenceMatrix.size();
    }


    /**
     * Возвращает все вершины графа
     *
     * @return все вершины графа
     */
    public Map<Node<T>, Integer> getNodes() {
        return Map.copyOf(nodes);
    }

    /**
     * Возвращает матрицу инцидентности графа
     *
     * @return все вершины графа
     */
    public List<List<Integer>> getIncidenceMatrix() {
        List<List<Integer>> matrix = new ArrayList<>();
        for (List<Integer> integers : incidenceMatrix) {
            List<Integer> row = List.copyOf(integers);
            matrix.add(row);
        }
        return matrix;
    }

    /**
     * Возвращает узел по его идентификатору
     *
     * @param info идентификатор
     * @return узел по его идентификатору
     */
    public Optional<Node<T>> getNode(T info) {
        return nodes.keySet().stream()
                .filter(node -> node.getInfo().equals(info))
                .findFirst();
    }

    /**
     * Возвращает true, если ребро присутствует в графе
     *
     * @param edge ребро
     * @return true, если ребпо присутствует в графе
     */
    public boolean isEdgeInGraph(Edge<T> edge) throws EdgeNotFoundException {
        try{
            if (!isNodeInGraph(edge.getFrom()))return false;
            if (!isNodeInGraph(edge.getTo()))return false;
        } catch (Exception e) {
            throw new EdgeNotFoundException("Ребро не существует");
        }
        for (int i = 0; i < countOfEdges; i++) {
            if (incidenceMatrix.get(nodes.get(edge.getFrom())).get(i) == 1 && incidenceMatrix.get(nodes.get(edge.getTo())).get(i) == -1)
                return true;
        }
        return false;
    }

    private boolean isNodeInGraph(Node<T> node){
            for (Map.Entry<Node<T>, Integer> entry : nodes.entrySet()) {
                if (entry.getKey() == node) {
                    return true;
                }
            }
            return false;
    }

    /**
     * Возвращает степень вершины
     *
     * @param node узел, для которого мы хотим найти степень
     * @return степень вершины
     */
    public int getDegreeOfNode(Node<T> node) {
        int counter = 0;
        for (int i = 0; i < countOfEdges; i++) {
            if (incidenceMatrix.get(nodes.get(node)).get(i) != 0) counter++;
        }
        return counter;
    }


    /**
     *Удаляет ребро, если оно есть в графе
     * @param edge ребро
     */
    public void deleteEdge(Edge<T> edge) {
        try {
            if (!isEdgeInGraph(edge)) return;
        } catch (EdgeNotFoundException e) {
            return;
        }

        int indexOfEdge = getIndexOfEdge(edge);

        for (int i = 0; i < incidenceMatrix.size(); i++) {
            incidenceMatrix.get(i).remove(indexOfEdge);
        }
        countOfEdges--;
    }

    private int getIndexOfEdge(Edge<T> edge) {
        try {
            isEdgeInGraph(edge);
        } catch (EdgeNotFoundException e) {
            return -1;
        }
        for (int i = 0; i < countOfEdges; i++) {
            if (incidenceMatrix.get(nodes.get(edge.getFrom())).get(i) == 1 && incidenceMatrix.get(nodes.get(edge.getTo())).get(i) == -1)
                return i;
        }
        return -1;
    }

    /**
     *Удаляет узел в графе
     * @param node вершина, которую нужно удалить
     */
    public void deleteNode(Node<T> node) {
        List<Node<T>> incidentNodes = getIncidentNodes(node);

        for (Node<T> incidentNode : incidentNodes) {
            try {
                if (isEdgeInGraph(new Edge<>(node, incidentNode)))
                    deleteEdge(new Edge<>(node, incidentNode));
                else
                    deleteEdge(new Edge<>(incidentNode, node));
            } catch (EdgeNotFoundException e) {
                return;
            }
        }

        incidenceMatrix.remove(nodes.get(node).intValue());

        nodes.remove(node);

        updateIndexes();
    }

    /**
     *Возвращает список вершин, смежных с данной
     * @param node вершина, для которой нужно найти смежные
     */
     public List<Node<T>> getIncidentNodes(Node<T> node) {
        List<Node<T>> incidentNodes = new ArrayList<>();
        for (int i = 0; i < countOfEdges; i++) {
            if (incidenceMatrix.get(nodes.get(node)).get(i) != 0)
                for (int j = 0; j < nodes.size(); j++) {
                    if (incidenceMatrix.get(j).get(i) != 0 && j != nodes.get(node)) {
                        Optional<Node<T>> nodeOptional = getNodeByIndex(j);
                        nodeOptional.ifPresent(incidentNodes::add);
                    }
                }
        }
        return incidentNodes;
    }

    private void updateIndexes() {
        for (int i = 0; i < nodes.size(); i++) {
            Optional<Node<T>> nodeOptional = getNodeByIndex(i);
            if (nodeOptional.isPresent()) {
                Node<T> node = nodeOptional.get();
                nodes.replace(node, i);
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return (Iterator<T>) new NodeIterator<T>(this);
    }

    /**
     * Возвращает итератор по вершинам
     * @return итератор по вершинам
     */
    public NodeIterator<T> getNodeIterator() {
        return new NodeIterator<T>(this);
    }

    /**
     * Возвращает итератор по ребрам
     * @return итератор по ребрам
     */
    public EdgeIterator<T> getEdgesIterator() {
        return new EdgeIterator<>(this);
    }


    /**
     * Возвращает узел по его идентификатору с использованием итератора
     * @param nodeForFind идентификатор
     * @return узел по его идентификатору
     */
    public Optional<Node<T>> getNodeWithIter(Node<T> nodeForFind) {
        NodeIterator<T> nodeIterator = getNodeIterator();
        while (nodeIterator.hasNext()) {
            Node<T> edge = nodeIterator.next();
            if (nodeForFind.equals(edge)) return Optional.of(edge);
        }
        return Optional.empty();
    }

    /**
     * Возвращает ребро по двум вершинам с использованием итератора
     * @param edgeForFind ребро, которое нужно найти
     * @return ребро по двум вершинам с использованием итератора
     */
    public Optional<Edge<T>> getEdgeWithIter(Edge<T> edgeForFind) {
        EdgeIterator<T> edgeIterator = new EdgeIterator<>(this);
        while (edgeIterator.hasNext()) {
            Edge<T> edge = edgeIterator.next();
            if (edge.equals(edgeForFind)) return Optional.of(edge);
        }
        return Optional.empty();
    }


    /**
     *Удаляет ребро в графе с использованием итератора
     * @param edgeForDelete ребро, которое нужно удалить
     */
    public void deleteEdgeWithIter(Edge<T> edgeForDelete) {
        EdgeIterator<T> edgeIterator = new EdgeIterator<>(this);
        while (edgeIterator.hasNext()) {
            Edge<T> edge = edgeIterator.next();
            if (edgeForDelete.equals(edge)) {
                edgeIterator.remove();
            }
        }
    }

    /**
     *Удаляет вершину в графе с использованием итератора
     * @param nodeForDelete вершина, которую нужно удалить
     */
   public void deleteNodeWithIter(Node<T> nodeForDelete) {
        NodeIterator<T> nodeIterator = new NodeIterator<>(this);
        while (nodeIterator.hasNext()) {
            Node<T> node = nodeIterator.next();
            if (nodeForDelete.equals(node)) {
                nodeIterator.remove();
            }
        }
    }


    /**
     * Возвращает узел по его идентификатору с использованием Reversed итератора
     * @param nodeForFind идентификатор
     * @return узел по его идентификатору
     */
    public Optional<Node<T>> getNodeWithReversedIter(Node<T> nodeForFind) {
        ReverseNodeIterator<T> nodeIterator = new ReverseNodeIterator<>(this);
        while (nodeIterator.hasNext()) {
            Node<T> edge = nodeIterator.next();
            if (nodeForFind.equals(edge)) return Optional.of(edge);
        }
        return Optional.empty();
    }

    /**
     * Возвращает ребро по двум вершинам с использованием Reversed итератора
     * @param edgeForFind ребро, которое нужно найти
     * @return ребро по двум вершинам с использованием итератора
     */
    public Optional<Edge<T>> getEdgeWithReversedIter(Edge<T> edgeForFind) {
        ReverseEdgeIterator<T> edgeIterator = new ReverseEdgeIterator<>(this);
        while (edgeIterator.hasNext()) {
            Edge<T> edge = edgeIterator.next();
            if (edge.equals(edgeForFind)) return Optional.of(edge);
        }
        return Optional.empty();
    }
    /**
     * Возвращает смежные вершины с данной
     * @param node вершина, для которой ищутся смежные
     * @return смежные вершины с данной
     */
    public List<Node<T>> getAdjacentNodes(Node<T> node) {
        AdjacentVerticesIterator<T> nodeIterator = new AdjacentVerticesIterator<>(this, node);
        List<Node<T>> adjacentNodes = new ArrayList<>();
        while (nodeIterator.hasNext()) {
            Node<T> adjacentNode = nodeIterator.next();
            adjacentNodes.add(adjacentNode);
        }
        return adjacentNodes;
    }

    /**
     * Возвращает смежные ребра с данной вершиной
     * @param node вершина, для которой ищатся смежные ребра
     * @return смежные ребра с данной вершиной
     */
    public List<Edge<T>> getAdjacentEdges(Node<T> node) {
        AdjacentEdgesIterator<T> edgeIterator = new AdjacentEdgesIterator<>(this, node);
        List<Edge<T>>  adjacentEdges = new ArrayList<>();
        while (edgeIterator.hasNext()) {
           Edge<T> adjacentEdge = edgeIterator.next();
            adjacentEdges.add(adjacentEdge);
        }
        return adjacentEdges;
    }

}
