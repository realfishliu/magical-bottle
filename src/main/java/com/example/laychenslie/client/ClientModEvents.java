package com.example.laychenslie.client;

import com.example.laychenslie.LaychensLieMod;
import com.example.laychenslie.registry.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LaychensLieMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        // tintIndex 0 is the 'layer0' liquid overlay we just added to the JSON.
        // We return 0x385DC6 to perfectly match Vanilla Minecraft's water color.
        event.register((stack, tintIndex) -> {
            return tintIndex == 0 ? 0x385DC6 : -1; 
        }, ModItems.FILLED_LAYCHENS_LIE.get());
    }
}