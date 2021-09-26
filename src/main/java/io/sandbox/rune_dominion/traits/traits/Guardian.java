package io.sandbox.rune_dominion.traits.traits;

import java.util.ArrayList;
import java.util.UUID;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import dev.emi.trinkets.api.SlotReference;
import io.sandbox.rune_dominion.cache.SlotCache;
import io.sandbox.rune_dominion.traits.InventoryCheckTrait;
import io.sandbox.rune_dominion.traits.RuneTrait;
import io.sandbox.rune_dominion.traits.StatTrait;
import io.sandbox.rune_dominion.traits.TraitGroups;
import io.sandbox.rune_dominion.traits.Traits;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;

public class Guardian extends RuneTrait implements InventoryCheckTrait, StatTrait {
  public int maxLevel = 3;
  private static float getHpAmount(int level) {
    return level * 2.0f;
  }

  public TraitGroups getTraitGroup() {
    return TraitGroups.STAT;
  };

  public Traits getTraitType() {
    return Traits.GUARDIAN;
  }

  public Boolean hasShieldEquiped(PlayerEntity player) {
    ItemStack offHand = player.getOffHandStack();
    return offHand.getItem() instanceof ShieldItem;
  }

  public Multimap<EntityAttribute, EntityAttributeModifier> buildAttributes(SlotReference slot, int level) {
    Multimap<EntityAttribute, EntityAttributeModifier> list = Multimaps.newMultimap(Maps.newLinkedHashMap(), ArrayList::new);
    UUID uuid = SlotCache.getUuid(slot, this.getTraitType());
    list.put(
      EntityAttributes.GENERIC_MAX_HEALTH,
      new EntityAttributeModifier(
        uuid,
        "rune-dominion:guardian",
        getHpAmount(level),
        EntityAttributeModifier.Operation.ADDITION
      )
    );

    return list;
  }

  public void inventoryCheck(PlayerEntity player, SlotReference slot, int level) {
    if (hasShieldEquiped(player)) {
      this.addTrait(player, slot, level);
    } else {
      this.removeTrait(player, slot, level);
    }
  }

  public void addTrait(LivingEntity entity, SlotReference slot, int level) {
    if (hasShieldEquiped((PlayerEntity)entity)) {
      entity.getAttributes().addTemporaryModifiers(buildAttributes(slot, level));
    }
  }

  public void removeTrait(LivingEntity entity, SlotReference slot, int level) {
    entity.getAttributes().removeModifiers(buildAttributes(slot, level));
  }
}
