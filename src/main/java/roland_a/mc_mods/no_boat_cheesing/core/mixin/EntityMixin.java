package roland_a.mc_mods.no_boat_cheesing.core.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static roland_a.mc_mods.no_boat_cheesing.core.helper.ShouldCancelAddPassengerKt.shouldNotAllowRiding;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Shadow
	public abstract List<Entity> getPassengers();

	@Inject(method = "addPassenger", at = @At("HEAD"), cancellable = true)
	public void preventRidingIfApplicable(Entity entity, CallbackInfo ci){
		if (!(entity instanceof Mob mob)){
			return;
		}

		var self = (Entity)(Object)this;

		if (shouldNotAllowRiding(self, mob)){
			ci.cancel();
		}
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void kickOutPassengerIfApplicable(CallbackInfo ci){
		var self = (Entity)(Object)this;

		for (var passenger: this.getPassengers()){
			if (!(passenger instanceof Mob mobPassenger)){
				continue;
			}

			if (shouldNotAllowRiding(self, mobPassenger)){
				passenger.stopRiding();
			}
		}
	}
}
