package net.rentalhost.plugins.laravel.hammer.gotoDeclaration

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.jetbrains.php.lang.psi.elements.impl.MethodReferenceImpl
import com.jetbrains.php.lang.psi.elements.impl.VariableImpl
import net.rentalhost.plugins.hammer.extensions.psi.hasTrait
import net.rentalhost.plugins.hammer.services.ClassService
import net.rentalhost.plugins.laravel.hammer.services.IlluminateService.Database.Eloquent.Concerns.HasGlobalScopes

class ScopeHandler: GotoDeclarationHandler {
    override fun getGotoDeclarationTargets(sourceElement: PsiElement?, offset: Int, editor: Editor): Array<PsiElement>? {
        val methodReference = sourceElement?.parent as? MethodReferenceImpl ?: return null
        val methodVariable = methodReference.classReference as? VariableImpl ?: return null
        val methodName = methodReference.nameNode?.text ?: return null

        methodVariable.globalType.types.forEach {
            val typeClass = ClassService.findFQN(it, sourceElement.project) ?: return@forEach

            if (!typeClass.hasTrait(HasGlobalScopes.name))
                return@forEach

            return arrayOf(typeClass.findOwnMethodByName("scope$methodName") ?: return@forEach)
        }

        return null
    }
}
