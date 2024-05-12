package fr.fabienhebuterne.whitelistnbt.json

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import org.bukkit.NamespacedKey
import org.bukkit.Registry
import org.bukkit.enchantments.Enchantment
import java.io.IOException

internal class EnchantmentKeyDeserializer : KeyDeserializer() {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserializeKey(key: String, ctxt: DeserializationContext?): Enchantment {
        return Registry.ENCHANTMENT.get(NamespacedKey.minecraft(key)) as Enchantment
    }
}