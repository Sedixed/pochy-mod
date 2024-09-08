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
import net.minecraft.world.entity.ai.goal.*;
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
import net.sedixed.pochy.entity.ai.PochyFloatGoal;
import net.sedixed.pochy.entity.ai.PochyFollowOwnerGoal;
import net.sedixed.pochy.entity.variant.PochyVariant;
import net.sedixed.pochy.sound.ModSounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PochyEntity extends TamableAnimal implements InventoryCarrier {
    private final SimpleContainer inventory = new SimpleContainer(27);

    private static final EntityDataAccessor<Boolean> SWIMMING =
            SynchedEntityData.defineId(PochyEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> RUNNING =
            SynchedEntityData.defineId(PochyEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT =
            SynchedEntityData.defineId(PochyEntity.class, EntityDataSerializers.INT);

    public PochyEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState swimAnimationState = new AnimationState();
    private int swimAnimationTimeout = 0;

    public final AnimationState runAnimationState = new AnimationState();
    private int runAnimationTimeout = 0;

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if (this.isSwimming()) {
            if (this.swimAnimationTimeout <= 0) {
                swimAnimationTimeout = this.random.nextInt(40) + 80;
                swimAnimationState.start(this.tickCount);
            } else {
                --swimAnimationTimeout;
            }
        } else {
            swimAnimationState.stop();
        }

        if (this.isRunning()) {
            if (this.runAnimationTimeout <= 0) {
                runAnimationTimeout = this.random.nextInt(40) + 80;
                runAnimationState.start(this.tickCount);
            } else {
                --runAnimationTimeout;
            }
        } else {
            runAnimationState.stop();
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        this.walkAnimation.update(
                this.getPose() == Pose.STANDING ? Math.min(pPartialTick * 6f, 1f) : 0f,
                0.2f
        );
    }

    public void setSwimmingState(boolean swimming) {
        this.entityData.set(SWIMMING, swimming);
    }

    public boolean isSwimming() {
        return this.entityData.get(SWIMMING);
    }

    public void setRunning(boolean running) {
        this.entityData.set(RUNNING, running);
    }

    public boolean isRunning() {
        return this.entityData.get(RUNNING);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SWIMMING, false);
        this.entityData.define(RUNNING, false);
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PochyFloatGoal(this));
        this.goalSelector.addGoal(0, new PochyFollowOwnerGoal(this, 1.65f, 5, 100, true));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 10.0f));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0f));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes()  {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.FOLLOW_RANGE, 100);
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
        return this.inventory;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        writeInventoryToTag(pCompound);
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        readInventoryFromTag(pCompound);
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(DATA_ID_TYPE_VARIANT, pCompound.getInt("Variant"));
    }

    @Override
    public InteractionResult mobInteract(Player player, @NotNull InteractionHand pHand) {
        ItemStack itemStack = player.getItemInHand(pHand);

        if (this.level().isClientSide) {
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
            if (isFood(itemStack) && this.getHealth() < this.getMaxHealth()) {
                this.heal(2f);
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                this.gameEvent(GameEvent.EAT, this);
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
        PochyVariant variant = Util.getRandom(PochyVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public PochyVariant getVariant() {
        return PochyVariant.byId(this.getTypeVariant() & 255);
    }

    private void setVariant(PochyVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }
}
