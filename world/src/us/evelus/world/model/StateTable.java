package us.evelus.world.model;

import java.util.LinkedList;
import java.util.List;

public final class StateTable {

    private final List<StateTableListener> listeners = new LinkedList<>();
    private boolean alertListeners = true;
    private int[] states;
    private int capacity;

    public StateTable() {
        this(100);
    }

    public StateTable(int capacity) {
        states = new int[capacity];
        this.capacity = capacity;
    }

    private void checkId(int id) {
        if(id < 0 || id >= capacity) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void attach(StateTableListener listener) {
        listeners.add(listener);
    }

    public void detach(StateTableListener listener) {
        listeners.remove(listener);
    }

    public void add(String name, int amt) {
        StateSymbol symbol = StateSymbol.fromName(name);
        add(symbol.getId(), amt);
    }

    public void add(int id, int amt) {
        set(id, states[id] + amt);
    }

    public void sub(String name, int amt) {
        StateSymbol symbol = StateSymbol.fromName(name);
        sub(symbol.getId(), amt);
    }

    public void sub(int id, int amt) {
        set(id, states[id] - amt);
    }

    public void mul(String name, int amt) {
        StateSymbol symbol = StateSymbol.fromName(name);
        mul(symbol.getId(), amt);
    }

    public void mul(int id, int amt) {
        set(id, states[id] * amt);
    }

    public void div(String name, int amt) {
        StateSymbol symbol = StateSymbol.fromName(name);
        div(symbol.getId(), amt);
    }

    public void div(int id, int amt) {
        set(id, states[id] / amt);
    }

    public void set(String name, int amt) {
        StateSymbol symbol = StateSymbol.fromName(name);
        set(symbol.getId(), amt);
    }

    public boolean alertListeners() {
        return alertListeners;
    }

    public void toggleAlertListener() {
        alertListeners = !alertListeners;
    }

    public void set(int id, int value) {
        checkId(id);

        int oldValue = states[id];
        states[id] = value;

        if(alertListeners) {

            for(StateTableListener listener : listeners) {
                listener.updated(id, oldValue, value);
            }
        }
    }
}
