package com.gabrielequa.zzzoom;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue BASE_ZOOM_DIVISOR = BUILDER
            .comment("FOV divisor applied the moment the zoom key is first pressed, before any scroll adjustment.")
            .defineInRange("baseZoomDivisor", 4.0, 1.0, 10.0);

    public static final ModConfigSpec.DoubleValue MIN_ZOOM_DIVISOR = BUILDER
            .comment("Lowest FOV divisor reachable by scrolling out while zoomed.")
            .defineInRange("minZoomDivisor", 2.0, 1.0, 10.0);

    public static final ModConfigSpec.DoubleValue MAX_ZOOM_DIVISOR = BUILDER
            .comment("Highest FOV divisor reachable by scrolling in while zoomed. Vanilla clamps the underlying FOV modifier to a minimum of 0.1x, so divisors above 10 have no additional effect.")
            .defineInRange("maxZoomDivisor", 8.0, 1.0, 10.0);

    public static final ModConfigSpec.DoubleValue SCROLL_STEP = BUILDER
            .comment("How much the FOV divisor changes per scroll notch while zoomed.")
            .defineInRange("scrollStep", 1.0, 0.1, 10.0);

    public static final ModConfigSpec.DoubleValue SMOOTHING_FACTOR = BUILDER
            .comment("Fraction of the remaining zoom distance closed per tick. Higher is snappier, lower is smoother.")
            .defineInRange("smoothingFactor", 0.6, 0.01, 1.0);

    public static final ModConfigSpec.BooleanValue SENSITIVITY_REDUCTION_ENABLED = BUILDER
            .comment("Whether mouse sensitivity is reduced while zoomed.")
            .define("sensitivityReductionEnabled", true);

    public static final ModConfigSpec.DoubleValue SENSITIVITY_SCALE = BUILDER
            .comment("Multiplier applied on top of the 1/divisor sensitivity curve while zoomed.")
            .defineInRange("sensitivityScale", 1.0, 0.1, 5.0);

    public static final ModConfigSpec.BooleanValue HIDE_HAND_WHILE_ZOOMED = BUILDER
            .comment("Whether the held item and bare arm are hidden while the zoom key is held.")
            .define("hideHandWhileZoomed", true);

    public static final ModConfigSpec CLIENT_SPEC = BUILDER.build();
}
