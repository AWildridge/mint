package us.evelus.world.model;

import us.evelus.world.model.region.Region;
import us.evelus.world.model.region.RegionLoader;

import java.util.*;

public final class RegionRepository {

    private Map<Integer, Region> regions = new HashMap<>();
    private Queue<Region> regionQueue = new PriorityQueue<>();

    public void tick() {
        Iterator<Region> iterator = regionQueue.iterator();
        while(iterator.hasNext()) {
            Region region = iterator.next();
            if(!region.dispose()) {
                break;
            }
            Position position = region.getPosition();
            regions.remove(position.hash18());
            iterator.remove();
        }
    }

    public Region get(Position position) {
        if(!regions.containsKey(position.hash18())) {
            Region region = RegionLoader.load(position);

            // Register the region to the map and queue
            regions.put(position.hash18(), region);
            regionQueue.add(region);
        }

        return regions.get(position);
    }
}
