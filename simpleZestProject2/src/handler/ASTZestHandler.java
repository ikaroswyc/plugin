/*
 * @(#) ASTZestHandler.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */
package handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import data.analysis.ASTAnalyzer;
import view.SimpleASTZestView;

public class ASTZestHandler {
   @Execute
   public void execute(EPartService service) {
      MPart findPart = service.findPart(SimpleASTZestView.VIEW_ID);
      if (findPart != null && findPart.getObject() instanceof SimpleASTZestView) {
         SimpleASTZestView zestView = (SimpleASTZestView) findPart.getObject();
         ASTAnalyzer astAnalyzer = new ASTAnalyzer();
         if (!astAnalyzer.getNodeList().isEmpty())
            zestView.update(astAnalyzer.getNodeList());
      }
   }
}