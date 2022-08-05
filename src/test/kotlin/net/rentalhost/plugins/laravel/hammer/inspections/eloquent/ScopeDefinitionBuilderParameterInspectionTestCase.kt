package net.rentalhost.plugins.laravel.hammer.inspections.eloquent

import net.rentalhost.plugins.services.IlluminateService
import net.rentalhost.plugins.services.TestCase

class ScopeDefinitionBuilderParameterInspectionTestCase: TestCase() {
    fun testAll(): Unit = testInspection(
        ScopeDefinitionBuilderParameterInspection::class.java,
        "default", IlluminateService.eloquentFiles
    )
}
