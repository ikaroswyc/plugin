/*
 * @(#) MethodVisitor.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */
package data.visitor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import data.DataNode;

public class DeclarationVisitor extends ASTVisitor {
   Map<String, DataNode> nodeMap = new HashMap<String, DataNode>();

   /**
    * A type declaration is the union of a class declaration
    * and an interface declaration.
    */
   @Override
   public boolean visit(TypeDeclaration typeDecl) {
      String pkgName = typeDecl.resolveBinding().getPackage().getName();
      String typeName = typeDecl.getName().getFullyQualifiedName();
      String nodeName = pkgName + "." + typeName;
      nodeMap.put(nodeName, new DataNode(nodeName));
      return super.visit(typeDecl);
   }

   @Override
   public boolean visit(MethodDeclaration methodDecl) {
      String typeName = methodDecl.resolveBinding().getDeclaringClass().getQualifiedName();
      DataNode node = nodeMap.get(typeName);
      if (node == null) {
         throw new RuntimeException(); // no parent node.
      } else {
         String methodName = methodDecl.getName().getFullyQualifiedName();
         String nodeName = typeName + "." + methodName;
         node.addChildNode(nodeName);
      }
      return super.visit(methodDecl);
   }

   public Map<String, DataNode> getNodeMap() {
      return nodeMap;
   }

   @Deprecated
   public ASTNode getOuterClass(ASTNode node) {
      do {
         node = node.getParent();
      } while (node != null && node.getNodeType() != ASTNode.TYPE_DECLARATION && //
            ((AbstractTypeDeclaration) node).isPackageMemberTypeDeclaration());
      return node;
   }
}
