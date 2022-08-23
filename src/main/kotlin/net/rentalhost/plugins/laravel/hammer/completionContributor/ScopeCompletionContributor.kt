package net.rentalhost.plugins.laravel.hammer.completionContributor

import com.google.common.base.CaseFormat
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementPresentation
import com.intellij.patterns.PlatformPatterns
import com.intellij.patterns.StandardPatterns
import com.intellij.util.ProcessingContext
import com.jetbrains.php.PhpIcons
import com.jetbrains.php.PhpIndex
import com.jetbrains.php.PhpPresentationUtil
import com.jetbrains.php.lang.lexer.PhpTokenTypes
import com.jetbrains.php.lang.psi.elements.FieldReference
import com.jetbrains.php.lang.psi.elements.MemberReference
import com.jetbrains.php.lang.psi.elements.MethodReference
import com.jetbrains.php.lang.psi.elements.Variable
import net.rentalhost.plugins.hammer.extensions.psi.getTypes
import net.rentalhost.plugins.hammer.extensions.psi.hasTrait
import net.rentalhost.plugins.laravel.hammer.services.IlluminateService

class ScopeCompletionContributor: CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            StandardPatterns.or(
                PlatformPatterns.psiElement(PhpTokenTypes.IDENTIFIER).afterLeaf("->").withParent(FieldReference::class.java),
                PlatformPatterns.psiElement(PhpTokenTypes.IDENTIFIER).afterLeaf("->").withParent(MethodReference::class.java)
            ),
            object: CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    val element = parameters.position
                    val elementParent = element.parent as? MemberReference ?: return
                    val elementVariable = elementParent.firstChild as? Variable ?: return
                    val elementTypes = elementVariable.getTypes()

                    for (elementType in elementTypes) {
                        val elementClasses = PhpIndex.getInstance(elementVariable.project).getClassesByFQN(elementType)

                        for (elementClass in elementClasses) {
                            if (!elementClass.hasTrait(IlluminateService.Database.Eloquent.Concerns.HasGlobalScopes.name))
                                continue

                            for (method in elementClass.methods) {
                                if (!method.name.lowercase().startsWith("scope"))
                                    continue

                                val methodSliced = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, method.name.substring(5))
                                val methodParameters = method.parameters.run { if (isNotEmpty()) copyOfRange(1, size) else this }

                                result.addElement(object: LookupElement() {
                                    override fun getLookupString(): String = methodSliced
                                    override fun isCaseSensitive(): Boolean = false

                                    override fun handleInsert(context: InsertionContext) = with(context.editor) {
                                        if (element.parent is FieldReference) {
                                            document.insertString(caretModel.offset, "()")
                                            caretModel.moveCaretRelatively(1, 0, false, false, false)
                                        }
                                    }

                                    override fun renderElement(presentation: LookupElementPresentation) = with(presentation) {
                                        itemText = lookupString
                                        icon = PhpIcons.METHOD
                                        typeText = "\$this"

                                        appendTailText(PhpPresentationUtil.formatParameters(null, methodParameters).toString(), true)
                                    }
                                })
                            }
                        }
                    }
                }
            }
        )
    }
}
