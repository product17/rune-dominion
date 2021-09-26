package io.sandbox.rune_dominion.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import io.sandbox.rune_dominion.items.BaseRune;
import io.sandbox.rune_dominion.traits.InventoryCheckTrait;
import io.sandbox.rune_dominion.traits.RuneTrait;
import io.sandbox.rune_dominion.traits.TraitMap;
import io.sandbox.rune_dominion.traits.Traits;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
	@Shadow
	@Final
	private PlayerEntity player;

	@Inject(at = @At("TAIL"), method = "updateItems")
	private void updateItems(CallbackInfo info) {
    if (player.world.isClient()) return;

    if (TrinketsApi.getTrinketComponent(player).isPresent()) {
      TrinketComponent trinkets = TrinketsApi.getTrinketComponent(player).get();
      for (Pair<SlotReference, ItemStack> itemPair : trinkets.getAllEquipped()) {
        ItemStack itemStack = itemPair.getRight();
        Item item = itemStack.getItem();
        if (
          item instanceof BaseRune
        ) {
          String runeTraitsEncoded = itemStack.getOrCreateNbt().getString(((BaseRune)item).traitPath);
          Map<Traits, Integer> traitList = RuneTrait.decodeTraitListNbt(runeTraitsEncoded);
          SlotReference slotRef = itemPair.getLeft();
          for (Map.Entry<Traits, Integer> trait : traitList.entrySet()) {
            RuneTrait runeTrait = TraitMap.getTrait(trait.getKey());
            if (runeTrait != null && runeTrait instanceof InventoryCheckTrait) {
              ((InventoryCheckTrait)runeTrait).inventoryCheck(player, slotRef, trait.getValue());
            }
          }
        }
      }
    }
  }
}
