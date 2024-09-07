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
        entity.setRunning(true);
    }

    @Override
    public void stop() {
        entity.setRunning(false);
        super.stop();
    }
}
