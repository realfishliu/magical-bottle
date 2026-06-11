package com.example.laychenslie.item;

import com.example.laychenslie.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class FilledLaychensLieItem extends Item {
    public FilledLaychensLieItem(Properties properties) {
        super(properties);
    }

    // This method applies the permanent enchanted glint effect to the item
    @Override
    public boolean isFoil(ItemStack stack) {
        return true; 
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        
        if (stack.hasTag() && stack.getTag().contains("CapturedEntity")) {
            CompoundTag entityData = stack.getTag().getCompound("CapturedEntity");
            
            // Decode and display specific NBTs for readability
            if (entityData.contains("Health")) {
                float health = entityData.getFloat("Health");
                tooltipComponents.add(Component.literal("§cHealth: §f" + String.format("%.1f", health)));
            }
            if (entityData.contains("id")) {
                tooltipComponents.add(Component.literal("§7Type: §f" + entityData.getString("id")));
            }

            // Display a snippet of the raw NBT payload as a generic description
            String rawNbt = entityData.toString();
            if (rawNbt.length() > 60) {
                rawNbt = rawNbt.substring(0, 60) + "...";
            }
            tooltipComponents.add(Component.literal("§8Data: " + rawNbt));
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide) {
            ItemStack stack = context.getItemInHand();
            if (stack.hasTag() && stack.getTag().contains("CapturedEntity")) {
                CompoundTag entityData = stack.getTag().getCompound("CapturedEntity");
                Optional<EntityType<?>> type = EntityType.by(entityData);

                if (type.isPresent()) {
                    BlockPos spawnPos = context.getClickedPos().relative(context.getClickedFace());
                    Entity entity = type.get().create(level);
                    
                    if (entity != null) {
                        // Load the exact previous data
                        entity.load(entityData);
                        // Reset the position to exactly where the player clicked
                        entity.setPos(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);
                        level.addFreshEntity(entity);

                        Player player = context.getPlayer();
                        if (player != null && !player.isCreative()) {
                            stack.shrink(1);
                            ItemStack emptyBottle = new ItemStack(ModItems.EMPTY_LAYCHENS_LIE.get());
                            
                            if (stack.isEmpty()) {
                                // Anti-Deletion Workaround: Block the hand slot temporarily with a dummy item.
                                player.setItemInHand(context.getHand(), new ItemStack(Items.STONE));
                                
                                if (!player.getInventory().add(emptyBottle)) {
                                    player.drop(emptyBottle, false);
                                }
                                
                                // Restore the original empty stack so Vanilla can naturally clear the hand.
                                player.setItemInHand(context.getHand(), stack);
                            } else {
                                if (!player.getInventory().add(emptyBottle)) {
                                    player.drop(emptyBottle, false);
                                }
                            }
                        }
                        return InteractionResult.sidedSuccess(level.isClientSide);
                    }
                }
            }
        }
        return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
    }
}