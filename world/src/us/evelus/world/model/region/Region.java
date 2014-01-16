package us.evelus.world.model.region;

import io.netty.buffer.ByteBuf;
import us.evelus.world.model.Position;
import us.evelus.world.model.obj.SceneObject;
import us.evelus.world.model.pf.TraversalMap;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class Region implements Comparable<Region> {

    public static final long DISPOSE_TIME = 50000L;
    public static final int BLOCKED_FLAG = 0x0;

    private Map<Position, SceneObject> objects = new LinkedHashMap<>();
    private TraversalMap traversalMap = new TraversalMap(64);
    private Position position;

    private List<RegionObserver> observers = new LinkedList<>();

    private byte[] areaFlags = new byte[4096];
    private int[] areaIds = new int[8];

    private byte[] triggerFlags = new byte[4096];
    private int[] triggerIds = new int[8];

    private byte[] tileFlags = new byte[4096];
    private long lastUpdatedTime;

    public Region(Position pos) {
        attach(new RegionClipObserver(traversalMap));
        lastUpdatedTime = System.currentTimeMillis();
        position = pos;
    }

    public void attach(RegionObserver listener) {
        observers.add(listener);
    }

    public void detach(RegionObserver listener) {
        observers.remove(listener);
    }

    public void decodeObjectData(ByteBuf buf) {
        while(buf.isReadable()) {
            Position position = Position.fromHash14(buf.readShort());
            int type = buf.readShort();

            SceneObject object = new SceneObject();
            object.setPosition(position);

            // Only store objects that are interactable into the region
            if(object.isInteractable()) {
                objects.put(position, object);
            }

            for(RegionObserver observer : observers) {
                observer.sceneObjectAdded(this, position, object);
            }
        }
    }

    public void decodeTileData(ByteBuf buf) {
        while(buf.isReadable()) {
            int hash = buf.readShort();
            byte flag = buf.readByte();
            tileFlags[hash] = flag;

            Position position = Position.fromHash14(hash);

            for(RegionObserver listener : observers) {
                listener.flagSet(this, position, flag);
            }
        }
    }

    public void decodeAreaData(ByteBuf buf) {
        while(buf.isReadable()) {

            // Read the opcode from the buffer
            int opcode = buf.readByte();
            if(opcode == 0) {
                break;
            }

            // Reserved for setting an area id
            if(opcode == 1) {
                areaIds[buf.readByte()] = buf.readShort();
            }

            // Reserved for updating an area flag
            if(opcode == 2) {
                areaFlags[buf.readShort()] = buf.readByte();
            }
        }
    }

    public void decodeTriggerData(ByteBuf buf) {
        while(buf.isReadable()) {

            // Read the opcode from the buffer
            int opcode = buf.readByte();
            if(opcode == 0) {
                break;
            }

            // Reserved for setting a trigger id
            if(opcode == 1) {
                triggerIds[buf.readByte()] = buf.readShort();
            }

            // Reserved for updating a trigger flag
            if(opcode == 2) {
                triggerFlags[buf.readShort()] = buf.readByte();
            }
        }
    }

    /**
     * Gets if the region should be disposed.
     *
     * @return
     *          If the region should be disposed of.
     */
    public boolean dispose() {
        long diff = System.currentTimeMillis() - lastUpdatedTime;
        return diff >= DISPOSE_TIME;
    }

    public void updated() {
        lastUpdatedTime = System.currentTimeMillis();
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public int compareTo(Region other) {
        if(lastUpdatedTime > other.lastUpdatedTime) {
            return 1;
        }

        if(lastUpdatedTime < other.lastUpdatedTime) {
            return -1;
        }

        return 0;
    }
}
