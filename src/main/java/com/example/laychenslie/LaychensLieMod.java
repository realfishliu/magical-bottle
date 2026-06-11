package com.example.laychenslie;

import com.example.laychenslie.registry.ModCreativeTabs;
import com.example.laychenslie.registry.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LaychensLieMod.MOD_ID)
public class LaychensLieMod {
    public static final String MOD_ID = "laychenslie";

    public LaychensLieMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register items and creative tabs
        ModItems.register(modEventBus);
        ModCreativeTabs.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
}