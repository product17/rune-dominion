package io.sandbox.rune_dominion.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

@Mixin(ServerPlayNetworkHandler.class)
public class PlayerLeaveMixin {
  @Shadow
  public ServerPlayerEntity player;

  @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;onDisconnect()V"), method = "onDisconnected", cancellable = true)
  private void onPlayerLeave(Text reason, CallbackInfo info) {
    // List<Quartet<
    //   RuneTrait,
    //   LivingEntity,
    //   SlotReference,
    //   Integer
    // >> equippedTraits = RuneTrait.getEquippedTraits((LivingEntity)player);

    // for (Quartet<RuneTrait, LivingEntity, SlotReference, Integer> equippedTrait : equippedTraits) {
    //   RuneTrait runeTrait = equippedTrait.getA();
    //   if (runeTrait instanceof StatTrait) {
    //     ((StatTrait)runeTrait).removeTrait(player, equippedTrait.getC(), equippedTrait.getD());
    //   }
    // }
    
    // Optional<TrinketComponent> trinketComponentOptional = TrinketsApi.getTrinketComponent(this.player);

    // if (trinketComponentOptional.isPresent()) {
    //   TrinketComponent trinketComponent = trinketComponentOptional.get();
    //   for (Pair<SlotReference, ItemStack> itemPair : trinketComponent.getAllEquipped()) {
    //     ItemStack itemStack = itemPair.getRight();
    //     Item item = itemStack.getItem();
    //     if (
    //       item instanceof BaseRune
    //     ) {
    //       String runeTraitsEncoded = itemStack.getOrCreateNbt().getString(((BaseRune)item).traitPath);
    //       Map<Traits, Integer> traitList = RuneTrait.decodeTraitListNbt(runeTraitsEncoded);
    //       for (Map.Entry<Traits, Integer> trait : traitList.entrySet()) {
    //         RuneTrait runeTrait = TraitMap.getTrait(trait.getKey());
    //         if (runeTrait != null && runeTrait instanceof StatTrait) {
    //           ((StatTrait)runeTrait).removeTrait(this.player, itemPair.getLeft(), trait.getValue());
    //         }
    //       }
    //     };
    //   }
    // }
  }
}