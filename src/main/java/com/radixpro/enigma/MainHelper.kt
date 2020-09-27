/*
 * Jan Kampherbeek, (c) 2020.
 * Enigma is open source.
 * Please check the file copyright.txt in the root of the source for further details.
 *
 */
package com.radixpro.enigma

import com.radixpro.enigma.Injector.injectAppVersion
import com.radixpro.enigma.Injector.injectDashboard
import com.radixpro.enigma.be.persistency.AppDb
import javafx.application.Application
import javafx.stage.Stage
import org.apache.log4j.Logger

/**
 * MainHelper, required for DIY-DI
 */
class MainHelper : Application() {
    private var env: String? = null
    private var scope: AppScope? = null
    fun run(args: Array<String?>) {
        env = if (args.isNotEmpty()) args[0] else "prod"
        launch(*args)
    }

    override fun start(primaryStage: Stage) {
        scope = AppScope()
        val appDb = AppDb.initAppDb("prod")
        scope!!.env = env
        LOG.info("Started Enigma.")
        injectAppVersion()
        injectDashboard().showDashboard()
    }

    companion object {
        private val LOG = Logger.getLogger(App::class.java)
    }
}