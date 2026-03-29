package cat.daisy.series.example

import cat.daisy.series.biome.DaisyBiomes
import cat.daisy.series.itemflag.DaisyItemFlags
import cat.daisy.series.enchantment.DaisyEnchantments
import cat.daisy.series.entity.DaisyEntities
import cat.daisy.series.gamemode.DaisyGameModes
import cat.daisy.series.material.DaisyMaterials
import cat.daisy.series.particle.DaisyParticles
import cat.daisy.series.potion.DaisyPotions
import cat.daisy.series.sound.DaisySounds
import cat.daisy.series.statistic.DaisyStatistics
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ProfileLoadoutCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>,
    ): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Players only.")
            return true
        }

        val icon = DaisyMaterials.parse(ExampleSeriesConfig.icon)
        val sound = DaisySounds.parse(ExampleSeriesConfig.feedbackSound)
        val flags = DaisyItemFlags.parseMany(ExampleSeriesConfig.flags)
        val enchantment = DaisyEnchantments.parse(ExampleSeriesConfig.enchantment)
        val effect = DaisyPotions.parse(ExampleSeriesConfig.effect)
        val biome = DaisyBiomes.parse(ExampleSeriesConfig.biome)
        val entity = DaisyEntities.parse(ExampleSeriesConfig.entity)
        val gameMode = DaisyGameModes.parse(ExampleSeriesConfig.gameMode)
        val particle = DaisyParticles.parse(ExampleSeriesConfig.particle)
        val statistic = DaisyStatistics.parse(ExampleSeriesConfig.statistic)

        sender.sendMessage("Icon: ${DaisyMaterials.displayName(icon)} (${DaisyMaterials.key(icon)})")
        sender.sendMessage("Sound: ${DaisySounds.displayName(sound)} (${DaisySounds.key(sound)})")
        sender.sendMessage("Flags: ${flags.joinToString { DaisyItemFlags.displayName(it) }}")
        sender.sendMessage("Enchant: ${DaisyEnchantments.displayName(enchantment)} (${DaisyEnchantments.key(enchantment)})")
        sender.sendMessage("Effect: ${DaisyPotions.displayName(effect)} (${DaisyPotions.key(effect)})")
        sender.sendMessage("Biome: ${DaisyBiomes.displayName(biome)} (${DaisyBiomes.key(biome)})")
        sender.sendMessage("Entity: ${DaisyEntities.displayName(entity)} (${DaisyEntities.key(entity)})")
        sender.sendMessage("Game mode: ${DaisyGameModes.displayName(gameMode)} (${DaisyGameModes.key(gameMode)})")
        sender.sendMessage("Particle: ${DaisyParticles.displayName(particle)} (${DaisyParticles.key(particle)})")
        sender.sendMessage("Statistic: ${DaisyStatistics.displayName(statistic)} (${DaisyStatistics.key(statistic)})")
        return true
    }
}
