package io.sandbox.rune_dominion.traits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import io.sandbox.rune_dominion.items.BaseRune;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import oshi.util.tuples.Quartet;

public abstract class RuneTrait {
  public TraitGroups traitGroup = TraitGroups.NONE; 
  public int maxLevel = 1;
  public Traits traitType;

  public RuneTrait() {
    this.traitGroup = getTraitGroup();
    this.traitType = getTraitType();
  }

  public static Map<Traits, Integer> decodeTraitListNbt(String nbtString) {
    Map<Traits, Integer> result = new HashMap<>();

    String[] traitPairs = nbtString.split("&");
    if (traitPairs.length > 0) {
      for (String traitPair : traitPairs) {
        String[] traitUnparsed = traitPair.split(":");
        if (traitUnparsed.length == 0) {
          continue;
        }

        try {
          Traits traitName = Traits.valueOf(traitUnparsed[0]);
          
          int level = 1; // default to level 1
          if (traitUnparsed.length == 2) {
            level = Integer.parseInt(traitUnparsed[1]);
          }

          result.put(traitName, level);
        } catch (IllegalArgumentException error) {}
      }
    }

    return result;
  }

  public static String encodeTraitListNbt(Map<Traits, Integer> traitList) {
    List<String> result = new ArrayList<>();

    traitList.forEach((trait, level) -> {
      result.add(trait + ":" + level);
    });

    return String.join("&", result);
  }

  public static List<Quartet<RuneTrait, LivingEntity, SlotReference, Integer>> getEquippedTraits(LivingEntity player) {
    List<Quartet<RuneTrait, LivingEntity, SlotReference, Integer>> playerTraits = new ArrayList<>();

    if (TrinketsApi.getTrinketComponent(player).isPresent()) {
      List<Pair<SlotReference, ItemStack>> equippedTrinkets = TrinketsApi.getTrinketComponent(player).get().getAllEquipped();
      
      for (Pair<SlotReference, ItemStack> itemPair : equippedTrinkets) {
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
            if (runeTrait != null) {
              // System.out.println("does this ever hit???");
              playerTraits.add(
                new Quartet<RuneTrait, LivingEntity, SlotReference, Integer>(
                  runeTrait,
                  player,
                  slotRef,
                  trait.getValue()
                )
              );
            }
          }
        }
      }
    }

    return playerTraits;
  }

  public abstract TraitGroups getTraitGroup();
  public abstract Traits getTraitType();
}
