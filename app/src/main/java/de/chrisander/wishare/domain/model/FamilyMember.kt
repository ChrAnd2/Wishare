package de.chrisander.wishare.domain.model

data class FamilyMember(
    val name: String,
    val wishes: List<Wish>
)
