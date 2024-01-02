package de.chrisander.wishare.presentation.di

import de.chrisander.wishare.R
import de.chrisander.wishare.domain.model.Family
import de.chrisander.wishare.domain.model.FamilyMember
import de.chrisander.wishare.domain.model.UiImage
import de.chrisander.wishare.domain.model.Wish
import de.chrisander.wishare.domain.model.WishState
import de.chrisander.wishare.domain.util.FamilyMemberId
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.net.URL
import java.util.UUID

enum class PreviewData() {
    MustermannChristianId, //("Preview_Mustermann_Christian_ID"),
    MustermannSimonId, //("Preview_Mustermann_Simon_ID"),
    MustermannSophieId, //("Preview_Mustermann_Sophie_ID"),
    MustermannIngoId, //("Preview_Mustermann_Ingo_ID"),
    MustermannClaudiaId, //("Preview_Mustermann_Claudia_ID"),
    MustermannChristianWishes, //("Preview_Mustermann_Christian_Wishes"),
    MustermannSimonWishes, //("Preview_Mustermann_Simon_Wishes"),
    MustermannSophieWishes, //("Preview_Mustermann_Sophie_Wishes"),
    MustermannIngoWishes, //("Preview_Mustermann_Ingo_Wishes"),
    MustermannClaudiaWishes, //("Preview_Mustermann_Claudia_Wishes"),
    MustermannChristianWish, //("Preview_Mustermann_Christian_Wish"),
    MustermannSimonWish, //("Preview_Mustermann_Simon_Wish"),
    MustermannSophieWish, //("Preview_Mustermann_Sophie_Wish"),
    MustermannIngoWish, //("Preview_Mustermann_Ingo_Wish"),
    MustermannClaudiaWish, //("Preview_Mustermann_Claudia_Wish"),
    MustermannChristianMember, //("Preview_Mustermann_Christian_Member"),
    MustermannSimonMember, //("Preview_Mustermann_Simon_Member"),
    MustermannSophieMember, //("Preview_Mustermann_Sophie_Member"),
    MustermannIngoMember, //("Preview_Mustermann_Ingo_Member"),
    MustermannClaudiaMember, //("Preview_Mustermann_Claudia_Member"),
    MustermannMembers, //("Preview_Mustermann_Members"),
    MustermannFamily, //("Preview_Mustermann_Family"),

    MuellerJanaId, //("Preview_Mueller_Jana_ID"),
    MuellerBiancaId, //("Preview_Mueller_Bianca_ID"),
    MuellerBettinaId, //("Preview_Mueller_Bettina_ID"),
    MuellerJochenId, //("Preview_Mueller_Jochen_ID"),
    MuellerJanaWishes, //("Preview_Mueller_Jana_Wishes"),
    MuellerBiancaWishes, //("Preview_Mueller_Bianca_Wishes"),
    MuellerBettinaWishes, //("Preview_Mueller_Bettina_Wishes"),
    MuellerJochenWishes, //("Preview_Mueller_Jochen_Wishes"),
    MuellerJanaWish, //("Preview_Mueller_Jana_Wish"),
    MuellerBiancaWish, //("Preview_Mueller_Bianca_Wish"),
    MuellerBettinaWish, //("Preview_Mueller_Bettina_Wish"),
    MuellerJochenWish, //("Preview_Mueller_Jochen_Wish"),
    MuellerJanaMember, //("Preview_Mueller_Jana_Member"),
    MuellerBiancaMember, //("Preview_Mueller_Bianca_Member"),
    MuellerBettinaMember, //("Preview_Mueller_Bettina_Member"),
    MuellerJochenMember, //("Preview_Mueller_Jochen_Member"),
    MuellerMembers, //("Preview_Mueller_Members"),
    MuellerFamily, //("Preview_Mueller_Family"),

    Families, //("Preview_Families")
}

val previewModule = module {
    //region Mustermann
        single<FamilyMemberId>(named(PreviewData.MustermannChristianId)){
            UUID.randomUUID()
        }
        single<FamilyMemberId>(named(PreviewData.MustermannSimonId)){
            UUID.randomUUID()
        }
        single<FamilyMemberId>(named(PreviewData.MustermannSophieId)){
            UUID.randomUUID()
        }
        single<FamilyMemberId>(named(PreviewData.MustermannIngoId)){
            UUID.randomUUID()
        }
        single<FamilyMemberId>(named(PreviewData.MustermannClaudiaId)){
            UUID.randomUUID()
        }
        single<List<Wish>>(named(PreviewData.MustermannChristianWishes)){
            listOf(
                Wish(
                    name = "Fire pit",
                    description = "I would like to have a fire pit for our garden",
                    url = URL("https://toom.de/p/feuerschale-sicilia-stahl-100-cm/4383609"),
                    creationDate = System.currentTimeMillis(),
                    state = WishState.Reserved(get(named(PreviewData.MustermannSimonId))),
                    wisherId = get(named(PreviewData.MustermannChristianId))
                ),
                Wish(
                    name = "Wilding Shoes",
                    description = "I would like to have a new winter pair of wildling shoes",
                    url = null,
                    creationDate = System.currentTimeMillis(),
                    state = WishState.Open,
                    wisherId = get(named(PreviewData.MustermannChristianId))
                ),
                Wish(
                    name = "Shaver",
                    description = "I need a new shaver!",
                    url = null,
                    creationDate = System.currentTimeMillis(),
                    state = WishState.Bought(get(named(PreviewData.MustermannSophieId))),
                    wisherId = get(named(PreviewData.MustermannChristianId))
                ),
                Wish(
                    name = "Memory Card",
                    description = "I had to use our memory card of our dashcam for something else. No i need a new one. urgently",
                    url = null,
                    creationDate = System.currentTimeMillis(),
                    state = WishState.HandedOver,
                    wisherId = get(named(PreviewData.MustermannChristianId))
                )
            )
        }
        single<List<Wish>>(named(PreviewData.MustermannSimonWishes)){
            listOf(
                Wish(
                    name = "Pizza Oven",
                    description = "Pizza for the win!",
                    url = URL("https://www.obi.de/gasgrills/cozze-17-pizzaofen-fuer-gas-mit-druckminderer-und-schlauch/p/2878957"),
                    creationDate = System.currentTimeMillis(),
                    state = WishState.Reserved(get(named(PreviewData.MustermannChristianId))),
                    wisherId = get(named(PreviewData.MustermannSimonId))
                ),
            )
        }
        single<List<Wish>>(named(PreviewData.MustermannSophieWishes)){
            listOf(
                Wish(
                    name = "Green Abo",
                    description = "",
                    url = null,
                    creationDate = System.currentTimeMillis(),
                    state = WishState.Reserved(get(named(PreviewData.MustermannChristianId))),
                    wisherId = get(named(PreviewData.MustermannSophieId))
                ),
            )
        }
        single<List<Wish>>(named(PreviewData.MustermannClaudiaWishes)){
            listOf()
        }
        single<List<Wish>>(named(PreviewData.MustermannIngoWishes)){
            listOf()
        }
        single<FamilyMember>(named(PreviewData.MustermannChristianMember)){
            FamilyMember(
                id = get(named(PreviewData.MustermannChristianId)),
                name = "Christian",
                wishes = get(named(PreviewData.MustermannChristianWishes))
            )
        }
        single<FamilyMember>(named(PreviewData.MustermannSimonMember)){
            FamilyMember(
                id = get(named(PreviewData.MustermannSimonId)),
                name = "Simon",
                wishes = get(named(PreviewData.MustermannSimonWishes))
            )
        }
        single<FamilyMember>(named(PreviewData.MustermannSophieMember)){
            FamilyMember(
                id = get(named(PreviewData.MustermannSophieId)),
                name = "Sophie",
                wishes = get(named(PreviewData.MustermannSophieWishes))
            )
        }
        single<FamilyMember>(named(PreviewData.MustermannIngoMember)){
            FamilyMember(
                id = get(named(PreviewData.MustermannIngoId)),
                name = "Ingo",
                wishes = get(named(PreviewData.MustermannIngoWishes))
            )
        }
        single<FamilyMember>(named(PreviewData.MustermannClaudiaMember)){
            FamilyMember(
                id = get(named(PreviewData.MustermannClaudiaId)),
                name = "Claudia",
                wishes = get(named(PreviewData.MustermannClaudiaWishes))
            )
        }
        single<Wish>(named(PreviewData.MustermannChristianWish)) {
                get<List<Wish>>(named(PreviewData.MustermannChristianWishes)).first()
            }
        single<Wish>(named(PreviewData.MustermannSimonWish)) {
            get<List<Wish>>(named(PreviewData.MustermannSimonWishes)).first()
        }
        single<Wish>(named(PreviewData.MustermannSophieWish)) {
            get<List<Wish>>(named(PreviewData.MustermannSophieWishes)).first()
        }
        single<Wish>(named(PreviewData.MustermannIngoWish)) {
            get<List<Wish>>(named(PreviewData.MustermannIngoWishes)).first()
        }
        single<Wish>(named(PreviewData.MustermannClaudiaWish)) {
            get<List<Wish>>(named(PreviewData.MustermannClaudiaWishes)).first()
        }
        single<List<FamilyMember>>(named(PreviewData.MustermannMembers)) {
            listOf(
                get(named(PreviewData.MustermannChristianMember)),
                get(named(PreviewData.MustermannSimonMember)),
                get(named(PreviewData.MustermannSophieMember)),
                get(named(PreviewData.MustermannClaudiaMember)),
                get(named(PreviewData.MustermannIngoMember)),
            )
        }
        single<Family>(named(PreviewData.MustermannFamily)) {
            Family(
                name = "Mustermann",
                image = UiImage.Static(R.drawable.ic_family),
                memberIds = get<List<FamilyMember>>(named(PreviewData.MustermannMembers)).map { it.id }
            )
        }
    //endregion Mustermann

    //region Mueller
        single<FamilyMemberId>(named(PreviewData.MuellerJanaId)){
            UUID.randomUUID()
        }
        single<FamilyMemberId>(named(PreviewData.MuellerBettinaId)){
            UUID.randomUUID()
        }
        single<FamilyMemberId>(named(PreviewData.MuellerBiancaId)){
            UUID.randomUUID()
        }
        single<FamilyMemberId>(named(PreviewData.MuellerJochenId)){
            UUID.randomUUID()
        }
        single<List<Wish>>(named(PreviewData.MuellerJanaWishes)){
            listOf(
                Wish(
                    name = "Harry Potter Switch Game",
                    description = "I Love Harry Potter <3",
                    url = URL("https://www.mediamarkt.de/de/product/_hogwarts-legacy-nintendo-switch-2829202.html"),
                    creationDate = System.currentTimeMillis(),
                    state = WishState.Reserved(get(named(PreviewData.MuellerBettinaId))),
                    wisherId = get(named(PreviewData.MuellerJanaId))
                ),
                Wish(
                    name = "Biokiste Abo",
                    description = "I would like to have a new winter pair of wildling shoes",
                    url = URL("https://www.bioland-gauchel.de/de/biokiste"),
                    creationDate = System.currentTimeMillis(),
                    state = WishState.Open,
                    wisherId = get(named(PreviewData.MuellerJanaId))
                ),
            )
        }
        single<List<Wish>>(named(PreviewData.MuellerBettinaWishes)){
            listOf(
                Wish(
                    name = "Pizza Oven",
                    description = "Pizza for the win!",
                    url = URL("https://www.obi.de/gasgrills/cozze-17-pizzaofen-fuer-gas-mit-druckminderer-und-schlauch/p/2878957"),
                    creationDate = System.currentTimeMillis(),
                    state = WishState.Reserved(get(named(PreviewData.MuellerJanaId))),
                    wisherId = get(named(PreviewData.MuellerBettinaId))
                ),
            )
        }
        single<List<Wish>>(named(PreviewData.MuellerBiancaWishes)){
            listOf(
                Wish(
                    name = "Money for a Tattoo",
                    description = "",
                    url = null,
                    creationDate = System.currentTimeMillis(),
                    state = WishState.Reserved(get(named(PreviewData.MuellerJanaId))),
                    wisherId = get(named(PreviewData.MuellerBiancaId))
                ),
            )
        }
        single<List<Wish>>(named(PreviewData.MuellerJochenWishes)){
            listOf()
        }
        single<FamilyMember>(named(PreviewData.MuellerJanaMember)){
            FamilyMember(
                id = get(named(PreviewData.MuellerJanaId)),
                name = "Jana",
                wishes = get(named(PreviewData.MuellerJanaWishes))
            )
        }
        single<FamilyMember>(named(PreviewData.MuellerBettinaMember)){
            FamilyMember(
                id = get(named(PreviewData.MuellerBettinaId)),
                name = "Bettina",
                wishes = get(named(PreviewData.MuellerBettinaWishes))
            )
        }
        single<FamilyMember>(named(PreviewData.MuellerBiancaMember)){
            FamilyMember(
                id = get(named(PreviewData.MuellerBiancaId)),
                name = "Bianca",
                wishes = get(named(PreviewData.MuellerBiancaWishes))
            )
        }
        single<FamilyMember>(named(PreviewData.MuellerJochenMember)){
            FamilyMember(
                id = get(named(PreviewData.MuellerJochenId)),
                name = "Jochen",
                wishes = get(named(PreviewData.MuellerJochenWishes))
            )
        }
        single<Wish>(named(PreviewData.MuellerJanaWish)) {
                get<List<Wish>>(named(PreviewData.MuellerJanaWishes)).first()
            }
        single<Wish>(named(PreviewData.MuellerBettinaWish)) {
            get<List<Wish>>(named(PreviewData.MuellerBettinaWishes)).first()
        }
        single<Wish>(named(PreviewData.MuellerBiancaWish)) {
            get<List<Wish>>(named(PreviewData.MuellerBiancaWishes)).first()
        }
        single<Wish>(named(PreviewData.MuellerJochenWish)) {
            get<List<Wish>>(named(PreviewData.MuellerJochenWishes)).first()
        }
        single<List<FamilyMember>>(named(PreviewData.MuellerMembers)) {
            listOf(
                get(named(PreviewData.MuellerJanaMember)),
                get(named(PreviewData.MuellerBettinaMember)),
                get(named(PreviewData.MuellerBiancaMember)),
                get(named(PreviewData.MuellerJochenMember)),
            )
        }
        single<Family>(named(PreviewData.MuellerFamily)) {
            Family(
                name = "Mueller",
                image = UiImage.Static(R.drawable.ic_family),
                memberIds = get<List<FamilyMember>>(named(PreviewData.MuellerMembers)).map { it.id }
            )
        }
    //endregion Mueller

    single<List<Family>>(named(PreviewData.Families)) {
        listOf(
            get(named(PreviewData.MustermannFamily)),
            get(named(PreviewData.MuellerFamily))
        )
    }
}