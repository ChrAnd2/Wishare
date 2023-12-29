package de.chrisander.wishare.domain.model

import java.net.URL

data class Wish(
    val name: String,
    val description: String,
    val url: URL?,
    val creationDate: Long,
    val state: WishState
)
