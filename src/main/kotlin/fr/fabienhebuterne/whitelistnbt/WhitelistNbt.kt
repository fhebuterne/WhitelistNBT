package fr.fabienhebuterne.whitelistnbt

import fr.fabienhebuterne.whitelistnbt.config.DefaultConfigService
import fr.fabienhebuterne.whitelistnbt.listeners.InventoryClickEventListener
import fr.fabienhebuterne.whitelistnbt.listeners.InventoryDragEventListener
import fr.fabienhebuterne.whitelistnbt.listeners.PlayerDropItemEventListener
import org.bukkit.plugin.java.JavaPlugin

class WhitelistNbt : JavaPlugin() {

    private lateinit var defaultConfigService: DefaultConfigService

    override fun onDisable() {
        super.onDisable()
    }

    override fun onEnable() {
        super.onEnable()

        defaultConfigService = DefaultConfigService(this)
        defaultConfigService.createAndLoadConfig(true)
        val defaultConfig = defaultConfigService.getSerialization()

        val itemRemoveComputeService = ItemRemoveComputeService(defaultConfig)

        val pluginManager = server.pluginManager
        pluginManager.registerEvents(InventoryClickEventListener(itemRemoveComputeService), this)
        pluginManager.registerEvents(InventoryDragEventListener(itemRemoveComputeService), this)
        pluginManager.registerEvents(PlayerDropItemEventListener(itemRemoveComputeService), this)
    }
}