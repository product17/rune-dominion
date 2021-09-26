package io.sandbox.rune_dominion.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import io.sandbox.rune_dominion.items.BaseRune;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

public class TrinketCache {
  private static Map<UUID, List<Pair<SlotReference, ItemStack>>> equipedTrickets = new HashMap<>();

  public static List<Pair<SlotReference, ItemStack>> getEquipped(UUID playerId) {
    return equipedTrickets.getOrDefault(playerId, new ArrayList<>());
  }

  public static void setEquipped(UUID playerId, List<Pair<SlotReference, ItemStack>> list) {
    equipedTrickets.put(playerId, list);
  }

  public static List<Pair<SlotReference, ItemStack>> getDiff(LivingEntity player) {
    UUID playerId = player.getUuid();
    List<Pair<SlotReference, ItemStack>> result = new ArrayList<>();
    List<Pair<SlotReference, ItemStack>> cachedTrinkets = TrinketCache.getEquipped(playerId);
    List<Pair<SlotReference, ItemStack>> currentTrinkets = TrinketsApi.getTrinketComponent(player).get().getAllEquipped();

    for (Pair<SlotReference, ItemStack> slotPair : cachedTrinkets) {
      Boolean match = false;
      for (Pair<SlotReference, ItemStack> currentSlotPair : currentTrinkets) {
        if (currentSlotPair.getLeft().equals(slotPair.getLeft())) {
          if (currentSlotPair.getRight().equals(slotPair.getRight())) {
            match = true;
          }
        }
      }

      if (!match) {
        Item item = slotPair.getRight().getItem();
        if (item instanceof BaseRune) {
          result.add(slotPair);
        }
      }
    }

    return result;
  }

  public static void resetTrinkets(LivingEntity player) {
    // UUID playerId = player.getUuid();
    // List<Pair<SlotReference, ItemStack>> cachedTrinkets = TrinketCache.getEquipped(playerId);
    // List<Pair<SlotReference, ItemStack>> currentTrinkets = TrinketsApi.getTrinketComponent(player).get().getAllEquipped();

    // for (Pair<SlotReference, ItemStack> slotPair : cachedTrinkets) {
    //   Boolean match = false;
    //   for (Pair<SlotReference, ItemStack> currentSlotPair : currentTrinkets) {
    //     if (currentSlotPair.getLeft().equals(slotPair.getLeft())) {
    //       if (currentSlotPair.getRight().equals(slotPair.getRight())) {
    //         match = true;
    //       }
    //     }
    //   }

    //   if (!match) {
    //     Item item = slotPair.getRight().getItem();
    //     if (item instanceof BaseRune) {
    //       ((BaseRune)item).onUnequip(slotPair.getRight(), slotPair.getLeft(), player);
    //     }
    //   }
    // }

    List<Pair<SlotReference, ItemStack>> diff = getDiff(player);
    for (Pair<SlotReference, ItemStack> slotPair : diff) {
      Item item = slotPair.getRight().getItem();
        if (item instanceof BaseRune) {
          ((BaseRune)item).onUnequip(slotPair.getRight(), slotPair.getLeft(), player);
        }
    }

    TrinketCache.setEquipped(player.getUuid(), TrinketsApi.getTrinketComponent(player).get().getAllEquipped());
  }
}
