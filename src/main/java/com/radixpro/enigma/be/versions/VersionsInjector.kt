/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.be.versions

import com.radixpro.enigma.xchg.api.XchgApiInjector

object VersionsInjector {
    fun injectUpdater(): Updater {
        return Updater()
    }

    @JvmStatic
    fun injectAppVersion(): AppVersion {
        return AppVersion(injectUpdater(), XchgApiInjector.injectVersionApi())
    }
}