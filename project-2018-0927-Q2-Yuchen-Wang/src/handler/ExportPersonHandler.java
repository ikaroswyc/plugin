package handler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import model.Person;
import model.PersonModelProvider;
import util.UtilFile;

public class ExportPersonHandler {
	@Execute
	public void execute(Shell shell){
		List<Person> listPersons = PersonModelProvider.INSTANCE.getPersons();
		List<String> listContents = new ArrayList<>();
		
		listContents.add(doubleQuote("First Name") + "," + doubleQuote("LastName") + "," +  doubleQuote("Phone") + "," +  doubleQuote("Address"));
		
		for(Person iPerson: listPersons){
			String iLine = doubleQuote(iPerson.getFirstName()) + "," + doubleQuote(iPerson.getLastName()) + "," + doubleQuote(iPerson.getPhone()) + "," + doubleQuote(iPerson.getAddress());
			listContents.add(iLine);
		}
		//String workDir = System.getProperty("user.dir");
		String filePath = "C:\\Users\\Yuchen\\workspace-neon3\\project-2018-0927-Q2-Yuchen-Wang\\output-q2-0927-Yuchen-Wang.csv";
		
		try {
			UtilFile.saveFile(filePath, listContents);
			MessageDialog.openInformation(shell, "Title", "Saved output-q2-0927-Yuchen-Wang.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	String doubleQuote(String s){
		return "\"" + s + "\"";
	}
}
