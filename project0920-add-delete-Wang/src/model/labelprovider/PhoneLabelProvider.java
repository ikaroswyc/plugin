package model.labelprovider;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Text;

import model.Person;

public class PhoneLabelProvider extends FirstNameLabelProvider {

	public PhoneLabelProvider(Text searchText) {
		super(searchText);
	}

	@Override
	public void update(ViewerCell cell) {
		super.update(cell);
	}

	protected String getCellText(ViewerCell cell) {
		Person person = (Person) cell.getElement();
		String cellText = person.getPhone();
		return cellText;
	}
}
