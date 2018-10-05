package analysis;
/*
 * @(#) ASTAnalyzer.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

import model.ProgramElement;
import util.ParseUtil;
import visitor.rewrite.ReplaceMethodVisitor;

public class ReplaceMethodNameAnalyzer {
   private ProgramElement curProgElem;
   private String         newMethodName;

   public ReplaceMethodNameAnalyzer(ProgramElement curProgName, String newMethodName) {
      this.curProgElem = curProgName;
      this.newMethodName = newMethodName;

      // Get all projects in the workspace.
      IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
      for (IProject project : projects) {
         try {
            analyzeJavaProject(project);
         } catch (MalformedTreeException | BadLocationException | CoreException e) {
            e.printStackTrace();
         }
      }
   }

   void analyzeJavaProject(IProject project) throws CoreException, JavaModelException, MalformedTreeException, BadLocationException {
      // Check if we have a Java project.
      if (!project.isOpen() || !project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
         return;
      }
      IJavaProject javaProject = JavaCore.create(project);
      IPackageFragment[] packages = javaProject.getPackageFragments();
      for (IPackageFragment iPackage : packages) {
         // Package fragments include all packages in the classpath.
         // We will only look at the package from the source folder,
         // indicating this root only contains source files.
         // K_BINARY would include also included JARS, e.g. rt.jar.
         if (iPackage.getKind() == IPackageFragmentRoot.K_SOURCE && //
               iPackage.getCompilationUnits().length >= 1 && //
               iPackage.getElementName().equals(curProgElem.getPkgName())) {
            replaceMethodName(iPackage);
         }
      }
   }

   void replaceMethodName(IPackageFragment iPackage) throws JavaModelException, MalformedTreeException, BadLocationException {
      for (ICompilationUnit iCUnit : iPackage.getCompilationUnits()) {
         /*String nameICUnit = ParseUtil.getClassNameFromJavaFile(iCUnit.getElementName());
         if (nameICUnit.equals(this.curProgElem.getClassName()) == false) {
            continue;
         }*/
         // Creation of DOM/AST from a ICompilationUnit
         // Creation of ASTRewrite
         ICompilationUnit workingCopy = iCUnit.getWorkingCopy(null);
         CompilationUnit cUnit = ParseUtil.parse(workingCopy);
         ASTRewrite rewrite = ASTRewrite.create(cUnit.getAST());
         ReplaceMethodVisitor v = new ReplaceMethodVisitor(curProgElem, newMethodName);
         v.setAST(cUnit.getAST());
         v.ASTRewrite(rewrite);
         cUnit.accept(v);
         TextEdit edits = rewrite.rewriteAST(); // Compute the edits
         workingCopy.applyTextEdit(edits, null); // Apply the edits.
         workingCopy.commitWorkingCopy(false, null); // Save the changes.
      }
   }

   void addParameter(MethodDeclaration decl, ICompilationUnit workingCopy) throws JavaModelException, IllegalArgumentException {
      AST ast = decl.getAST();
      ASTRewrite astRewrite = ASTRewrite.create(ast);

      SimpleName newName = ast.newSimpleName("newName");
      astRewrite.set(decl, MethodDeclaration.NAME_PROPERTY, newName, null);

      ListRewrite paramRewrite = astRewrite.getListRewrite(decl, MethodDeclaration.PARAMETERS_PROPERTY);

      SingleVariableDeclaration newParam = ast.newSingleVariableDeclaration();
      newParam.setType(ast.newPrimitiveType(PrimitiveType.INT));
      newParam.setName(ast.newSimpleName("p1"));
      paramRewrite.insertFirst(newParam, null);

      TextEdit edit = astRewrite.rewriteAST();
      workingCopy.applyTextEdit(edit, null);
      workingCopy.commitWorkingCopy(false, null);
   }

}