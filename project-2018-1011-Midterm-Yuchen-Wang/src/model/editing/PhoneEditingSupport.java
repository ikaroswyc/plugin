package model.editing;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;

import model.Person;

public class PhoneEditingSupport extends FirstNameEditingSupport {
	private final TableViewer viewer;

	public PhoneEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected Object getValue(Object element) {
		return ((Person) element).getPhone();
	}

	@Override
	protected void setValue(Object element, Object value) {
		((Person) element).setPhone(String.valueOf(value));
		viewer.update(element, null);
	}
}