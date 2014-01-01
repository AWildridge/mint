package us.evelus.world.util;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public final class QuadTree<E extends Object> {

    private Node root;

    private class Node {

        private E value;
        private int x, y;
        private Node ne, nw, se, sw;

        Node(E value, int x, int y) {
            this.value = value;
            this.x = x;
            this.y = y;
        }
    }

    public QuadTree() {}

    public void insert(E value, int x, int y) {
        root = insert(root, value, x, y);
    }

    private Node insert(Node node, E value, int x, int y) {
        if(node == null) return new Node(value, x, y);
        if(x == node.x && y == node.y) node.value = value;
        else if(x < node.x  && y  < node.y) node.sw = insert(node.sw, value, x, y);
        else if(x < node.x  && y >= node.y) node.nw = insert(node.nw, value, x, y);
        else if(x >= node.x && y  < node.y) node.se = insert(node.se, value, x, y);
        else if(x >= node.x && y >= node.y) node.ne = insert(node.ne, value, x, y);
        return node;
    }

    public List<E> query(Rectangle rectangle) {
        if(root == null) return null;
        List<E> list = new LinkedList<>();
        query(list, root, rectangle);
        return list;
    }

    private void query(List<E> list, Node node, Rectangle rectangle) {
        if(node == null) return;
        if(rectangle.contains(node.x, node.y)) list.add(node.value);
        else if(rectangle.getMinX() < node.x  && rectangle.getMinY() < node.y)  query(list, node.sw, rectangle);
        else if(rectangle.getMinX() < node.x  && rectangle.getMaxY() >= node.y) query(list, node.nw, rectangle);
        else if(rectangle.getMaxX() >= node.x && rectangle.getMinY()  < node.y) query(list, node.se, rectangle);
        else if(rectangle.getMaxX() >= node.x && rectangle.getMaxY() >= node.y) query(list, node.ne, rectangle);
    }

    public List<E> query(int x, int y) {
        if(root == null) return null;
        List<E> list = new LinkedList<>();
        query(list, root, x, y);
        return list;
    }

    private void query(List<E> list, Node node, int x, int y) {
        if(node == null) return;
        if(node.x == x && node.y == y) list.add(node.value);
        else if(x < node.x  && y  < node.y) query(list, node.sw, x, y);
        else if(x < node.x  && y >= node.y) query(list, node.nw, x, y);
        else if(x >= node.x && y  < node.y) query(list, node.se, x, y);
        else if(x >= node.x && y >= node.y) query(list, node.ne, x, y);                                                      // ?
    }
}
