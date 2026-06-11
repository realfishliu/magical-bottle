package com.example.laychenslie.registry;

import com.example.laychenslie.LaychensLieMod;
import com.example.laychenslie.item.EmptyLaychensLieItem;
import com.example.laychenslie.item.FilledLaychensLieItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, LaychensLieMod.MOD_ID);

    public static final RegistryObject<Item> EMPTY_LAYCHENS_LIE = ITEMS.register("empty_laychens_lie",
            () -> new EmptyLaychensLieItem(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> FILLED_LAYCHENS_LIE = ITEMS.register("filled_laychens_lie",
            () -> new FilledLaychensLieItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}