package model.editing;

import org.eclipse.jface.viewers.TableViewer;

import analysis.ReplaceMethodNameAnalyzer;
import model.ProgramElement;

public class MethodNameEditingSupport extends ProgElemEditingSupport {

   public MethodNameEditingSupport(TableViewer viewer) {
      super(viewer);
   }

   @Override
   protected Object getValue(Object element) {
      return ((ProgramElement) element).getMethodName();
   }

   @Override
   protected void setValue(Object element, Object value) {
      ProgramElement p = (ProgramElement) element;
      new ReplaceMethodNameAnalyzer(p, String.valueOf(value));
      p.setMethodName((String.valueOf(value)));
      this.viewer.update(element, null);
      refreshViewer();
   }
}
