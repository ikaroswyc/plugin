/*
 * @(#) GClassNode.java
 *
 */
package graph.model.node;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import graph.model.GNode;

public class GClassNode extends GNode {
   public  boolean isPublic = false;
   public GClassNode(String id, String name) {
      super(id, name);
   }
   public GClassNode(String id, String name, boolean isPublic) {
	      super(id, name);
	      this.isPublic = isPublic;
	   }
   public  boolean isPublic() {
	return isPublic;
}
public  void setPublic(boolean isPublic) {
	isPublic = isPublic;
}

}
