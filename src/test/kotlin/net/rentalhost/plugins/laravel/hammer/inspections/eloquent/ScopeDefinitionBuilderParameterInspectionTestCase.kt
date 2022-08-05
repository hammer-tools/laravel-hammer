package net.rentalhost.plugins.laravel.hammer.inspections.eloquent

import net.rentalhost.plugins.hammer.services.TestCase
import net.rentalhost.plugins.laravel.hammer.services.IlluminateService

class ScopeDefinitionBuilderParameterInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(
        ScopeDefinitionBuilderParameterInspection::class.java,
        "default", IlluminateService.eloquentFiles
    )
}
