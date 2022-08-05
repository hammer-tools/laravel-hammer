package net.rentalhost.plugins.laravel.hammer.services

import net.rentalhost.plugins.hammer.services.ProblemsHolderService as BaseProblemsHolderService

class ProblemsHolderService: BaseProblemsHolderService(ProjectService.instance) {
    companion object {
        val instance: ProblemsHolderService = ProblemsHolderService()
    }
}
