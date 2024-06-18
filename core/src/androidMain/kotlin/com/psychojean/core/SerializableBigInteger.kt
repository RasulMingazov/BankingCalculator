package com.psychojean.core

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.math.BigInteger

typealias SerializableBigInteger = @Serializable(with = BigIntegerSerializer::class) BigInteger

object BigIntegerSerializer : KSerializer<BigInteger> {

  override val descriptor = PrimitiveSerialDescriptor("java.math.BigInteger", PrimitiveKind.LONG)

  override fun deserialize(decoder: Decoder): BigInteger = decoder.decodeString().toBigInteger()

  override fun serialize(encoder: Encoder, value: BigInteger) = encoder.encodeString(value.toString())
}