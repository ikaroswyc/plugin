/*
 * @(#) Node.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */
package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DataNode {
   private String       name;
   private List<String> childNodes = new ArrayList<String>();

   public DataNode(String n) {
      this.name = n;
   }

   public DataNode addChildNodes(Set<String> s) {
      childNodes.addAll(s);
      return this;
   }

   public List<String> getChildNodes() {
      return this.childNodes;
   }

   public String getNodeName() {
      return this.name;
   }

   public void addChildNode(String n) {
      this.childNodes.add(n);
   }
}
