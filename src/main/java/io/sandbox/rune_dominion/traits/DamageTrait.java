package io.sandbox.rune_dominion.traits;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public interface DamageTrait {
  public float applyDamage(LivingEntity entity, DamageSource source, float amount, int level);
}
