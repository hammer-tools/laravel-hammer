package net.rentalhost.plugins.laravel.hammer.inspections.eloquent

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.extensions.psi.hasTrait
import net.rentalhost.plugins.extensions.psi.insertBefore
import net.rentalhost.plugins.extensions.psi.parametersList
import net.rentalhost.plugins.services.FactoryService
import net.rentalhost.plugins.services.IlluminateService.Database.Eloquent.Builder
import net.rentalhost.plugins.services.IlluminateService.Database.Eloquent.Builder.Concerns.HasGlobalScopes
import net.rentalhost.plugins.services.LocalQuickFixService
import net.rentalhost.plugins.services.ProblemsHolderService

class ScopeDefinitionBuilderParameterInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpMethod(method: Method) {
            if (!method.name.lowercase().startsWith("scope"))
                return

            val methodClass = method.containingClass ?: return

            if (!methodClass.hasTrait(HasGlobalScopes.name))
                return

            if (method.parameters.isEmpty()) {
                return ProblemsHolderService.registerProblem(
                    problemsHolder,
                    (method.nameNode ?: return).psi,
                    "missing first parameter",
                    LocalQuickFixService.SimpleReplaceQuickFix(
                        "Add Builder parameter", method.parametersList() ?: return,
                        FactoryService.createParameterList(problemsHolder.project, "${Builder.name} \$builder")
                    )
                )
            }

            val methodParameter = method.parameters[0]
            val methodParameterDeclaredType = methodParameter.declaredType

            if (methodParameterDeclaredType.toStringResolved() == "") {
                return ProblemsHolderService.registerProblem(
                    problemsHolder,
                    methodParameter,
                    "missing first parameter type",
                    LocalQuickFixService.SimpleInlineQuickFix("Set parameter type to Builder") {
                        methodParameter.firstChild.insertBefore(FactoryService.createParameterType(problemsHolder.project, Builder.name))
                    }
                )
            }

            if (methodParameterDeclaredType.toStringResolved() != Builder.name) {
                return ProblemsHolderService.registerProblem(
                    problemsHolder,
                    methodParameter.firstChild,
                    "wrong first parameter type",
                    LocalQuickFixService.SimpleReplaceQuickFix(
                        "Replace parameter type to Builder",
                        FactoryService.createParameterType(problemsHolder.project, Builder.name)
                    )
                )
            }
        }
    }
}
