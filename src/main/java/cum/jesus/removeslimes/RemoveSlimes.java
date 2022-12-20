package cum.jesus.removeslimes;

import com.google.common.io.Files;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
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
    public static boolean removeHitBoxes = false;
    public static boolean onSkyblock;
    public static boolean onIsland;
    public static File configIg = new File(mc.mcDataDir, "slimefucker");
    public static File toggleFile = new File(configIg, "toggled.txt");
    public static File hitBoxFile = new File(configIg, "hitbox.txt");

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new ToggleCommand());
        MinecraftForge.EVENT_BUS.register(new Utils());
        MinecraftForge.EVENT_BUS.register(this);

        if (!configIg.exists()) {
            try {
                configIg.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!toggleFile.exists()) {
            try {
                toggleFile.createNewFile();
                Files.write("false".getBytes(StandardCharsets.UTF_8), toggleFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!hitBoxFile.exists()) {
            try {
                hitBoxFile.createNewFile();
                Files.write("false".getBytes(StandardCharsets.UTF_8), hitBoxFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SubscribeEvent
    public void checkConfig(TickEvent.ClientTickEvent event) {
        try {
            toggled = Files.readFirstLine(toggleFile, StandardCharsets.UTF_8).equals("true");
            removeHitBoxes = Files.readFirstLine(hitBoxFile, StandardCharsets.UTF_8).equals("true");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isNameStand(EntityLivingBase entity) {
        return onSkyblock && toggled && entity instanceof EntityArmorStand && entity.isInvisible();
    }

    @SubscribeEvent
    public void render(RenderLivingEvent.Pre<EntityLivingBase> event) {
        EntityLivingBase entity = event.entity;

        if (entity instanceof EntitySlime && toggled && (onSkyblock && onIsland)) {
            event.setCanceled(true);
            if (removeHitBoxes) {
                entity.setEntityBoundingBox(new AxisAlignedBB(0, 0, 0, 0, 0, 0));
            }
        }
        if (isNameStand(entity)) {
            event.setCanceled(true);
        }
    }
}
