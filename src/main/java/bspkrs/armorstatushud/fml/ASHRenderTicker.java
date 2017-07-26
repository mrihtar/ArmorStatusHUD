package bspkrs.armorstatushud.fml;

import bspkrs.armorstatushud.ArmorStatusHUD;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ASHRenderTicker
{
    private Minecraft mcClient;
    private static boolean isRegistered = false;

    public ASHRenderTicker()
    {
        mcClient = FMLClientHandler.instance().getClient();
        isRegistered = true;
    }

    @SubscribeEvent
    public void onTick(RenderTickEvent event)
    {
        if (event.phase.equals(Phase.START))
            return;

        if (!ArmorStatusHUD.onTickInGame(mcClient))
        {
            FMLCommonHandler.instance().bus().unregister(this);
            isRegistered = false;
        }
    }

    public static boolean isRegistered()
    {
        return isRegistered;
    }
}
