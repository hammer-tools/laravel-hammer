package net.rentalhost.plugins.laravel.hammer.services

import net.rentalhost.plugins.hammer.services.QuickFixService as BaseQuickFixService

class QuickFixService: BaseQuickFixService(ProjectService.instance) {
    companion object {
        val instance: QuickFixService = QuickFixService()
    }
}
