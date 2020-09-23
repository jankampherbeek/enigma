/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma

import com.radixpro.enigma.be.persistency.PropertyDao
import com.radixpro.enigma.shared.Property
import com.radixpro.enigma.xchg.api.PersistedPropertyApi
import org.apache.log4j.Logger
import java.util.*

/**
 * i18N manager, takes care of Resource Bundles and Locale's.<br></br>
 * Implemented as a singleton.
 */
object Rosetta {
    private var propApi: PersistedPropertyApi? = null
    private var resourceBundle: ResourceBundle? = null
    private var helpResourceBundle: ResourceBundle? = null
    private val LOG = Logger.getLogger(Rosetta::class.java)
    private const val RB_LOCATION = "texts"
    private const val RB_HELP_LOCATION = "help"
    private const val DUTCH = "du"
    private const val ENGLISH = "en"
    private const val PROP_LANG = "lang"
    private var initialized = false;

    private var locale: Locale = Locale(ENGLISH, ENGLISH.toUpperCase())

    /**
     * Sets new language
     *
     * @param language use "en" for English or "du" for Dutch (case-sensitive).
     */
    @JvmStatic
    fun setLanguage(language: String) {
        LOG.info("Setting language to : $language")
        if (language == ENGLISH || language == DUTCH) {
            val langProp = Property(PROP_LANG, language)
            propApi!!.update(langProp)
            reInitialize()
        } else {
            LOG.error("Unsupported language encountered: $language")
        }
    }

    private fun reInitialize() {
        initi18N()
        defineResourceBundles()
        initialized = true
    }

    private fun initi18N() {
        propApi = PersistedPropertyApi(PropertyDao())
        val props = propApi!!.read(PROP_LANG)
        var language = "en" // handle first start as no database has been created.
        if (props.isNotEmpty()) {
            val currentProp = propApi!!.read(PROP_LANG)[0]!!
            language = currentProp.value
        }
        locale = if (language == DUTCH) Locale(DUTCH, DUTCH.toUpperCase()) else Locale(ENGLISH, ENGLISH.toUpperCase())
    }

    private fun defineResourceBundles() {
        resourceBundle = ResourceBundle.getBundle(RB_LOCATION, locale)
        helpResourceBundle = ResourceBundle.getBundle(RB_HELP_LOCATION, locale)
    }

    @JvmStatic
    fun getText(key: String): String {
        if (!initialized) reInitialize()
        return resourceBundle!!.getString(key)
    }

    @JvmStatic
    fun getHelpText(key: String): String {
        if (!initialized) reInitialize()
        return helpResourceBundle!!.getString(key)
    }

    @JvmStatic
    fun getLocale(): Locale {
        return locale
    }


//        /**
//         * Retrieve instance of singleton Rosetta.
//         *
//         * @return instance of Rosetta.
//         */
//        var rosetta: Rosetta? = null
//            private set

//        @JvmStatic
//        fun defineRosetta(): Rosetta? {
//            if (null == rosetta) {
//                rosetta = Rosetta()
//                rosetta!!.reInitialize()
//            }
//            return rosetta
//        }
}