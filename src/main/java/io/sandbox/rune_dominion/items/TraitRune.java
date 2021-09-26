package io.sandbox.rune_dominion.items;

import net.minecraft.item.Item;

public class TraitRune extends BaseRune {
  public static final String name = "trait_rune";

  public TraitRune(Item.Settings settings) {
    super(settings);
    this.traitPath = "basic_traits";
  }
}
