package net.sedixed.pochy.entity.ai;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.sedixed.pochy.entity.custom.PochyEntity;

public class PochyFloatGoal extends FloatGoal {
    private final PochyEntity entity;

    public PochyFloatGoal(Mob pMob) {
        super(pMob);
        entity = (PochyEntity) pMob;
    }

    @Override
    public void start() {
        entity.setSwimmingState(true);
        super.start();
    }

    @Override
    public boolean canUse() {
        return this.entity.isInWater()
                || this.entity.isInLava()
                || this.entity.isInFluidType(
                        (fluidType, height) -> this.entity.canSwimInFluidType(fluidType)
                                && height > this.entity.getFluidJumpThreshold());
    }

    @Override
    public void stop() {
        entity.setSwimmingState(false);
        super.stop();
    }

    @Override
    public void tick() {
        if (this.entity.getRandom().nextFloat() < 0.8F
                && this.entity.getFluidHeight(FluidTags.WATER) > this.entity.getFluidJumpThreshold()
        ) {
            this.entity.getJumpControl().jump();
        }
    }
}
