package com.gabrielequa.zzzoom;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.CalculatePlayerTurnEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Zzzoom.MODID, value = Dist.CLIENT)
public class ZoomHandler {
    public static final KeyMapping.Category ZOOM_CATEGORY =
            new KeyMapping.Category(Identifier.fromNamespaceAndPath(Zzzoom.MODID, "zoom"));

    public static final KeyMapping ZOOM_KEY =
            new KeyMapping("key.zzzoom.zoom", GLFW.GLFW_KEY_C, ZOOM_CATEGORY);

    private static boolean wasKeyDown = false;
    private static double zoomDivisorTarget = 1.0;
    private static double zoomDivisorCurrent = 1.0;

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.registerCategory(ZOOM_CATEGORY);
        event.register(ZOOM_KEY);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        boolean isKeyDown = ZOOM_KEY.isDown();
        if (isKeyDown && !wasKeyDown) {
            zoomDivisorTarget = Mth.clamp(
                    Config.BASE_ZOOM_DIVISOR.getAsDouble(),
                    Config.MIN_ZOOM_DIVISOR.getAsDouble(),
                    Config.MAX_ZOOM_DIVISOR.getAsDouble());
        } else if (!isKeyDown) {
            zoomDivisorTarget = 1.0;
        }
        wasKeyDown = isKeyDown;

        zoomDivisorCurrent = Mth.lerp(Config.SMOOTHING_FACTOR.getAsDouble(), zoomDivisorCurrent, zoomDivisorTarget);
    }

    @SubscribeEvent
    public static void onComputeFovModifier(ComputeFovModifierEvent event) {
        event.setNewFovModifier((float) (event.getNewFovModifier() / zoomDivisorCurrent));
    }

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        if (!ZOOM_KEY.isDown()) {
            return;
        }
        double step = Config.SCROLL_STEP.getAsDouble() * Math.signum(event.getScrollDeltaY());
        zoomDivisorTarget = Mth.clamp(
                zoomDivisorTarget + step,
                Config.MIN_ZOOM_DIVISOR.getAsDouble(),
                Config.MAX_ZOOM_DIVISOR.getAsDouble());
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onCalculatePlayerTurn(CalculatePlayerTurnEvent event) {
        if (zoomDivisorCurrent <= 1.0 || !Config.SENSITIVITY_REDUCTION_ENABLED.getAsBoolean()) {
            return;
        }
        double scale = (1.0 / zoomDivisorCurrent) * Config.SENSITIVITY_SCALE.getAsDouble();
        event.setMouseSensitivity(event.getMouseSensitivity() * scale);
    }
}
