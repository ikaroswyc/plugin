package handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import model.Person;
import model.PersonModelProvider;
import util.UtilFile;

public class SaveHandler {
	String filepath = "C:\\Users\\Yuchen\\workspace-neon3\\project-2018-1011-Midterm-Yuchen-Wang\\output-midterm-1011-Yuchen.csv";
	@Inject
	private ESelectionService selectionService;
	
	List<String> selectedList = new ArrayList<>();
	
	@Execute
	public void execute(Shell shell){
		List<Person> listPersons = PersonModelProvider.INSTANCE.getPersons();
//		List<String> listContents = new ArrayList<>();
		
		selectedList.add(doubleQuote("First Name") + "," + doubleQuote("LastName") + "," +  doubleQuote("Phone") + "," +  doubleQuote("Address"));
		getSelectedRows(selectionService.getSelection());
		
	
		
//		for(Person iPerson: listPersons){
//			String iLine = doubleQuote(iPerson.getFirstName()) + "," + doubleQuote(iPerson.getLastName()) + "," + doubleQuote(iPerson.getPhone()) + "," + doubleQuote(iPerson.getAddress());
//			selectedList.add(iLine);
//		}
		//String workDir = System.getProperty("user.dir");
		//String filePath = "C:/Users/Yuchen/workspace-neon3/project-2018-1011-Midterm-Yuchen-Wang/inputdata-midterm-1011.txt";
		
		try {
			UtilFile.saveFile(filepath, selectedList);
			MessageDialog.openInformation(shell, "Export", "Saved output-midterm-1011-Yuchen.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void getSelectedRows(Object selection){
		if(selection instanceof Person){ 
			Person p = (Person) selection;
			selectedList.add(p.getFirstName() + "," + p.getLastName() + "," + p.getPhone() + "," + p.getAddress());
			return;
		}
		if(selection instanceof Object[]){
			Object[] objs = (Object[]) selection;
			for(int i = 0; i < objs.length; i++){
				getSelectedRows(objs[i]);
			}
		}
	}
	
	String doubleQuote(String s){
		return "\"" + s + "\"";
	}
}
