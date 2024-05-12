package fr.fabienhebuterne.whitelistnbt.config

import fr.fabienhebuterne.whitelistnbt.json.Jackson
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException


abstract class ConfigService<T : ConfigType>(
    private val instance: JavaPlugin,
    private val fileName: String
) {
    companion object {
        private const val EXTENSION_FILE = "yml"
    }

    protected var file: File = File(instance.dataFolder, "$fileName.$EXTENSION_FILE")
    protected lateinit var config: T
    protected val json = Jackson.mapper

    fun createAndLoadConfig(copyFromResource: Boolean) {
        if (!file.exists()) {
            file.parentFile.mkdirs()
            if (copyFromResource) {
                instance.saveResource("$fileName.$EXTENSION_FILE", false)
            } else {
                file.createNewFile()
            }
        }

        loadConfig()
    }

    fun loadAndFindConfigIfExist(): T? {
        if (file.exists()) {
            loadConfig()
            return config
        }
        return null
    }

    fun loadConfig() {
        config = decodeFromString()

        // We save after load to add missing key if config is updated
        save()
    }

    fun getSerialization(): T {
        return config
    }

    private fun save() {
        try {
            file.writeText(encodeToString(), Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    protected abstract fun decodeFromString(): T

    protected fun encodeToString(): String = json.writeValueAsString(config)
}
