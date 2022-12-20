package cum.jesus.removeslimes;

import com.google.common.io.Files;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Mod(modid = "jesus", name = "Remove Slimes", clientSideOnly = true)
public class RemoveSlimes {
    public static Minecraft mc = Minecraft.getMinecraft();
    public static boolean toggled = false;
    public static boolean onSkyblock;
    public static boolean onIsland;
    public static File configIg = new File(mc.mcDataDir, "slimefucker.txt");

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new ToggleCommand());
        MinecraftForge.EVENT_BUS.register(new Utils());
        MinecraftForge.EVENT_BUS.register(this);

        if (!configIg.exists()) {
            try {
                configIg.createNewFile();
                Files.write("false".getBytes(StandardCharsets.UTF_8), configIg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public void checkConfig(TickEvent.ClientTickEvent event) {
        try {
            toggled = Files.readFirstLine(configIg, Charset.defaultCharset()).equals("true");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void render(RenderLivingEvent.Pre<EntityLivingBase> event) {
        EntityLivingBase entity = event.entity;

        if (entity instanceof EntitySlime && toggled) {
            event.setCanceled(true);
        }
    }
}
