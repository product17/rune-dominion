package io.sandbox.rune_dominion.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import dev.emi.trinkets.api.SlotReference;
import io.sandbox.rune_dominion.traits.DamageTrait;
import io.sandbox.rune_dominion.traits.RuneTrait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import oshi.util.tuples.Quartet;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
  public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Shadow abstract float applyArmorToDamage(DamageSource source, float amount);

	@Redirect(method = "applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyArmorToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F"))
	private float addDamageBeforeArmorCalc(LivingEntity entity, DamageSource source, float amount) {
		LivingEntity player = (LivingEntity)source.getAttacker();
		if (player != null && player instanceof PlayerEntity) {

			List<Quartet<
				RuneTrait,
				LivingEntity,
				SlotReference,
				Integer
			>> equippedTraits = RuneTrait.getEquippedTraits((LivingEntity)player);

			for (Quartet<RuneTrait, LivingEntity, SlotReference, Integer> equippedTrait : equippedTraits) {
				RuneTrait runeTrait = equippedTrait.getA();
				if (runeTrait instanceof DamageTrait) {
					amount = ((DamageTrait)runeTrait).applyDamage(player, source, amount, equippedTrait.getD());
				}
			}
		}
		// 	Optional<TrinketComponent> trinketComponentOptional = TrinketsApi.getTrinketComponent(player);

		// 	if (trinketComponentOptional.isPresent()) {
		// 		TrinketComponent trinketComponent = trinketComponentOptional.get();
		// 		for (Pair<SlotReference, ItemStack> itemPair : trinketComponent.getAllEquipped()) {
		// 			ItemStack itemStack = itemPair.getRight();
		// 			Item item = itemStack.getItem();
		// 			if (
		// 				item instanceof BaseRune
		// 			) {
		// 				String runeTraitsEncoded = itemStack.getOrCreateNbt().getString(((BaseRune)item).traitPath);
		// 				Map<Traits, Integer> traitList = RuneTrait.decodeTraitListNbt(runeTraitsEncoded);
		// 				for (Map.Entry<Traits, Integer> trait : traitList.entrySet()) {
		// 					RuneTrait damageTrait = TraitMap.getTrait(trait.getKey());
		// 					if (damageTrait != null && damageTrait instanceof DamageTrait) {
		// 						amount = ((DamageTrait)damageTrait).applyDamage(entity, source, amount, trait.getValue());
		// 					}
		// 				}
		// 			};
		// 		}
		// 	}
		// }

		return applyArmorToDamage(source, amount);
	}
}
