package io.sandbox.rune_dominion;

import io.sandbox.rune_dominion.items.ItemLoader;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class RuneDominion implements ModInitializer {
  public static final String MODID = "rune_dominion";

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ItemLoader.init();

		System.out.println("Rune Dominion has loaded");
	}

	public static Identifier buildId(String name) {
		return new Identifier(RuneDominion.MODID, name);
	}
}
