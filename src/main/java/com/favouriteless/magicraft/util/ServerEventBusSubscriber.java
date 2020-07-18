package com.favouriteless.magicraft.util;

import com.favouriteless.magicraft.init.MagicraftRituals;
import com.favouriteless.magicraft.rituals.Ritual;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber
public class ServerEventBusSubscriber {

    @SubscribeEvent
    public static void OnServerTick(TickEvent.ServerTickEvent event) {

        // Server side start tick
        if(event.phase == TickEvent.Phase.START) {
            // For rituals
            List<Ritual> ritualsToRemove = new ArrayList<>();
            if (MagicraftRituals.ACTIVE_RITUALS != null) {
                for (Ritual ritual : MagicraftRituals.ACTIVE_RITUALS) {

                    if (ritual.inactive) {
                        ritualsToRemove.add(ritual);
                    } else {
                        ritual.tick();
                    }

                }
                for (Ritual ritual : ritualsToRemove) {
                    MagicraftRituals.ACTIVE_RITUALS.remove(ritual);
                }
            }
        }
    }



    @SubscribeEvent
    public static void OnWorldLoaded(WorldEvent.Load event) {

        if (!event.getWorld().isRemote() && event.getWorld() instanceof ServerWorld)
        {
            MagicraftWorldSavedData saver = MagicraftWorldSavedData.forWorld((ServerWorld) event.getWorld());

            if(saver.data.contains("MagicraftActiveRituals"))
            {
                MagicraftRituals.LoadRitualsTag( (CompoundNBT) Objects.requireNonNull(saver.data.get("MagicraftActiveRituals")), (ServerWorld) event.getWorld().getWorld());
            }
        }

    }



    @SubscribeEvent
    public static void OnWorldSave(WorldEvent.Save event) {

        if (!event.getWorld().isRemote() && event.getWorld() instanceof ServerWorld)
        {
            MagicraftWorldSavedData saver = MagicraftWorldSavedData.forWorld((ServerWorld) event.getWorld());

            CompoundNBT nbt = new CompoundNBT();

            nbt.put("MagicraftActiveRituals", MagicraftRituals.GetRitualsTag());

            saver.data = nbt;
            saver.markDirty();
        }
    }

    @SubscribeEvent
    public static void OnServerStop(FMLServerStoppingEvent event) {
        MagicraftRituals.ACTIVE_RITUALS = new ArrayList<>();
    }

}