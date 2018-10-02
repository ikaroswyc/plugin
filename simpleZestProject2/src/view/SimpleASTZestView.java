/*
 * @(#) SimpleASTZestView.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */
package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
//import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import data.DataNode;

public class SimpleASTZestView {
   public final static String     VIEW_ID      = "simplezestproject2.partdescriptor.simpleastzestview";

   private Graph                  graph;
   private Map<String, GraphNode> graphNodeMap = new HashMap<String, GraphNode>();

   @PostConstruct
   public void createControls(Composite parent) {
      this.graph = new Graph(parent, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
      graph.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
            System.out.println(e);
         }
      });
   }

   public void update(List<DataNode> nodeList) {
      clear();
      for (DataNode iNode : nodeList) {
         GraphNode iGraphNode = getGraphNodeInstance(iNode.getNodeName());
         List<String> adjNodeList = iNode.getChildNodes();

         for (String jAdjNode : adjNodeList) {
            GraphNode jGraphNode = getGraphNodeInstance(jAdjNode);
            new GraphConnection(graph, ZestStyles.CONNECTIONS_SOLID, iGraphNode, jGraphNode);
         }
      }
      graph.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
   }

   private void clear() {
      for (Entry<String, GraphNode> entry : graphNodeMap.entrySet()) {
         GraphNode iNode = entry.getValue();
         iNode.dispose();
      }
      graphNodeMap.clear();
   }

   private GraphNode getGraphNodeInstance(String n) {
      GraphNode graphNode = graphNodeMap.get(n);
      if (graphNode == null) {
         graphNode = new GraphNode(this.graph, SWT.NONE, n);
         graphNodeMap.put(n, graphNode);
      }
      return graphNode;
   }

   @PreDestroy
   public void dispose() {
   }

   @Focus
   public void setFocus() {
      this.graph.setFocus();
   }
}