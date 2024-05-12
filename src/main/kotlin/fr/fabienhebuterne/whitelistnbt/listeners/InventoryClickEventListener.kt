package fr.fabienhebuterne.whitelistnbt.listeners

import fr.fabienhebuterne.whitelistnbt.ItemRemoveComputeService
import fr.fabienhebuterne.whitelistnbt.config.DefaultConfig
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryClickEventListener(private val itemRemoveComputeService: ItemRemoveComputeService) : Listener {

    @EventHandler
    fun execute(event: InventoryClickEvent) {
        if (event.isCancelled) {
            return
        }

        val whoClicked = event.whoClicked
        val uuid = if (whoClicked is Player) {
            whoClicked.uniqueId
        } else {
            null
        }

        val name = whoClicked.name
        val item = event.currentItem
        val shouldRemoveItem = itemRemoveComputeService.shouldBeRemoved(uuid, name, item)

        if (shouldRemoveItem) {
            Bukkit.getLogger().info("remove item ${item?.type} - ${item?.itemMeta?.displayName} from player $uuid and name $name")
            event.currentItem = null
            if (item != null) {
                whoClicked.inventory.removeItem(item)
            }
        }

    }

}