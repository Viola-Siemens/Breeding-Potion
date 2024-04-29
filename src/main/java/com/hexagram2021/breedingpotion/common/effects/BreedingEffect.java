package com.hexagram2021.breedingpotion.common.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;

public class BreedingEffect extends MobEffect {
	public BreedingEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int level) {
		if (entity.level().isClientSide) {
			return;
		}
		if (entity.getHealth() < entity.getMaxHealth() && entity.level().getRandom().nextInt(5) <= level) {
			if(entity instanceof Player player) {
				player.getFoodData().eat(1, 1.0F);
			} else {
				entity.heal(1.0F);
			}
		}
		if(entity instanceof Animal animal) {
			if(animal.getAge() == 0 && animal.canFallInLove()) {
				animal.setInLove(null);
			}
		} else if(entity instanceof Villager villager && villager.getAge() == 0 && entity.level().getRandom().nextInt(5) <= level) {
			villager.foodLevel += 1;
		}
	}
}