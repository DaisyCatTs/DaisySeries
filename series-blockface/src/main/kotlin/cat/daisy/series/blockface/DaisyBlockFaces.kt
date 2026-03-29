package cat.daisy.series.blockface

import cat.daisy.series.DaisySeriesParser
import cat.daisy.series.internal.buildUnknownFailure
import cat.daisy.series.internal.displayNameFromKey
import cat.daisy.series.internal.enumKey
import cat.daisy.series.internal.normalizeSeriesInput
import org.bukkit.block.BlockFace

object DaisyBlockFaces : DaisySeriesParser<BlockFace> {
    private val canonicalKeys = BlockFace.values().associateBy { key(it) }

    private val lookup =
        buildMap {
            canonicalKeys.forEach { (blockFaceKey, blockFace) ->
                put(blockFaceKey, blockFace)
            }
        }

    override fun parse(input: String): BlockFace =
        parseOrNull(input) ?: throw buildUnknownFailure("block face", input, lookup.keys)

    override fun parseOrNull(input: String): BlockFace? = lookup[normalizeSeriesInput(input)]

    fun key(blockFace: BlockFace): String = enumKey(blockFace.name)

    fun displayName(blockFace: BlockFace): String = displayNameFromKey(key(blockFace))

    fun aliases(blockFace: BlockFace): Set<String> = emptySet()
}
