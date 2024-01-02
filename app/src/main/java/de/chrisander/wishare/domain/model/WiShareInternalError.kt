package de.chrisander.wishare.domain.model

import de.chrisander.wishare.domain.util.FamilyMemberId
import de.chrisander.wishare.domain.util.WishId
import de.chrisander.wishare.domain.util.toHexString

sealed class WiShareInternalError(
    val domain: WiShareErrorDomain,
    errorID: UInt,
    open val underlyingError: Throwable? = null,
    val contextVariables: Map<String, String> = emptyMap()
) : Throwable(underlyingError){
    val id = domain.domainID or errorID
    val idAsHex = id.toHexString()
    override val message by lazy {
        "\n" +
                "Domain: $domain, \n" +
                "ID: $idAsHex, \n" +
                "underlyingError: ${underlyingError?.stackTraceToString()}, \n" +
                "contextVariables: $contextVariables"
    }

    sealed class GeneralError(
        errorID: UInt,
        underlyingError: Throwable? = null,
        contextVariables: Map<String, String> = emptyMap()
    ): WiShareInternalError(
        WiShareErrorDomain.General,
        errorID,
        underlyingError,
        contextVariables
    ) {
        class Unknown(
            underlyingError: Throwable? = null,
            contextVariables: Map<String, String> = emptyMap()
        ): GeneralError(
            errorID = 0xFFFFFFu,
            underlyingError = underlyingError,
            contextVariables = contextVariables
        )

        object ActionGotCancelledError: GeneralError(
            errorID = 0x000000u,
            underlyingError = null,
            contextVariables = mapOf()
        )
    }

    sealed class RuntimeModelError(
        errorID: UInt,
        underlyingError: Throwable? = null,
        contextVariables: Map<String, String> = emptyMap()
    ): WiShareInternalError(
        WiShareErrorDomain.RuntimeModel,
        errorID,
        underlyingError,
        contextVariables
    ) {
        class CouldNotFindMember(
            val memberId: FamilyMemberId
        ): RuntimeModelError(errorID = 0x000000u)

        class CouldNotFindWish(
            val wishId: WishId
        ): RuntimeModelError(errorID = 0x000001u)
    }


    enum class WiShareErrorDomain(val domainID: UInt) {
        General(0x00000000u),
        LocalDatabase(0x01000000u),
        CloudDatabase(0x02000000u),
        Authentication(0x03000000u),
        RuntimeModel(0x04000000u);
    }
}