// This is a generated file. Not intended for manual editing.
package intellij.haskell.psi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface HaskellQqExpression extends HaskellCompositeElement {

  @NotNull
  List<HaskellQName> getQNameList();

  @NotNull
  HaskellQuasiQuote getQuasiQuote();

  @NotNull
  List<HaskellSccPragma> getSccPragmaList();

}
