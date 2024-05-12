package fr.fabienhebuterne.whitelistnbt.listeners

import fr.fabienhebuterne.whitelistnbt.ItemRemoveComputeService
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryDragEvent

class InventoryDragEventListener(private val itemRemoveComputeService: ItemRemoveComputeService): Listener {

    @EventHandler()
    fun execute(event: InventoryDragEvent) {
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
        val item = event.cursor
        val shouldRemoveItem = itemRemoveComputeService.shouldBeRemoved(uuid, name, item)

        if (shouldRemoveItem) {
            Bukkit.getLogger().info("remove item ${item?.type} - ${item?.itemMeta?.displayName} from player $uuid and name $name")
            event.isCancelled = true
            if (item != null) {
                whoClicked.inventory.removeItem(item)
            }
        }
    }

}