// This is a generated file. Not intended for manual editing.
package intellij.haskell.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import intellij.haskell.psi.HaskellNewconstrFielddecl;
import intellij.haskell.psi.HaskellQName;
import intellij.haskell.psi.HaskellTtype;
import intellij.haskell.psi.HaskellVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HaskellNewconstrFielddeclImpl extends HaskellCompositeElementImpl implements HaskellNewconstrFielddecl {

  public HaskellNewconstrFielddeclImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull HaskellVisitor visitor) {
    visitor.visitNewconstrFielddecl(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof HaskellVisitor) accept((HaskellVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<HaskellQName> getQNameList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, HaskellQName.class);
  }

  @Override
  @NotNull
  public HaskellTtype getTtype() {
    return findNotNullChildByClass(HaskellTtype.class);
  }

}
