package io.sandbox.rune_dominion.traits.traits;

import java.util.ArrayList;
import java.util.UUID;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import dev.emi.trinkets.api.SlotReference;
import io.sandbox.rune_dominion.cache.SlotCache;
import io.sandbox.rune_dominion.traits.RuneTrait;
import io.sandbox.rune_dominion.traits.StatTrait;
import io.sandbox.rune_dominion.traits.TraitGroups;
import io.sandbox.rune_dominion.traits.Traits;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;

public class Vitality extends RuneTrait implements StatTrait {
  public int maxLevel = 3;
  private static float getHpAmount(int level) {
    return level * 2.0f;
  }

  public TraitGroups getTraitGroup() {
    return TraitGroups.STAT;
  };

  public Traits getTraitType() {
    return Traits.VITALITY;
  }

  public Multimap<EntityAttribute, EntityAttributeModifier> buildAttributes(SlotReference slot, int level) {
    Multimap<EntityAttribute, EntityAttributeModifier> list = Multimaps.newMultimap(Maps.newLinkedHashMap(), ArrayList::new);
    UUID uuid = SlotCache.getUuid(slot, this.getTraitType());
    list.put(
      EntityAttributes.GENERIC_MAX_HEALTH,
      new EntityAttributeModifier(
        uuid,
        "rune-dominion:vitality",
        getHpAmount(level),
        EntityAttributeModifier.Operation.ADDITION
      )
    );

    return list;
  }

  public void addTrait(LivingEntity entity, SlotReference slot, int level) {
    entity.getAttributes().addTemporaryModifiers(buildAttributes(slot, level));
  }

  public void removeTrait(LivingEntity entity, SlotReference slot, int level) {
    entity.getAttributes().removeModifiers(buildAttributes(slot, level));
  }
}
