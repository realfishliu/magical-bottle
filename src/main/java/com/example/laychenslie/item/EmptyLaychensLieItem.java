package com.example.laychenslie.item;

import com.example.laychenslie.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EmptyLaychensLieItem extends Item {
    public EmptyLaychensLieItem(Properties properties) {
        super(properties);
    }

    // This method applies the permanent enchanted glint effect to the item
    @Override
    public boolean isFoil(ItemStack stack) {
        return true; 
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (!player.level().isClientSide) {
            CompoundTag entityData = new CompoundTag();
            
            if (interactionTarget.saveAsPassenger(entityData)) {
                // Remove UUID so it can be spawned as a fresh entity later without conflicts
                entityData.remove("UUID");

                ItemStack filledBottle = new ItemStack(ModItems.FILLED_LAYCHENS_LIE.get());
                CompoundTag tag = filledBottle.getOrCreateTag();
                tag.put("CapturedEntity", entityData);

                // Set the bottle's name to the entity's exact tagged or default name
                filledBottle.setHoverName(interactionTarget.getName());

                // Remove the creature from the world
                interactionTarget.discard();

                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                
                if (stack.isEmpty()) {
                    // Anti-Deletion Workaround: Block the hand slot temporarily with a dummy item 
                    // so the filled bottle safely diverts to another inventory slot.
                    player.setItemInHand(usedHand, new ItemStack(Items.STONE));
                    
                    if (!player.getInventory().add(filledBottle)) {
                        player.drop(filledBottle, false);
                    }
                    
                    // Restore the original empty stack so Vanilla can naturally clear the hand.
                    player.setItemInHand(usedHand, stack);
                } else {
                    if (!player.getInventory().add(filledBottle)) {
                        player.drop(filledBottle, false);
                    }
                }
                
                return InteractionResult.sidedSuccess(player.level().isClientSide);
            }
        }
        return InteractionResult.sidedSuccess(player.level().isClientSide);
    }
}