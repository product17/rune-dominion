package io.sandbox.rune_dominion.items;

import java.util.UUID;

import com.google.common.collect.Multimap;

import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PrimalRune extends BaseRune {
  public static final String name = "primal_rune";

  public PrimalRune(Item.Settings settings) {
    super(settings);
    this.traitPath = "primal_traits";
  }
  
  public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
    var modifiers = super.getModifiers(stack, slot, entity, uuid);
    
    // If the player has access to ruene1 slot, this will give them an extra one
    int slotCount = stack.getOrCreateNbt().getInt(PrimalRune.slotCount);
    SlotAttributes.addSlotModifier(modifiers, slot.inventory().getSlotType().getGroup() + "/trait", uuid, slotCount, EntityAttributeModifier.Operation.ADDITION);
    return modifiers;
  }
}
