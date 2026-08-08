package com.gabrielequa.zzzoom;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Zzzoom.MODID, value = Dist.CLIENT)
public class ZoomHandler {
    public static final KeyMapping.Category ZOOM_CATEGORY =
            new KeyMapping.Category(Identifier.fromNamespaceAndPath(Zzzoom.MODID, "zoom"));

    public static final KeyMapping ZOOM_KEY =
            new KeyMapping("key.zzzoom.zoom", GLFW.GLFW_KEY_C, ZOOM_CATEGORY);

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.registerCategory(ZOOM_CATEGORY);
        event.register(ZOOM_KEY);
    }
}
