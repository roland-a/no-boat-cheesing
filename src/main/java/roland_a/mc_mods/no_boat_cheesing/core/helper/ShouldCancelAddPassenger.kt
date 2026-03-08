package roland_a.mc_mods.no_boat_cheesing.core.helper

import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal
import net.minecraft.world.entity.ai.goal.BreedGoal
import net.minecraft.world.entity.ai.goal.FleeSunGoal
import net.minecraft.world.entity.ai.goal.Goal
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal
import net.minecraft.world.entity.ai.goal.PanicGoal
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal
import net.minecraft.world.entity.ai.goal.RestrictSunGoal
import net.minecraft.world.entity.ai.goal.TemptGoal
import net.minecraft.world.entity.ai.goal.target.TargetGoal
import net.minecraft.world.entity.vehicle.VehicleEntity

private val applicableGoals = setOf(
	//fleeing goals
	AvoidEntityGoal::class,
	FleeSunGoal::class,
	RestrictSunGoal::class,
	PanicGoal::class,

	//temping goals
	BreedGoal::class,
	TemptGoal::class,

	//attacking goals
	MeleeAttackGoal::class,
	TargetGoal::class,
	RangedBowAttackGoal::class,
	BreedGoal::class,
)

fun shouldNotAllowRiding(vehicle: Entity, passenger: Mob): Boolean {
	if (vehicle !is VehicleEntity){
		return false
	}

	fun Goal.isApplicableGoal(): Boolean {
		return applicableGoals.any { it.isInstance(this) }
	}

	return (
		(passenger.goalSelector.availableGoals + passenger.targetSelector.availableGoals)
		.filter { it.isRunning }
		.map { it.goal }
		.any {
			it.isApplicableGoal()
		}
	)
}
