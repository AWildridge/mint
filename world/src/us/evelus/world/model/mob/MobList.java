package us.evelus.world.model.mob;

import java.util.Iterator;

public final class MobList<T extends Mob> implements Iterable<T> {

    private Mob[] mobs;
    private int capacity;
    private int size = 0;

    private class MobListIterator implements Iterator<T> {

        private int currentId;
        private int found;

        @Override
        public boolean hasNext() {
            if(found == size) return false;

            while(currentId < capacity) {
                if(mobs[currentId++] != null) {
                    found++;
                    return true;
                }
            }
            return false;
        }

        @Override
        public T next() {
            return (T) mobs[currentId - 1];
        }

        @Override
        public void remove() {
            Mob mob = mobs[currentId];
            mobs[currentId] = null;
            mob.resetId();
            size--;
        }
    }

    public MobList(int capacity) {
        mobs = new Mob[capacity];
        this.capacity = capacity;
    }

    private void checkId(int id) {
        if(id < 1 || id >= mobs.length + 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public boolean add(T mob) {
        for(int i = 0; i < mobs.length; i++) {
            if(mobs[i] != null) {
                continue;
            }

            mobs[i] = mob;
            mob.setId(i + 1);
            size++;

            return true;
        }

        return false;
    }

    public boolean remove(T mob) {
        int id = mob.getId();
        checkId(id);

        Mob other = mobs[id - 1];
        if(other == null) {
            return false;
        }

        if(other.getId() != id) {
            throw new IllegalArgumentException("unmatched mob ids");
        }

        assert mob == other;

        mobs[id - 1] = null;
        mob.resetId();
        size--;

        return true;
    }

    public T get(int id) {
        checkId(id);
        return (T) mobs[id - 1];
    }

    @Override
    public Iterator<T> iterator() {
        return new MobListIterator();
    }

    public int size() {
        return size;
    }
}