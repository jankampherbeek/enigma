/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 */
package com.radixpro.enigma.shared

import com.radixpro.enigma.ui.screens.EmergencyExit
import org.apache.log4j.Logger

/**
 * Is called in case of a unrecoverable error. Shows a message and gives only the option to terminate the application.
 */
class FailFastHandler {
    fun terminate(_causeOfError: String?) {
        var causeOfError = _causeOfError
        if (null == causeOfError || causeOfError.isEmpty()) causeOfError = "No cause of error mentioned."
        LOG.info("Showing termination message as a unrecoverable error occurred. Cause of error: $causeOfError")
        try {
            EmergencyExit(causeOfError)
        } catch (e: Exception) {
            LOG.error("Could not start the termination popup : " + e.message)
        }
    }

    companion object {
        private val LOG = Logger.getLogger(FailFastHandler::class.java)
    }
}