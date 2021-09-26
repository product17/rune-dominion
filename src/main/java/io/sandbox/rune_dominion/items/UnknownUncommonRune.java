package io.sandbox.rune_dominion.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class UnknownUncommonRune extends Item {
  public static final String name = "unknown_uncommon_rune";
  public UnknownUncommonRune(Settings settings) {
    super(settings);
  }
  
  @Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand user) {
		ItemStack tmpStack = player.getStackInHand(user);
    ItemStack stack = new ItemStack(ItemLoader.PRIMAL_RUNE);
    NbtCompound nbt = stack.getOrCreateNbt();
    BaseRune.putTraits(
      nbt,
      ItemLoader.PRIMAL_RUNE.traitPath,
      BaseRune.generateTraitList(stack)
    );
    // Not sure what the count should be yet... or if it should be based off Rarity
    BaseRune.putSlots(nbt, 3);

    // Add/Drop the new item
    player.getInventory().offerOrDrop(stack);

    // Remove and item from the held stack
    tmpStack.setCount(tmpStack.getCount() - 1);
    return TypedActionResult.success(tmpStack, world.isClient());
	}
}
