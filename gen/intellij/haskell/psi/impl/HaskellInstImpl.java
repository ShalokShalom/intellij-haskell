// This is a generated file. Not intended for manual editing.
package intellij.haskell.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import intellij.haskell.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HaskellInstImpl extends HaskellCompositeElementImpl implements HaskellInst {

  public HaskellInstImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull HaskellVisitor visitor) {
    visitor.visitInst(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof HaskellVisitor) accept((HaskellVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public HaskellGtycon getGtycon() {
    return findChildByClass(HaskellGtycon.class);
  }

  @Override
  @NotNull
  public List<HaskellInstvar> getInstvarList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, HaskellInstvar.class);
  }

  @Override
  @Nullable
  public HaskellQName getQName() {
    return findChildByClass(HaskellQName.class);
  }

  @Override
  @Nullable
  public HaskellTtype getTtype() {
    return findChildByClass(HaskellTtype.class);
  }

}
