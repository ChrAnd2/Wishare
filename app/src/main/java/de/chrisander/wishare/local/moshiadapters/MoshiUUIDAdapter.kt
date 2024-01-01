package de.chrisander.wishare.local.moshiadapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.UUID

class MoshiUUIDAdapter {
    @FromJson
    fun fromJson(uuid: String): UUID = UUID.fromString(uuid)

    @ToJson
    fun toJson(value: UUID): String = value.toString()
}