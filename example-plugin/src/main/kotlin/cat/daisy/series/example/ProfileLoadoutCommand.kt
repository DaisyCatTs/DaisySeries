package cat.daisy.series.example

import cat.daisy.series.attribute.DaisyAttributes
import cat.daisy.series.biome.DaisyBiomes
import cat.daisy.series.blockface.DaisyBlockFaces
import cat.daisy.series.damagecause.DaisyDamageCauses
import cat.daisy.series.difficulty.DaisyDifficulties
import cat.daisy.series.itemflag.DaisyItemFlags
import cat.daisy.series.enchantment.DaisyEnchantments
import cat.daisy.series.entity.DaisyEntities
import cat.daisy.series.gamemode.DaisyGameModes
import cat.daisy.series.material.DaisyMaterials
import cat.daisy.series.operation.DaisyOperations
import cat.daisy.series.particle.DaisyParticles
import cat.daisy.series.patterntype.DaisyPatternTypes
import cat.daisy.series.potion.DaisyPotions
import cat.daisy.series.sound.DaisySounds
import cat.daisy.series.statistic.DaisyStatistics
import cat.daisy.series.villagerprofession.DaisyVillagerProfessions
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
        val profession = DaisyVillagerProfessions.parse(ExampleSeriesConfig.villagerProfession)
        val attribute = DaisyAttributes.parse(ExampleSeriesConfig.attribute)
        val difficulty = DaisyDifficulties.parse(ExampleSeriesConfig.difficulty)
        val blockFace = DaisyBlockFaces.parse(ExampleSeriesConfig.blockFace)
        val damageCause = DaisyDamageCauses.parse(ExampleSeriesConfig.damageCause)
        val operation = DaisyOperations.parse(ExampleSeriesConfig.attributeOperation)
        val patternType = DaisyPatternTypes.parse(ExampleSeriesConfig.bannerPattern)

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
        sender.sendMessage("Profession: ${DaisyVillagerProfessions.displayName(profession)} (${DaisyVillagerProfessions.key(profession)})")
        sender.sendMessage("Attribute: ${DaisyAttributes.displayName(attribute)} (${DaisyAttributes.key(attribute)})")
        sender.sendMessage("Difficulty: ${DaisyDifficulties.displayName(difficulty)} (${DaisyDifficulties.key(difficulty)})")
        sender.sendMessage("Facing: ${DaisyBlockFaces.displayName(blockFace)} (${DaisyBlockFaces.key(blockFace)})")
        sender.sendMessage("Damage cause: ${DaisyDamageCauses.displayName(damageCause)} (${DaisyDamageCauses.key(damageCause)})")
        sender.sendMessage("Operation: ${DaisyOperations.displayName(operation)} (${DaisyOperations.key(operation)})")
        sender.sendMessage("Banner pattern: ${DaisyPatternTypes.displayName(patternType)} (${DaisyPatternTypes.key(patternType)})")
        return true
    }
}
