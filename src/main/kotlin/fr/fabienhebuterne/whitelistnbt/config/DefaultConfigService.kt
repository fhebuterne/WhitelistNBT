package fr.fabienhebuterne.whitelistnbt.config

import com.fasterxml.jackson.module.kotlin.readValue
import org.bukkit.plugin.java.JavaPlugin

class DefaultConfigService(instance: JavaPlugin) :
    ConfigService<DefaultConfig>(instance, "config") {

    override fun decodeFromString(): DefaultConfig {
        return json.readValue(file.readText(Charsets.UTF_8))
    }

}
