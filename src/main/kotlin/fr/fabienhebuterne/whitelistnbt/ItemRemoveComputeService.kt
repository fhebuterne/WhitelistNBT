package fr.fabienhebuterne.whitelistnbt

import fr.fabienhebuterne.whitelistnbt.config.DefaultConfig
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import java.util.*

class ItemRemoveComputeService(private val defaultConfig: DefaultConfig) {

    fun shouldBeRemoved(uuid: UUID?, name: String, item: ItemStack?): Boolean {
        var shouldRemoveItem = false

        if (defaultConfig.enabledWhitelistEnchantments) {
            item?.enchantments?.forEach { (enchantment, level) ->
                val enchantConfig = defaultConfig.allowedEnchantments[enchantment]

                if (enchantConfig != null && level > enchantConfig && !shouldRemoveItem) {
                    Bukkit.getLogger()
                        .info("player $uuid and name $name can't interact with item because enchantment is too high")
                    shouldRemoveItem = true
                }
            }
        }

        val itemMeta = item?.itemMeta
        val itemFlags = itemMeta?.itemFlags

        if (defaultConfig.enabledWhitelistItemFlags && itemFlags != null && !defaultConfig.allowedItemFlags.containsAll(
                itemFlags
            )
        ) {
            Bukkit.getLogger()
                .info("player $uuid and name $name can't interact with item because contains itemFlags forbidden")
            shouldRemoveItem = true
        }

        val persistentDataContainer = itemMeta?.persistentDataContainer
        val persistentDataContainerKeys = persistentDataContainer?.keys?.map { it.toString() } ?: listOf()
        if (defaultConfig.enabledWhitelistPersistentDataContainer
            && persistentDataContainer != null
            && !defaultConfig.allowedPersistentDataContainer.containsAll(persistentDataContainerKeys)) {
            Bukkit.getLogger().info("player $uuid and name $name can't interact with item because contains persistentDataContainer keys forbidden")
            shouldRemoveItem = true
        }

        val attributeModifiers = itemMeta?.attributeModifiers
        val attributeModifiersKeys = attributeModifiers?.keys() ?: listOf()
        if (defaultConfig.enabledWhitelistAttributeModifiers
            && attributeModifiers != null
            && !defaultConfig.allowedAttributeModifiers.containsAll(attributeModifiersKeys)) {
            Bukkit.getLogger().info("player $uuid and name $name can't interact with item because contains attributeModifiers keys forbidden")
            shouldRemoveItem = true
        }

        return shouldRemoveItem
    }

}