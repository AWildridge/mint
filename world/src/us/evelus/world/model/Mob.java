package us.evelus.world.model;

import org.jruby.RubySymbol;
import us.evelus.world.interact.InteractionHandler;

public abstract class Mob extends Entity {

    private StateTable stateTable = new StateTable();
    private InteractionHandler interactionHandler;
    private int id = -1;

    public void setInteractionHandler(InteractionHandler handler) {
        interactionHandler = handler;
    }

    public void interact(String action, Player target) {
        interactionHandler.handlePlayerInteraction(this, action, target);
    }

    public void interact(String action, GameObject obj) {
        interactionHandler.handleObjInteraction(this, action, obj);
    }

    public void add(RubySymbol symbol, int amt) {
        stateTable.add(symbol.asJavaString(), amt);
    }

    public void add(String name, int amt) {
        stateTable.add(name, amt);
    }

    public void sub(RubySymbol symbol, int amt) {
        stateTable.sub(symbol.asJavaString(), amt);
    }

    public void sub(String name, int amt) {
        stateTable.sub(name, amt);
    }

    public void mul(RubySymbol symbol, int amt) {
        stateTable.mul(symbol.asJavaString(), amt);
    }

    public void mul(String name, int amt) {
        stateTable.mul(name, amt);
    }

    public void div(RubySymbol symbol, int amt) {
        stateTable.div(symbol.asJavaString(), amt);
    }

    public void div(String name, int amt) {
        stateTable.div(name, amt);
    }

    public void set(RubySymbol symbol, int amt) {
        stateTable.set(symbol.asJavaString(), amt);
    }

    public void set(String name, int amt) {
        stateTable.set(name, amt);
    }

    public StateTable getStateTable() {
        return stateTable;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void resetId() {
        id = -1;
    }
}
