package de.chrisander.wishare

import org.junit.Test
import java.util.UUID

class UUIDTest {

    @Test
    fun UUIDEqualsTest(){
        val uuid = UUID.randomUUID()
        val uuidCopy = UUID.fromString(uuid.toString())
        assert(uuid == uuidCopy)
    }
}