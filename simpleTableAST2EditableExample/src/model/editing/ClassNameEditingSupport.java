package model.editing;

import org.eclipse.jface.viewers.TableViewer;

import analysis.ReplaceClassNameAnalyzer;
import model.ProgramElement;

public class ClassNameEditingSupport extends ProgElemEditingSupport {

   public ClassNameEditingSupport(TableViewer viewer) {
      super(viewer);
   }

   @Override
   protected Object getValue(Object element) {
      return ((ProgramElement) element).getClassName();
   }

   @Override
   protected void setValue(Object element, Object value) {
      ProgramElement p = (ProgramElement) element;
      new ReplaceClassNameAnalyzer(p, String.valueOf(value));
      p.setClassName((String.valueOf(value)));
      this.viewer.update(element, null);
      refreshViewer();
   }
}
