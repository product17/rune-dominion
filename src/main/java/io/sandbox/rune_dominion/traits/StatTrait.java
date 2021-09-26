package io.sandbox.rune_dominion.traits;

import dev.emi.trinkets.api.SlotReference;
import net.minecraft.entity.LivingEntity;

public interface StatTrait {
  public void addTrait(LivingEntity entity, SlotReference slot, int level);

  public void removeTrait(LivingEntity entity, SlotReference slot, int level);

  default public Boolean shouldAddTrait(LivingEntity entity, SlotReference slot, int currentLevel, int updatedLevel) {
    if (currentLevel == updatedLevel) {
      return false;
    }

    if (currentLevel > 0 && currentLevel != updatedLevel) {
      // make sure to remove old stat
      this.removeTrait(entity, slot, currentLevel);
    }

    return true;
  }
}
