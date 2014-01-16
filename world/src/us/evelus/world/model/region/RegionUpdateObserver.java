package us.evelus.world.model.region;

import us.evelus.world.model.mob.Mob;
import us.evelus.world.model.mob.MobObserverAdapter;

public final class RegionUpdateObserver extends MobObserverAdapter {

    private final RegionRepository repository;

    public RegionUpdateObserver(RegionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void active(Mob mob) {
        Region region = repository.load(mob.getPosition());
        region.updated();
    }
}
