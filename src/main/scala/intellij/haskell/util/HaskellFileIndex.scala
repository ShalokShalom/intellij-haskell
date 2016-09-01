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

package intellij.haskell.util

import java.io.{DataInput, DataOutput}

import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.search.{FileTypeIndex, GlobalSearchScope}
import com.intellij.util.indexing.FileBasedIndex.InputFilter
import com.intellij.util.indexing._
import com.intellij.util.io.{DataExternalizer, EnumeratorStringDescriptor, KeyDescriptor}
import intellij.haskell.module.HaskellModuleType
import intellij.haskell.{HaskellFile, HaskellFileType}

import scala.collection.JavaConversions._

class HaskellFileIndex extends ScalaScalarIndexExtension[String] {

  override def getName: ID[String, Unit] = HaskellFileIndex.HaskellFileIndex

  override def getKeyDescriptor: KeyDescriptor[String] = HaskellFileIndex.Descriptor

  override def dependsOnFileContent(): Boolean = false

  override def getVersion: Int = HaskellFileIndex.IndexVersion

  override def getInputFilter: InputFilter = {
    HaskellFileIndex.HaskellModuleFilter
  }

  override def getIndexer: DataIndexer[String, Unit, FileContent] = haskellDataIndexer

  private val haskellDataIndexer = new HaskellDataIndexer

  private class HaskellDataIndexer extends DataIndexer[String, Unit, FileContent] {

    def map(inputData: FileContent): java.util.Map[String, Unit] = {
      Map(inputData.getFile.getNameWithoutExtension -> ())
    }
  }

}

object HaskellFileIndex {

  private final val HaskellFileIndex: ID[String, Unit] = ID.create("HaskellFileIndex")
  private final val IndexVersion = 1
  private final val Descriptor = new EnumeratorStringDescriptor
  private final val HaskellModuleFilter = new FileBasedIndex.InputFilter {

    def acceptInput(file: VirtualFile): Boolean = {
      file.getFileType == HaskellFileType.INSTANCE
    }
  }

  def findFilesByName(project: Project, name: String, searchScope: GlobalSearchScope): Iterable[HaskellFile] = {
    getByName(project, name, searchScope)
  }

  def findHaskellFiles(project: Project, searchScope: GlobalSearchScope): Iterable[HaskellFile] = {
    getFilesForType(HaskellFileType.INSTANCE, project, searchScope)
  }

  def findProjectHaskellFiles(project: Project): Iterable[HaskellFile] = {
    findHaskellFiles(project, HaskellModuleType.findHaskellProjectModules(project).map(GlobalSearchScope.moduleScope).reduce(_.uniteWith(_)))
  }

  private def getFilesForType(fileType: FileType, project: Project, searchScope: GlobalSearchScope) = {
    val virtualFiles = FileBasedIndex.getInstance.getContainingFiles(FileTypeIndex.NAME, fileType, searchScope)
    HaskellFileUtil.convertToHaskellFiles(virtualFiles, project)
  }

  private def getByName(project: Project, name: String, searchScope: GlobalSearchScope): Iterable[HaskellFile] = {
    val virtualFiles = getVirtualFilesByName(project, name, searchScope)
    HaskellFileUtil.convertToHaskellFiles(virtualFiles, project)
  }

  private def getVirtualFilesByName(project: Project, name: String, searchScope: GlobalSearchScope) = {
    FileBasedIndex.getInstance.getContainingFiles(HaskellFileIndex, name, searchScope)
  }
}

/**
  * A specialization of FileBasedIndexExtension allowing to create a mapping [DataObject -> List of files containing this object]
  *
  */
object ScalaScalarIndexExtension {
  final val VoidDataExternalizer: DataExternalizer[Unit] = new ScalaScalarIndexExtension.UnitDataExternalizer

  private class UnitDataExternalizer extends DataExternalizer[Unit] {
    def save(out: DataOutput, value: Unit) {
    }

    def read(in: DataInput): Unit = {
      ()
    }
  }

}

abstract class ScalaScalarIndexExtension[K] extends FileBasedIndexExtension[K, Unit] {
  final def getValueExternalizer: DataExternalizer[Unit] = {
    ScalaScalarIndexExtension.VoidDataExternalizer
  }
}