package handler;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import model.Person;
import model.PersonModelProvider;
import view.MyTableViewer;

public class MovetoTopHandler {
	   @Inject
	   private ESelectionService selectionService;
	   
	   @Inject
	   private EPartService epartService;
	   		
		List<Person> selectedList = new ArrayList<>();
		List<Person> orgList = new ArrayList<>();
		private Person selectedPerson;

	   @Execute
	   public void execute(Shell shell) {
		   copySelectionToNewPerson();
		   
		   removeSelectionFromDataModel();
		   		   
		   PersonModelProvider personInstance = PersonModelProvider.INSTANCE;
		   orgList = personInstance.getPersons();
		   selectedList.add(selectedPerson);
		   for(Person p : orgList){
			   selectedList.add(p);
			   
		   }
		   personInstance.getPersons().clear();
		   
		   for(Person p: selectedList){
			   personInstance.getPersons().add(p);
			   System.out.println(p);
		   }
		   
		   selectedList.clear();
		   MPart findPart = epartService.findPart(MyTableViewer.ID);
	         Object findPartObj = findPart.getObject();

	         if (findPartObj instanceof MyTableViewer) {
	            MyTableViewer v = (MyTableViewer) findPartObj;
	            v.refresh();
	         }
	         
	       
		   MessageDialog.openInformation(shell, "Move", "Moved the Selected Line at the First Line.");
	   }
	   
	   private void copySelectionToNewPerson(){
		   Object selection = selectionService.getSelection();
		   if(selection instanceof Person){
			   selectedPerson = (Person) selection;
		   }
	   }
	   
	   
	   private void removeSelectionFromDataModel(){
		   Object selection = selectionService.getSelection();
		   if(selection instanceof Person){
			   PersonModelProvider.INSTANCE.getPersons().remove((Person)selection);
			   return;
		   }
	   }
	}
