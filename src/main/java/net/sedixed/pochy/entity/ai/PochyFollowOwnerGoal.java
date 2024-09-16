package net.sedixed.pochy.entity.ai;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.sedixed.pochy.entity.custom.PochyEntity;

public class PochyFollowOwnerGoal extends FollowOwnerGoal {
    private final PochyEntity entity;

    public PochyFollowOwnerGoal(TamableAnimal pTamable, double pSpeedModifier, float pStartDistance, float pStopDistance, boolean pCanFly) {
        super(pTamable, pSpeedModifier, pStartDistance, pStopDistance, pCanFly);
        entity = (PochyEntity) pTamable;
    }

    @Override
    public void start() {
        super.start();
        if (entityIsInFluid()) {
            entity.setSwimmingState(true);
        } else {
            entity.setRunning(true);
        }
    }

    @Override
    public void stop() {
        entity.setRunning(false);
        super.stop();
    }

    @Override
    public void tick() {
        super.tick();
        // Animation state update
        if (entityIsInFluid()) {
            if (!entity.isSwimming()) {
                entity.setSwimmingState(true);
            }
            if (entity.isRunning()) {
                entity.setRunning(false);
            }

        } else {
            if (entity.isSwimming()) {
                entity.setSwimmingState(false);
            }
            if (!entity.isRunning()) {
                entity.setRunning(true);
            }
        }
    }

    private boolean entityIsInFluid() {
        return this.entity.isInWater() ||
                this.entity.isInLava() ||
                this.entity.isInFluidType(
                        (fluidType, height) -> this.entity.canSwimInFluidType(fluidType) &&
                                height > this.entity.getFluidJumpThreshold());
    }
}
