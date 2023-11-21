package com.dezzzl;

import com.dezzzl.terator.*;

import java.util.*;

public class Graph<T> implements Iterable<T> {
    private final List<Node<T>> nodes = new ArrayList<>();
    private final List<List<Integer>> incidenceMatrix = new ArrayList<>();

    private final List<List<Node<T>>> edges = new ArrayList<>();

    private int countOfEdges;

    /**
     * Конструктор, создающий граф
     */

    public Graph() {
    }

    /**
     * Добавляет узел в граф
     *
     * @param node узел
     */
    public void addNode(Node<T> node) {
        nodes.add(node);
        node.setIndex(incidenceMatrix.size());
        if (incidenceMatrix.size() > 1) {
            incidenceMatrix.add(new ArrayList<>(Collections.nCopies(incidenceMatrix.get(0).size(), 0)));
        } else {
            incidenceMatrix.add(new ArrayList<>());
        }
    }

    /**
     * Возвращает все ребра графа
     *
     * @return все ребра графа
     **/
    public List<List<Node<T>>> getEdges() {
        return edges;
    }

    /**
     * Добавляет ребро между двумя вершинами
     *
     * @param from из какого узла
     * @param to   в какой узел
     */
    public void addEdge(Node<T> from, Node<T> to) {
        edges.add(List.of(from, to));
        countOfEdges++;
        int firstIndex = from.getIndex();
        int secondIndex = to.getIndex();
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
    public List<Node<T>> getNodes() {
        return nodes;
    }

    /**
     * Возвращает матрицу инцидентности графа
     *
     * @return все вершины графа
     */
    public List<List<Integer>> getIncidenceMatrix() {
        return incidenceMatrix;
    }

    /**
     * Возвращает узел по его идентификатору
     *
     * @param info идентификатор
     * @return узел по его идентификатору
     */
    public Optional<Node<T>> getNode(T info) {
        for (Node<T> node : nodes) {
            if (node.getInfo().equals(info)) {
                return Optional.of(node);
            }
        }
        return Optional.empty();
    }

    /**
     * Возвращает true, если узел присутствует в графе
     *
     * @param from узел из которого вышло ребро
     * @param to узел в который зашло ребро
     * @return true, если узел присутствует в графе
     */
    public boolean isEdgeInGraph(Node<T> from, Node<T> to) {
        for (int i = 0; i < countOfEdges; i++) {
            if (incidenceMatrix.get(from.getIndex()).get(i) == 1 && incidenceMatrix.get(to.getIndex()).get(i) == -1)
                return true;
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
            if (incidenceMatrix.get(node.getIndex()).get(i) != 0) counter++;
        }
        return counter;
    }


    /**
     *Удаляет ребро, если оно есть в графе
     * @param from узел из которого вышло ребро
     * @param to узел в который зашло ребро
     */
    public void deleteEdge(Node<T> from, Node<T> to) {
        if (!isEdgeInGraph(from, to)) return;

        edges.remove(Map.of(from, to));

        int indexOfEdge = getIndexOfEdge(from, to);

        for (int i = 0; i < incidenceMatrix.size(); i++) {
            incidenceMatrix.get(i).remove(indexOfEdge);
        }
        countOfEdges--;
    }

    private int getIndexOfEdge(Node<T> from, Node<T> to) {
        for (int i = 0; i < countOfEdges; i++) {
            if (incidenceMatrix.get(from.getIndex()).get(i) == 1 && incidenceMatrix.get(to.getIndex()).get(i) == -1)
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
            if (isEdgeInGraph(node, incidentNode))
                deleteEdge(node, incidentNode);
            else
                deleteEdge(incidentNode, node);
        }

        incidenceMatrix.remove(node.getIndex());

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
            if (incidenceMatrix.get(node.getIndex()).get(i) != 0)
                for (int j = 0; j < nodes.size(); j++) {
                    if (incidenceMatrix.get(j).get(i) != 0 && j != node.getIndex())
                        incidentNodes.add(nodes.get(j));
                }
        }
        return incidentNodes;
    }

    private void updateIndexes() {
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).setIndex(i);
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
    public List<Node<T>> getEdgeWithIter(List<Node<T>> edgeForFind) {
        EdgeIterator<T> edgeIterator = new EdgeIterator<>(this);
        while (edgeIterator.hasNext()) {
            List<Node<T>> edge = edgeIterator.next();
            if (edge.equals(edgeForFind)) return edge;
        }
        return new ArrayList<>();
    }


    /**
     *Удаляет ребро в графе с использованием итератора
     * @param edgeForDelete ребро, которое нужно удалить
     */
    public void deleteEdgeWithIter(List<Node<T>> edgeForDelete) {
        EdgeIterator<T> edgeIterator = new EdgeIterator<>(this);
        while (edgeIterator.hasNext()) {
            List<Node<T>> edge = edgeIterator.next();
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
    public List<Node<T>> getEdgeWithReversedIter(List<Node<T>> edgeForFind) {
        ReverseEdgeIterator<T> edgeIterator = new ReverseEdgeIterator<>(this);
        while (edgeIterator.hasNext()) {
            List<Node<T>> edge = edgeIterator.next();
            if (edge.equals(edgeForFind)) return edge;
        }
        return new ArrayList<>();
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
    public List<List<Node<T>>> getAdjacentEdges(Node<T> node) {
        AdjacentEdgesIterator<T> edgeIterator = new AdjacentEdgesIterator<>(this, node);
        List<List<Node<T>>>  adjacentEdges = new ArrayList<>();
        while (edgeIterator.hasNext()) {
           List<Node<T>> adjacentEdge = edgeIterator.next();
            adjacentEdges.add(adjacentEdge);
        }
        return adjacentEdges;
    }

}
