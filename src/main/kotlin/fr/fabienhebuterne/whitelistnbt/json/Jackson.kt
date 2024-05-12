package fr.fabienhebuterne.whitelistnbt.json

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.bukkit.enchantments.Enchantment

object Jackson {
    private val yaml: YAMLFactory = YAMLFactory()
        .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
        .disable(YAMLGenerator.Feature.SPLIT_LINES)

    private val enchantmentModule: SimpleModule = SimpleModule("EnchantmentModule")
        .addKeyDeserializer(Enchantment::class.java, EnchantmentKeyDeserializer())
        .addKeySerializer(Enchantment::class.java, EnchantmentKeySerializer())

    val mapper: ObjectMapper = ObjectMapper(yaml)
        .registerKotlinModule()
        .registerModule(enchantmentModule)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .enable(SerializationFeature.INDENT_OUTPUT)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
}
