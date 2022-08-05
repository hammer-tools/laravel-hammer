package net.rentalhost.plugins.laravel.hammer.inspections.eloquent

import com.intellij.codeInspection.ProblemsHolder
import com.jetbrains.php.lang.inspections.PhpInspection
import com.jetbrains.php.lang.psi.elements.Method
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor
import net.rentalhost.plugins.services.IlluminateService
import net.rentalhost.plugins.services.ProblemsHolderService

class ScopeDefinitionBuilderParameterInspection: PhpInspection() {
    override fun buildVisitor(problemsHolder: ProblemsHolder, isOnTheFly: Boolean): PhpElementVisitor = object: PhpElementVisitor() {
        override fun visitPhpMethod(method: Method) {
            if (!method.name.lowercase().startsWith("scope"))
                return

            val methodNameElement by lazy { method.nameNode?.psi }

            if (method.parameters.isEmpty()) {
                return ProblemsHolderService.registerProblem(
                    problemsHolder,
                    methodNameElement ?: return,
                    "missing first parameter (Builder)"
                )
            }

            val methodParameter = method.parameters[0]
            val methodParameterDeclaredType = methodParameter.declaredType

            if (methodParameterDeclaredType.toStringResolved() == "") {
                return ProblemsHolderService.registerProblem(
                    problemsHolder,
                    methodParameter,
                    "missing first parameter type (Builder)"
                )
            }

            if (methodParameterDeclaredType.toStringResolved() != IlluminateService.Database.Eloquent.builderClass) {
                return ProblemsHolderService.registerProblem(
                    problemsHolder,
                    methodParameter.firstChild,
                    "wrong first parameter type (expected ${IlluminateService.Database.Eloquent.builderClass})"
                )
            }
        }
    }
}
