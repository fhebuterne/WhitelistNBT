package fr.fabienhebuterne.whitelistnbt.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.KeyDeserializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.bukkit.NamespacedKey
import org.bukkit.Registry
import org.bukkit.enchantments.Enchantment
import java.io.IOException

internal class EnchantmentKeySerializer : JsonSerializer<Enchantment>() {
    override fun serialize(enchantment: Enchantment, jsonGenerator: JsonGenerator, p2: SerializerProvider) {
        jsonGenerator.writeFieldName(enchantment.key.key)
    }
}