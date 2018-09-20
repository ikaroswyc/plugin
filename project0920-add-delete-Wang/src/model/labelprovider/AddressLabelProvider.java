package model.labelprovider;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import model.Person;
import view.MyTableViewer;

public class AddressLabelProvider extends FirstNameLabelProvider {

	public AddressLabelProvider(Text searchText) {
		super(searchText);
	}

	@Override
	public void update(ViewerCell cell) {
		super.update(cell);
	}

	protected String getCellText(ViewerCell cell) {
		Person person = (Person) cell.getElement();
		String cellText = person.getAddress();
		return cellText;
	}
}
