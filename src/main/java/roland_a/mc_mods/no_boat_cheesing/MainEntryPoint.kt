package roland_a.mc_mods.no_boat_cheesing

import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object MainEntryPoint: ModInitializer {
	val MOD_ID: String = MainEntryPoint::class.java.packageName.split(".").last()

	@Suppress("unused")
	val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {}
}
