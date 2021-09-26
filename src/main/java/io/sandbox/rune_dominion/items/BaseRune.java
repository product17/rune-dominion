package io.sandbox.rune_dominion.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import io.sandbox.rune_dominion.cache.TrinketCache;
import io.sandbox.rune_dominion.traits.RuneTrait;
import io.sandbox.rune_dominion.traits.StatTrait;
import io.sandbox.rune_dominion.traits.TraitMap;
import io.sandbox.rune_dominion.traits.Traits;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Pair;
import net.minecraft.world.World;

public class BaseRune extends TrinketItem {
  public static final String slotCount = "slot_count";
  public String traitPath;

  public BaseRune(Item.Settings settings) {
    super(settings);
  }

  @Override
  public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
    String encodedTraitList = itemStack.getOrCreateNbt().getString(this.traitPath);
    Map<Traits, Integer> traitList = RuneTrait.decodeTraitListNbt(encodedTraitList);
    traitList.forEach((trait, level) -> {
      tooltip.add(new TranslatableText(trait + " : " + level));
    });
  }

  public static void putTraits(NbtCompound nbt, String traitPath, Map<Traits, Integer> traitList) {
    nbt.putString(traitPath, RuneTrait.encodeTraitListNbt(traitList));
  }

  public static void putSlots(NbtCompound nbt, int maxSlots) {
    nbt.putInt(BaseRune.slotCount, (new Random()).nextInt(maxSlots) + 1);
  }

  public static Map<Traits, Integer> generateTraitList(ItemStack stack) {
    Map<Traits, Integer> result = new HashMap<>();
    int traitCountToAdd = stack.getRarity().ordinal() + 1;
    int allTraitsCount = Traits.values().length;
    for (int i = 0; i < traitCountToAdd; i++) {
      int randomTrait = (new Random()).nextInt(allTraitsCount);
      Traits trait = Traits.values()[randomTrait];
      RuneTrait runeTrait = TraitMap.getTrait(trait);
      result.put(trait, (new Random()).nextInt(runeTrait.maxLevel + 1) + 1);
    }

    return result;
  }

  public Map<RuneTrait, Integer> getTraits(ItemStack stack) {
    String encodedTraitList = stack.getOrCreateNbt().getString(this.traitPath);
    Map<RuneTrait, Integer> result = new HashMap<>();
    Map<Traits, Integer> traitList = RuneTrait.decodeTraitListNbt(encodedTraitList);

    // Check and remove the traits
    for (Traits traitId : traitList.keySet()) {
      RuneTrait statTrait = TraitMap.getTrait(traitId);
      if (statTrait == null) {
        continue;
      }

      result.put(statTrait, traitList.get(traitId));
    }

    return result;
  }

  public void onEquip(ItemStack stack, SlotReference slot, LivingEntity player) {
    if (player.world.isClient()) {
      return;
    }

    TrinketCache.resetTrinkets(player);

    String runeTraitsEncoded = stack.getOrCreateNbt().getString(((BaseRune)(stack.getItem())).traitPath);
    Map<Traits, Integer> traitList = RuneTrait.decodeTraitListNbt(runeTraitsEncoded);

    for (Traits trait : traitList.keySet()) {
      RuneTrait runeTrait = TraitMap.getTrait(trait);
      if (runeTrait instanceof StatTrait) {
        ((StatTrait)runeTrait).addTrait(player, slot, traitList.get(trait));
      }
    }
	}

  public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity player) {
    if (player.world.isClient()) {
      return;
    }

    List<Pair<SlotReference, ItemStack>> oldTrinkets = TrinketCache.getDiff(player);
    for (Pair<SlotReference, ItemStack> slotPair : oldTrinkets) {
      ItemStack itemStack = slotPair.getRight();
      String runeTraitsEncoded = itemStack.getOrCreateNbt().getString(((BaseRune)(itemStack.getItem())).traitPath);
      Map<Traits, Integer> traitList = RuneTrait.decodeTraitListNbt(runeTraitsEncoded);

      for (Traits trait : traitList.keySet()) {
        RuneTrait runeTrait = TraitMap.getTrait(trait);
        if (runeTrait instanceof StatTrait) {
          ((StatTrait)runeTrait).removeTrait(player, slotPair.getLeft(), traitList.get(trait));
        }
      }
    }

    String runeTraitsEncoded = stack.getOrCreateNbt().getString(((BaseRune)(stack.getItem())).traitPath);
    Map<Traits, Integer> traitList = RuneTrait.decodeTraitListNbt(runeTraitsEncoded);

    for (Traits trait : traitList.keySet()) {
      RuneTrait runeTrait = TraitMap.getTrait(trait);
      if (runeTrait instanceof StatTrait) {
        ((StatTrait)runeTrait).removeTrait(player, slot, traitList.get(trait));
      }
    }
	}
}
