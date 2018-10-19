package model.editing;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;

import model.Person;

public class AddressEditingSupport extends FirstNameEditingSupport {
	private final TableViewer viewer;

	public AddressEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected Object getValue(Object element) {
		return ((Person) element).getAddress();
	}

	@Override
	protected void setValue(Object element, Object value) {
		((Person) element).setAddress(String.valueOf(value));
		viewer.update(element, null);
	}
}