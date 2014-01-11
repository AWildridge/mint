package us.evelus.world.model;

import us.evelus.util.QuadTree;
import us.evelus.world.model.Entity;
import us.evelus.world.model.Position;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

public final class EntityList<T extends Entity> implements Iterable<T> {

    private QuadTree<T> quadTree = new QuadTree<>();
    private Entity[] entities;
    private int capacity;
    private int size = 0;

    private class EntityListIterator implements Iterator<T> {

        private int currentId;
        private int found;

        @Override
        public boolean hasNext() {
            if(found == size) return false;

            while(currentId < capacity) {
                if(entities[currentId++] != null) {
                    found++;
                    return true;
                }
            }
            return false;
        }

        @Override
        public T next() {
            return (T) entities[currentId - 1];
        }

        @Override
        public void remove() {
            Entity entity = entities[currentId];
            entities[currentId] = null;
            entity.resetId();
            size--;
        }
    }

    public EntityList(int capacity) {
        entities = new Entity[capacity];
        this.capacity = capacity;
    }

    private void checkId(int id) {
        if(id < 1 || id >= entities.length + 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public boolean add(T entity) {
        for(int i = 0; i < entities.length; i++) {
            if(entities[i] != null) {
                continue;
            }

            entities[i] = entity;
            entity.setId(i + 1);
            size++;

            return true;
        }

        return false;
    }

    public boolean remove(T entity) {
        int id = entity.getId();

        Entity other = entities[id = 1];
        assert other == entity;

        remove(id);
        return true;
    }

    public boolean remove(int id) {
        checkId(id);

        Entity entity = entities[id - 1];
        if(entity == null) {
            return false;
        }

        if(entity.getId() != id) {
            throw new IllegalArgumentException("unmatched entity ids");
        }

        entities[id - 1] = null;
        entity.resetId();
        size--;

        return true;
    }

    public List<T> within(Rectangle rectangle) {
        return quadTree.query(rectangle);
    }

    public void rebuild() {
        QuadTree<T> tree = new QuadTree<>();
        for(T entity : this) {
            Position position = entity.getPosition();
            tree.insert(entity, position.x(), position.y());
        }
        quadTree = tree;
    }

    public T get(int id) {
        checkId(id);
        return (T) entities[id - 1];
    }

    @Override
    public Iterator<T> iterator() {
        return new EntityListIterator();
    }

    public int size() {
        return size;
    }
}