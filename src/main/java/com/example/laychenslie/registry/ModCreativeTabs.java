package com.example.laychenslie.registry;

import com.example.laychenslie.LaychensLieMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LaychensLieMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> LAYCHENS_TAB = CREATIVE_MODE_TABS.register("laychens_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.EMPTY_LAYCHENS_LIE.get()))
                    .title(Component.translatable("creativetab.laychens_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.EMPTY_LAYCHENS_LIE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}