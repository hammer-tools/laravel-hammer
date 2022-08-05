package net.rentalhost.plugins.laravel.hammer.services

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import net.rentalhost.plugins.hammer.services.SettingsService as BaseSettingsService

@State(name = "LaravelHammerState", storages = [Storage("laravel-hammer.state.xml")])
class SettingsService: BaseSettingsService() {
    override fun getServiceInstance(): SettingsService =
        ApplicationManager.getApplication().getService(SettingsService::class.java)
}
