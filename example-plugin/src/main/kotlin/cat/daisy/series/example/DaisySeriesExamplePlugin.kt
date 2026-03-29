package cat.daisy.series.example

import org.bukkit.plugin.java.JavaPlugin

class DaisySeriesExamplePlugin : JavaPlugin() {
    override fun onEnable() {
        getCommand("profileloadout")?.setExecutor(ProfileLoadoutCommand())
    }
}

