package fr.fabienhebuterne.whitelistnbt.listeners

import fr.fabienhebuterne.whitelistnbt.ItemRemoveComputeService
import fr.fabienhebuterne.whitelistnbt.config.DefaultConfig
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.inventory.ItemStack

class PlayerDropItemEventListener(private val itemRemoveComputeService: ItemRemoveComputeService): Listener {

    @EventHandler()
    fun execute(event: PlayerDropItemEvent) {
        if (event.isCancelled) {
            return
        }

        val whoClicked = event.player
        val uuid = whoClicked.uniqueId
        val name = whoClicked.name
        val item = event.itemDrop.itemStack
        val shouldRemoveItem = itemRemoveComputeService.shouldBeRemoved(uuid, name, item)

        if (shouldRemoveItem) {
            Bukkit.getLogger().info("remove item ${item.type} - ${item.itemMeta?.displayName} from player $uuid and name $name")
            whoClicked.inventory.removeItem(item)
            event.itemDrop.remove()
        }
    }

}