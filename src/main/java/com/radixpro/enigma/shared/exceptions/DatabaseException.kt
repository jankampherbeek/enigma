/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.shared.exceptions

/**
 * General exception for all database errors.
 */
class DatabaseException(message: String?) : Exception(message)