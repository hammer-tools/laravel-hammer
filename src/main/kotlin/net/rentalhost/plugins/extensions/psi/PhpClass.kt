package net.rentalhost.plugins.extensions.psi

import com.jetbrains.php.PhpClassHierarchyUtils
import com.jetbrains.php.lang.psi.elements.PhpClass

fun PhpClass.hasTrait(name: String): Boolean {
    var found = false

    PhpClassHierarchyUtils.processSuperClasses(this, true, true) { phpClass ->
        phpClass.traitNames.forEach { traitName ->
            if (traitName == name) {
                found = true
                return@processSuperClasses false
            }
        }

        return@processSuperClasses true
    }

    return found
}
