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

public class Barbarian extends RuneTrait implements DamageTrait {
  public int maxLevel = 3;
  public static float getDamageAmount(int level) {
    return 1.5F * level;
  }

  public TraitGroups getTraitGroup() {
    return TraitGroups.DAMAGE;
  };

  public Traits getTraitType() {
    return Traits.BARBARIAN;
  }

  // Applied damage just before armor is calculated
  public float applyDamage(LivingEntity entity, DamageSource source, float amount, int level) {
    LivingEntity player = (LivingEntity)source.getAttacker();
    
    Item usedItem = player.getMainHandStack().getItem();
    String offHandItem = player.getOffHandStack().getItem().toString();
    if (
      // This does not cut through shields...
      entity.blockedByShield(source) ||
      !(
        usedItem instanceof SwordItem ||
        usedItem instanceof AxeItem
      ) ||
      offHandItem != "air"
    ) {
      return amount;
    }

    return amount + Strength.getDamageAmount(level);
  }
}
