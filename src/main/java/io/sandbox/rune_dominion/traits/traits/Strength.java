package io.sandbox.rune_dominion.traits.traits;

import io.sandbox.rune_dominion.traits.DamageTrait;
import io.sandbox.rune_dominion.traits.RuneTrait;
import io.sandbox.rune_dominion.traits.TraitGroups;
import io.sandbox.rune_dominion.traits.Traits;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;

public class Strength extends RuneTrait implements DamageTrait {
  public int maxLevel = 3;
  public static float getDamageAmount(int level) {
    return 0.5F * level;
  }

  public TraitGroups getTraitGroup() {
    return TraitGroups.DAMAGE;
  };

  public Traits getTraitType() {
    return Traits.STRENGTH;
  }

  // Applied damage just before armor is calculated
  public float applyDamage(LivingEntity entity, DamageSource source, float amount, int level) {
    LivingEntity player = (LivingEntity)source.getAttacker();
    
    Item usedItem = player.getMainHandStack().getItem();
    if (
      entity.blockedByShield(source) ||
      !(
        usedItem instanceof SwordItem ||
        usedItem instanceof AxeItem
      )
    ) {
      // This does not cut through shields...
      // Can also check if amount is 0
      // But with block check we can make block piercing and stuff...
      return amount;
    }

    return amount + Strength.getDamageAmount(level);
  }
}
