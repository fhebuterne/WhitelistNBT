package fr.fabienhebuterne.whitelistnbt.config

import org.bukkit.Registry
import org.bukkit.attribute.Attribute
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag

data class DefaultConfig(
    val enabledWhitelistEnchantments: Boolean = false,
    val allowedEnchantments: Map<Enchantment, Int> = defaultEnchantments,
    val enabledWhitelistItemFlags: Boolean = false,
    val allowedItemFlags: Set<ItemFlag> = defaultItemFlags,
    val enabledWhitelistPersistentDataContainer: Boolean = false,
    val allowedPersistentDataContainer: Set<String> = setOf(),
    val enabledWhitelistAttributeModifiers: Boolean = false,
    val allowedAttributeModifiers: Set<Attribute> = defaultAttributeModifiers,
): ConfigType {
    companion object {
        private val defaultItemFlags = ItemFlag.entries.toSet()
        private val defaultEnchantments: Map<Enchantment, Int> = Registry.ENCHANTMENT.associateWith { it.maxLevel }
        private val defaultAttributeModifiers: Set<Attribute> = Attribute.entries.toSet()
    }
}