/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.domain.config

class Configuration(var id: Int, var parentId: Int, var name: String, var description: String,
                    var astronConfiguration: AstronConfiguration, var delinConfiguration: DelinConfiguration)