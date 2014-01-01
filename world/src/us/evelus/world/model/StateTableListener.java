package us.evelus.world.model;

import org.jruby.RubySymbol;

public abstract class StateTableListener {

    private static final int UNSET = -1;
    private int id;

    public StateTableListener() {
        this(UNSET);
    }

    public StateTableListener(RubySymbol symbol) {
        this(symbol.asJavaString());
    }

    public StateTableListener(String name) {
        this(StateSymbol.fromName(name).getId());
    }

    public StateTableListener(int id) {
        this.id = id;
    }

    public void updated(int updatedId, int oldValue, int newValue) {
        if(id == UNSET || updatedId == id) {
            valueUpdated(updatedId, oldValue, newValue);
        }
    }

    protected abstract void valueUpdated(int id, int oldValue, int newValue);
}
