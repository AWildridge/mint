package us.evelus.world.model;

import us.evelus.world.model.mob.Mob;
import us.evelus.world.model.mob.MobObserverAdapter;
import us.evelus.world.model.region.Region;

public final class MobActiveObserver extends MobObserverAdapter {

    private final RegionRepository repository;

    public MobActiveObserver(RegionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void active(Mob mob) {
        Region region = repository.get(mob.getPosition());
        region.markTime();
    }
}
