package net.rentalhost.plugins.laravel.hammer.inspections.eloquent

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.hammer.extensions.psi.hasTrait
import net.rentalhost.plugins.hammer.extensions.psi.insertBefore
import net.rentalhost.plugins.hammer.extensions.psi.parametersList
import net.rentalhost.plugins.hammer.services.FactoryService
import net.rentalhost.plugins.laravel.hammer.services.IlluminateService.Database.Eloquent.Builder
import net.rentalhost.plugins.laravel.hammer.services.IlluminateService.Database.Eloquent.Concerns.HasGlobalScopes
import net.rentalhost.plugins.laravel.hammer.services.ProblemsHolderService
import net.rentalhost.plugins.laravel.hammer.services.QuickFixService

class ScopeDefinitionBuilderParameterInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpMethod(method: Method) {
            if (!method.name.lowercase().startsWith("scope"))
                return

            val methodClass = method.containingClass ?: return

            if (!methodClass.hasTrait(HasGlobalScopes.name))
                return

            if (method.parameters.isEmpty()) {
                return ProblemsHolderService.instance.registerProblem(
                    problemsHolder,
                    (method.nameNode ?: return).psi,
                    "missing first parameter",
                    QuickFixService.instance.simpleReplace(
                        "Add Builder parameter", method.parametersList() ?: return,
                        FactoryService.createParameterList(problemsHolder.project, "${Builder.name} \$builder")
                    )
                )
            }

            val methodParameter = method.parameters[0]
            val methodParameterDeclaredType = methodParameter.declaredType

            if (methodParameterDeclaredType.toStringResolved() == "") {
                return ProblemsHolderService.instance.registerProblem(
                    problemsHolder,
                    methodParameter,
                    "missing first parameter type",
                    QuickFixService.instance.simpleInline("Set parameter type to Builder") {
                        methodParameter.firstChild.insertBefore(FactoryService.createParameterType(problemsHolder.project, Builder.name))
                    }
                )
            }

            if (methodParameterDeclaredType.toStringResolved() != Builder.name) {
                return ProblemsHolderService.instance.registerProblem(
                    problemsHolder,
                    methodParameter.firstChild,
                    "wrong first parameter type",
                    QuickFixService.instance.simpleReplace(
                        "Replace parameter type to Builder",
                        FactoryService.createParameterType(problemsHolder.project, Builder.name)
                    )
                )
            }
        }
    }
}
