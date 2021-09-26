package io.sandbox.rune_dominion.traits;

import dev.emi.trinkets.api.SlotReference;
import net.minecraft.entity.player.PlayerEntity;

public interface InventoryCheckTrait {
  public void inventoryCheck(PlayerEntity player, SlotReference slot, int level);
}
