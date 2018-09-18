package model.editing;

import org.eclipse.jface.viewers.TableViewer;

import model.Person;

public class AddressEditingSupport extends FirstNameEditingSupport{
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
