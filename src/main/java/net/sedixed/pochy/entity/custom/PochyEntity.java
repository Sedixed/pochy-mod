package net.sedixed.pochy.entity.custom;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.fluids.FluidType;
import net.sedixed.pochy.entity.ai.PochyFloatGoal;
import net.sedixed.pochy.entity.ai.PochyFollowOwnerGoal;
import net.sedixed.pochy.entity.variant.PochyVariant;
import net.sedixed.pochy.sound.ModSounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PochyEntity extends TamableAnimal implements InventoryCarrier {
    /**
     * Pochy's inventory.
     */
    private final SimpleContainer inventory = new SimpleContainer(27);

    /**
     * Animation states.
     */
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState swimAnimationState = new AnimationState();
    private int swimAnimationTimeout = 0;

    public final AnimationState runAnimationState = new AnimationState();
    private int runAnimationTimeout = 0;

    /**
     * Synced data.
     */
    private static final EntityDataAccessor<Boolean> SWIMMING =
            SynchedEntityData.defineId(PochyEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> RUNNING =
            SynchedEntityData.defineId(PochyEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT =
            SynchedEntityData.defineId(PochyEntity.class, EntityDataSerializers.INT);

    public PochyEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(SWIMMING, false);
        entityData.define(RUNNING, false);
        entityData.define(DATA_ID_TYPE_VARIANT, 0);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new PochyFloatGoal(this));
        goalSelector.addGoal(0, new PochyFollowOwnerGoal(this, 1.65f, 8, 2, true));
        goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 10.0f));
        goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.85f));
        goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes()  {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 100);
    }

    /**
     * State/animation update.
     */
    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            setupAnimationStates();
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        walkAnimation.update(
                getPose() == Pose.STANDING ? Math.min(pPartialTick * 6f, 1f) : 0f,
                0.2f
        );
    }

    public void setSwimmingState(boolean swimming) {
        entityData.set(SWIMMING, swimming);
    }

    public boolean isSwimming() {
        return entityData.get(SWIMMING);
    }

    public void setRunning(boolean running) {
        entityData.set(RUNNING, running);
    }

    public boolean isRunning() {
        return entityData.get(RUNNING);
    }

    private void setupAnimationStates() {
        if (idleAnimationTimeout <= 0) {
            idleAnimationTimeout = random.nextInt(40) + 80;
            idleAnimationState.start(tickCount);
        } else {
            --idleAnimationTimeout;
        }

        if (isSwimming()) {
            if (swimAnimationTimeout <= 0) {
                swimAnimationTimeout = random.nextInt(40) + 80;
                swimAnimationState.start(tickCount);
            } else {
                --swimAnimationTimeout;
            }
        } else {
            swimAnimationState.stop();
            swimAnimationTimeout = 0;
        }

        if (isRunning()) {
            if (runAnimationTimeout <= 0) {
                runAnimationTimeout = random.nextInt(40) + 80;
                runAnimationState.start(tickCount);
            } else {
                --runAnimationTimeout;
            }
        } else {
            runAnimationState.stop();
            runAnimationTimeout = 0;
        }
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }


    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.96f;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return null;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.POTATO);
    }

    @Override
    protected void dropAllDeathLoot(@NotNull DamageSource pDamageSource) {
        super.dropAllDeathLoot(pDamageSource);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.POCHY_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
        return ModSounds.POCHY_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.POCHY_DEATH.get();
    }

    public @NotNull SimpleContainer getInventory() {
        return inventory;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        writeInventoryToTag(pCompound);
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", getTypeVariant());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        readInventoryFromTag(pCompound);
        super.readAdditionalSaveData(pCompound);
        entityData.set(DATA_ID_TYPE_VARIANT, pCompound.getInt("Variant"));
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand pHand) {
        ItemStack itemStack = player.getItemInHand(pHand);

        if (level().isClientSide) {
            return itemStack.is(Items.POTATO) ? InteractionResult.CONSUME : InteractionResult.PASS;
        }

        // Inventory access
        if (pHand.equals(InteractionHand.OFF_HAND)) {

            player.openMenu(new SimpleMenuProvider(
                    (containerId, playerInventory, unusedPlayer) -> new ChestMenu(MenuType.GENERIC_9x3, containerId, playerInventory, inventory, 3),
                    Component.literal(getCustomName().getString() + "'s inventory")
            ));
            return InteractionResult.SUCCESS;
        } else {
            // Feeding
            if (isFood(itemStack) && getHealth() < getMaxHealth()) {
                heal(2f);
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            }

            return super.mobInteract(player, pHand);
        }
    }

    /**
     * Variants
     */

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        PochyVariant variant = Util.getRandom(PochyVariant.values(), random);
        setVariant(variant);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public PochyVariant getVariant() {
        return PochyVariant.byId(getTypeVariant() & 255);
    }

    private void setVariant(PochyVariant variant) {
        entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    private int getTypeVariant() {
        return entityData.get(DATA_ID_TYPE_VARIANT);
    }
}
