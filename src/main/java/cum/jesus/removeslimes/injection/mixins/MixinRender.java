package cum.jesus.removeslimes.injection.mixins;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySlime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cum.jesus.removeslimes.RemoveSlimes.*;

@Mixin({Render.class})
public class MixinRender {
    @Inject(method = "renderShadow", at = @At("HEAD"), cancellable = true)
    private void injectShadowRender(Entity entity, double p_renderShadow_2_, double p_renderShadow_3_, double p_renderShadow_4_, float p_renderShadow_5_, float p_renderShadow_6_, CallbackInfo ci) {
        if (entity instanceof EntitySlime && toggled && (onSkyblock && onIsland)) {
            ci.cancel();
        }
    }
}
