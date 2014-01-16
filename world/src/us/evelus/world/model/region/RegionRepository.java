package us.evelus.world.model.region;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.evelus.world.model.Position;

import java.util.*;

public final class RegionRepository {

    private Logger logger = LoggerFactory.getLogger(RegionRepository.class);

    private Map<Integer, Region> regions = new HashMap<>();
    private Queue<Region> regionQueue = new PriorityQueue<>();

    public void purge() {
        Iterator<Region> iterator = regionQueue.iterator();
        while(iterator.hasNext()) {

            // Get the next region from the iterators
            Region region = iterator.next();

            // If the region cannot be disposed of then stop looping
            if(!region.dispose()) {
                break;
            }

            Position position = region.getPosition();

            // Remove the region from the regions map and the queue
            regions.remove(position.hash18());
            iterator.remove();

            logger.info("Region @ '" + position + "' purged...");
        }
    }

    public Region load(Position position) {

        Region region = regions.get(position.hash18());

        if(region == null) {

            // Load the region from the loader
            region = RegionLoader.load(position);

            // Register the region to the map and queue
            regions.put(position.hash18(), region);
            regionQueue.add(region);

            logger.info("Region @ '" + position + "' loaded...");
        }

        return region;
    }

    public Region get(Position position) {
        return regions.get(position.hash18());
    }
}
