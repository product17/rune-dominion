package io.sandbox.rune_dominion.items;

import io.sandbox.rune_dominion.RuneDominion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ItemLoader {
  public static PrimalRune PRIMAL_RUNE = new PrimalRune(
		new Item.Settings().group(ItemGroup.MISC).rarity(Rarity.RARE).maxCount(16)
	);
	public static UnknownUncommonRune PRIMAL_RUNE_UNDEFINED = new UnknownUncommonRune(
		new Item.Settings().group(ItemGroup.MISC).rarity(Rarity.RARE).maxCount(16)
	);
	public static TraitRune TRAIT_RUNE = new TraitRune(
		new Item.Settings().group(ItemGroup.MISC).rarity(Rarity.UNCOMMON).maxCount(16)
	);
	public static UnknownCommonRune TRAIT_RUNE_UNDEFINED = new UnknownCommonRune(
		new Item.Settings().group(ItemGroup.MISC).rarity(Rarity.UNCOMMON).maxCount(16)
	);

  public static void init() {
		Registry.register(Registry.ITEM, RuneDominion.buildId(PrimalRune.name), PRIMAL_RUNE);
		Registry.register(Registry.ITEM, RuneDominion.buildId(UnknownUncommonRune.name), PRIMAL_RUNE_UNDEFINED);
		Registry.register(Registry.ITEM, RuneDominion.buildId(TraitRune.name), TRAIT_RUNE);
		Registry.register(Registry.ITEM, RuneDominion.buildId(UnknownCommonRune.name), TRAIT_RUNE_UNDEFINED);
  }
}
