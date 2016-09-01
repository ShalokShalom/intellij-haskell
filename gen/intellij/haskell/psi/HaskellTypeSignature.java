// This is a generated file. Not intended for manual editing.
package intellij.haskell.psi;

import com.intellij.navigation.ItemPresentation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import scala.Option;
import scala.collection.Seq;

import java.util.List;

public interface HaskellTypeSignature extends HaskellDeclarationElement {

  @Nullable
  HaskellContext getContext();

  @NotNull
  List<HaskellQNames> getQNamesList();

  @Nullable
  HaskellScontext getScontext();

  @NotNull
  HaskellTtype getTtype();

  String getName();

  ItemPresentation getPresentation();

  Seq<HaskellNamedElement> getIdentifierElements();

  Option<String> getModuleName();

}
