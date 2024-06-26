package com.hexagram2021.breedingpotion.common.effects;

import com.hexagram2021.breedingpotion.common.config.BPCommonConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistries;

public class BreedingEffect extends MobEffect {
	public BreedingEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		int k = 80 >> amplifier;
		if (k > 0) {
			return duration % k == 0;
		}
		return true;
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity.level().isClientSide) {
			return;
		}
		if(entity instanceof Player player) {
			player.getFoodData().eat(1, 1.0F);
		} else if (entity.getHealth() < entity.getMaxHealth()) {
			entity.heal(1.0F);
		}
		if(entity instanceof Animal animal) {
			ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(animal.getType());
			if(animal.getAge() == 0 && animal.canFallInLove() && id != null && !BPCommonConfig.IGNORE_ANIMALS.get().contains(id.toString())) {
				animal.setInLove(null);
			}
		} else if(entity instanceof Villager villager && villager.getAge() == 0 && villager.foodLevel < 30) {
			villager.foodLevel += 1;
		}
	}
}
