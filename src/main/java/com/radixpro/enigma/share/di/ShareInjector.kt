/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */

package com.radixpro.enigma.share.di

import com.radixpro.enigma.share.api.GlobalPropertyApi
import com.radixpro.enigma.share.api.PropertyApi
import com.radixpro.enigma.share.persistency.*
import com.radixpro.enigma.share.process.GlobalPropertyHandler
import com.radixpro.enigma.share.process.PropertyHandler

object ShareInjector {

    fun injectGlobalPropertyApi(): PropertyApi {
        return GlobalPropertyApi(injectGlobalPropertyHandler())
    }

    fun injectGlobalPropertyHandler(): PropertyHandler {
        return GlobalPropertyHandler(injectGlobalPropertyPersister(), injectGlobalPropertyRetriever())
    }

    fun injectGlobalPropertyPersister(): PropertyPersister {
        return GlobalPropertyPersister()
    }

    fun injectGlobalPropertyRetriever(): PropertyRetriever {
        return GlobalPropertyRetriever()
    }

    fun injectJsonReader(): Reader {
        return JsonReader()
    }

    fun injectJsonWriter(): Writer {
        return JsonWriter()
    }

}