package net.rentalhost.plugins.laravel.hammer.services

import net.rentalhost.plugins.hammer.services.PluginUpdateService

class PluginUpdateService: PluginUpdateService(ProjectService.instance)
