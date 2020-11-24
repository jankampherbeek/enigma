/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.share.exceptions

import org.apache.log4j.Logger

abstract class LoggedException(override val message: String) : Exception(message) {
    protected abstract val logger: Logger
    protected abstract val reason: String

    init {
        logger.error("Exception $reason. Original message: $message")
    }

}

class SaveException(override val message: String) : LoggedException(message) {
    override val reason = "while saving "
    override val logger: Logger = Logger.getLogger(SaveException::class.java)
}

class ItemNotFoundException(override val message: String) : LoggedException(message) {
    override val logger: Logger = Logger.getLogger(ItemNotFoundException::class.java)
    override val reason: String = "Could not find item "
}