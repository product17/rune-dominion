package io.sandbox.rune_dominion.items;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class UnknownCommonRune extends Item {
  public static final String name = "unknown_common_rune";
  public UnknownCommonRune(Settings settings) {
    super(settings);
  }
  
  @Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand user) {
		ItemStack tmpStack = player.getStackInHand(user);
    ItemStack stack = new ItemStack(ItemLoader.TRAIT_RUNE);
    NbtCompound nbt = stack.getOrCreateNbt();
    player.getAttributeInstance(
      EntityAttributes.GENERIC_MAX_HEALTH
    ).setBaseValue(20);
    BaseRune.putTraits(
      nbt,
      ItemLoader.TRAIT_RUNE.traitPath,
      BaseRune.generateTraitList(stack)
    );

    // Add/Drop the new item
    player.getInventory().offerOrDrop(stack);

    // Remove and item from the held stack
    tmpStack.setCount(tmpStack.getCount() - 1);
    return TypedActionResult.success(tmpStack, world.isClient());
	}
}
