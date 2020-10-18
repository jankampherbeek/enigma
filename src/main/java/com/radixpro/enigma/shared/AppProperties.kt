/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma.shared

import org.apache.log4j.Logger
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

/**
 * Only used to find location of database.
 * TODO replace with general system properties solution  Check if used for reading csv data from Enigma 2020.1.
 */
class AppProperties(env: String) {
    private val USERHOME = "user.home"
    private val PROPS_FILE = "enigmaastroprops"
    private val DBLOC_PROP = "dblocation"
    private val LANG_PROP = "lang"
    var databasePath: String? = null
        private set
    var language: String? = null
        private set

    private fun defineProperties(env: String) {
        var userHome = ""
        try {
            LOG.info("Started AppProperties.")
            userHome = System.getProperty(USERHOME)
            LOG.info("Value for userHome : $userHome")
            val defaultProps = Properties()
            var propsLoc = userHome + File.separator + "." + PROPS_FILE
            if ("dev".equals(env, ignoreCase = true)) propsLoc = "dev" + File.separator + PROPS_FILE
            if ("test".equals(env, ignoreCase = true)) propsLoc = "test" + File.separator + PROPS_FILE
            LOG.info("Location for properties file : $propsLoc")
            val inFile = FileInputStream(propsLoc)
            defaultProps.load(inFile)
            inFile.close()
            val applicationProps = Properties(defaultProps)
            databasePath = applicationProps.getProperty(DBLOC_PROP)
            LOG.info("Value for databasePath : $databasePath")
            language = applicationProps.getProperty(LANG_PROP)
            LOG.info("Value for language : $language")
        } catch (e: IOException) {
            LOG.error("Could not read properties file in " + userHome + " . Exception : " + e.message)
            FailFastHandler().terminate("The properties file in $userHome can not be read.")
        }
    }

    companion object {
        private val LOG = Logger.getLogger(AppProperties::class.java)
    }

    init {
        defineProperties(env)
    }
}