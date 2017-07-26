package bspkrs.armorstatushud.fml;

import bspkrs.armorstatushud.ArmorStatusHUD;
import bspkrs.armorstatushud.CommandArmorStatus;
import bspkrs.bspkrscore.fml.bspkrsCoreMod;
import bspkrs.util.ModVersionChecker;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        ArmorStatusHUD.initConfig(event.getSuggestedConfigurationFile());
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(new ASHGameTicker());
        FMLCommonHandler.instance().bus().register(new ASHRenderTicker());

        try
        {
            ClientCommandHandler.instance.registerCommand(new CommandArmorStatus());
        }
        catch (Throwable e)
        {}

        FMLCommonHandler.instance().bus().register(this);

        if (bspkrsCoreMod.instance.allowUpdateCheck)
        {
            ArmorStatusHUDMod.instance.versionChecker = new ModVersionChecker(Reference.MODID, ArmorStatusHUDMod.metadata.version, ArmorStatusHUDMod.instance.versionURL, ArmorStatusHUDMod.instance.mcfTopic);
            ArmorStatusHUDMod.instance.versionChecker.checkVersionWithLogging();
        }
    }

    @SubscribeEvent
    public void onConfigChanged(OnConfigChangedEvent event)
    {
        if (event.getModID().equals(Reference.MODID))
        {
            Reference.config.save();
            ArmorStatusHUD.syncConfig();
        }
    }
}
