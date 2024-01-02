package de.chrisander.wishare.domain.util

/**
 * Converts an unsigned integer to a hex string
 *
 * @param leadingZeros if true, the hex string gets padded with '0' for a full 32Bit appearance
 * @param withHexIndicator if true, the hex string gets a leading "0x" to indicate that its a hex string
 *
 * @return returns the number as string
 */
fun UInt.toHexString(leadingZeros: Boolean = true, withHexIndicator: Boolean = false): String {
    val hex = if (leadingZeros)
        this.toString(16).padStart(8, '0').uppercase()
    else
        this.toString(16).uppercase()

    if (!withHexIndicator)
        return hex

    return "0x$hex"
}