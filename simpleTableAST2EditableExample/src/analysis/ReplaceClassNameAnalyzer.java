/*
 * @(#) ASTAnalyzer.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */
package analysis;

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
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

import model.ProgramElement;
import visitor.rewrite.ReplaceClassVisitor;

public class ReplaceClassNameAnalyzer {
   private ProgramElement curProgElem;
   private String         newClassName;

   public ReplaceClassNameAnalyzer(ProgramElement curClassName, String newClassName) {
      this.curProgElem = curClassName;
      this.newClassName = newClassName;
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
         // We will only look at the package from the source folder, indicating this root only contains source files.
         // K_BINARY would include also included JARS, e.g. rt.jar.
         if (iPackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
            if (iPackage.getCompilationUnits().length < 1) {
               continue;
            }
            replaceClassName(iPackage);
         }
      }
   }

   void replaceClassName(IPackageFragment iPackage) throws JavaModelException, MalformedTreeException, BadLocationException {
      if (iPackage.getElementName().equals(curProgElem.getPkgName()) == false) {
         return;
      }
      for (ICompilationUnit iCunit : iPackage.getCompilationUnits()) {
         /*String nameICUnit = ParseUtil.getClassNameFromJavaFile(iCunit.getElementName());
         if (nameICUnit.equals(this.curProgElem.getClassName()) == false) {
            continue;
         }*/
         // Creation of DOM/AST from a ICompilationUnit
         // Creation of ASTRewrite
         ICompilationUnit workingCopy = iCunit.getWorkingCopy(null);
         CompilationUnit cUnit = parse(workingCopy);
         ASTRewrite rewrite = ASTRewrite.create(cUnit.getAST());
         ReplaceClassVisitor visitor = new ReplaceClassVisitor(curProgElem, newClassName);
         
            
         visitor.setICompilationUnit(iCunit);
         visitor.setRewrite(rewrite);
         visitor.setCompilationUnit(cUnit);

         cUnit.accept(visitor);
         TextEdit edits = null;
         try {
            // Compute the edits
            edits = rewrite.rewriteAST();
            // Apply the edits.
            workingCopy.applyTextEdit(edits, null);
            // Save the changes.
            workingCopy.commitWorkingCopy(false, null);
         } catch (Exception e) {
            // silence
         }
      }
   }

   /**
    * Reads a ICompilationUnit and creates the AST DOM for manipulating the
    * Java source file.
    * Constant for indicating the AST API that handles JLS8.
    * This API is capable of handling all constructs in the Java language
    * as described in the Java Language Specification, Java SE 8 Edition
    * (JLS8) as specified by JSR337. JLS8 is a superset of all earlier
    * versions of the Java language, and the JLS8 API can be used to
    * manipulate programs written in all versions of the Java language
    * up to and including Java SE 8 (aka JDK 1.8).
    */
   static CompilationUnit parse(ICompilationUnit unit) {
      ASTParser parser = ASTParser.newParser(AST.JLS8);
      parser.setKind(ASTParser.K_COMPILATION_UNIT);
      parser.setSource(unit);
      parser.setResolveBindings(true);
      return (CompilationUnit) parser.createAST(null); // parse
   }
}