package visitor.rewrite;

import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import model.ProgramElement;
import util.MsgUtil;

public class ReplaceClassVisitor extends ASTVisitor {
	private ProgramElement   curProgElem;
	private String           newClassName;
	private ICompilationUnit iUnit;
	private ASTRewrite       rewrite;
	private CompilationUnit  cUnit;

	public ReplaceClassVisitor(ProgramElement curProgElem, String newClassName) {
		this.curProgElem = curProgElem;
		this.newClassName = newClassName;
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		if (node.getName().getIdentifier().equals(curProgElem.getClassName()) == false) {
			return true;
		}
		// Description of the change
		SimpleName oldName = node.getName();
							
		
		SimpleName newName = cUnit.getAST().newSimpleName(newClassName);

		// modifiers are public, private, protected.....
		// get the class modifiers
		List<?> modifiers = node.modifiers();
		System.out.println("[DBG] modifiers' size: " + modifiers.size());
		boolean isPublicFinal = true;
		if(modifiers.size()==0){
			isPublicFinal = false;
		}
		// keywords are public, class, abstract, super...
		for (Object m : modifiers) {
			if (m instanceof Modifier) {
				Modifier mod = (Modifier) m;
				String modStr = mod.getKeyword().toString();

				if(modStr!="public" && modStr != "final"){
					System.out.println("Not Public");
					isPublicFinal = false;
				}

				System.out.println("[DBG] " + oldName + "'s Modifier: " + modStr);
			}
		}

		try {
			// Update type java element accordingly
			String iCUnitName = iUnit.getElementName();
	
			// A class, which is a nested class or exists below a class, throws JavaModelException.
			if (iCUnitName.replace(".java", "").equals(oldName.getFullyQualifiedName())) {
				IType oldType = iUnit.getType(oldName.getFullyQualifiedName());
				oldType.rename(newClassName, true, null);
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		if (isPublicFinal == false){
			String str = "You will not change it!";
			MsgUtil.openWarning(str);
			return super.visit(node);
		}
		else{
			String str = "Do you want to rename this public (or final)" + " class " + oldName;

			if (MsgUtil.openQuestion(str)== true){

				rewrite.replace(oldName, newName, null);
			}

		}


		return super.visit(node);
	}

	public void setICompilationUnit(ICompilationUnit iUnit) {
		this.iUnit = iUnit;
	}

	public void setRewrite(ASTRewrite rewrite) {
		this.rewrite = rewrite;
	}

	public void setCompilationUnit(CompilationUnit cUnit) {
		this.cUnit = cUnit;
	}
}
