package net.rentalhost.plugins.laravel.hammer.services

import com.intellij.openapi.project.Project
import net.rentalhost.plugins.hammer.services.SidebarService
import net.rentalhost.plugins.laravel.hammer.forms.SidebarForm
import javax.swing.JComponent

class SidebarService: SidebarService() {
    override fun getPanel(project: Project): JComponent = SidebarForm(project).panel
}
