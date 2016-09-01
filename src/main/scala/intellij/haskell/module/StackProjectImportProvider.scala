/*
 * Copyright 2016 Rik van der Kleij
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package intellij.haskell.module

import java.io.File

import com.intellij.ide.util.projectWizard.{ModuleWizardStep, WizardContext}
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.projectImport.ProjectImportProvider

class StackProjectImportProvider(builder: StackProjectImportBuilder) extends ProjectImportProvider(builder) {

  override def createSteps(context: WizardContext): Array[ModuleWizardStep] =
    Array(new HaskellModuleWizardStep(context, HaskellModuleType.getInstance.createModuleBuilder()))

  override def canImport(fileOrDirectory: VirtualFile, project: Project): Boolean = {
    val file = new File(fileOrDirectory.getPath)
    if (!file.isDirectory) {
      Messages.showInfoMessage(project, "Please select directory of project", "Select directory")
      false
    } else {
      val canImport = file.listFiles().exists(_.getName.endsWith(".cabal"))
      if (!canImport) Messages.showInfoMessage(project, "Cannot import Haskell stack project from " + file.getPath, "Cabal file has to exist")
      canImport
    }
  }
}
